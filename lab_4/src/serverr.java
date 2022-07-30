import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.io.InputStream;
import java.util.concurrent.ConcurrentSkipListSet;
// 1) UDP клиент/сервер
// 2) в клиенте указание адреса и порта сервера 2 - параметры
// 3) указание порта для сервера 2 – параметры
// 4) Сообщения, получаемые клиентом с сервера должны записываться в файл
//        «Журнала клиента» путь к которому определяется 2 – параметры
// 5) Сообщения, получаемые сервером от клиента должны записываться в файл
//    «Журнала сервера» путь к которому определяется: 3 – из файла

//  3)      На сервере есть три двумерных массива данных: целочисленных, вещественных и
//  строковых. Клиент по указанию номера массива данных и ячейки в нём должен быть
//  способен: считывать и перезаписывать её. Если клиент указывает несколько ячеек
//  (возможно в разных массивах данных), то они должны принимать некоторое
//  предустановленное значение. Также, по запросу клиента сервер должен возвращать
//  размерность указанного массива данных.
//   изменения в массивах должны дублироваться выводом массивов на консоль.

class Properties {
    public static final String SERVER_LOG = "server.log";

    private static final java.util.Properties properties = new java.util.Properties();

    /**
     * @param name имя параметра настройки
     * @return значение данного параметра
     */
    public static String getProperty(String name) {
        if (properties.isEmpty()){
            try (InputStream is = Properties.class.getClassLoader().getResourceAsStream("app.properties")) {
                properties.load(is);
            } catch (Exception ex){
                throw new RuntimeException("Error reading database settings file.");
            }
        }
        return properties.getProperty(name);
    }
}

public class serverr {
    public static final int LENGTH_PACKET = 30;
    public static final String HOST = "localhost";
    static int[][] intArr = new int[5][5];
    static String[][] strArr = new String[5][5];
    static float[][] floatArr = new float[5][5];
    private static InetAddress     clientAddr;
    private static int             clientPort;
    private static final int defaultIntValue = 12345;
    private static final String defaultStrValue = "abcdef";
    private static final float defaultFloatValue = 12345.23f;

    public static class Server implements Runnable{
        DatagramSocket  servSocket;
        DatagramPacket  datagram;
        byte[]          data;
        String          journalPath;



        public Server(int port, String journalPath) throws IOException {
            this.journalPath = journalPath;
            servSocket = new DatagramSocket(port);
        }

        @Override
        public void run() {
            try {
                data = new byte[LENGTH_PACKET];
                datagram = new DatagramPacket(data, data.length);
                servSocket.receive(datagram);
                String answer = new String(data, StandardCharsets.UTF_8).trim();
                BufferedWriter  journalFileWriter = new BufferedWriter(new FileWriter(journalPath));
                journalFileWriter.write(answer + "\n");
                journalFileWriter.close();
                System.out.println("Принято от клиента: " + answer);
                if (answer.equals("start")){
                    data = startClient(datagram);
                }
                else if (answer.startsWith("size")){
                    data = getArraySize();
                }
                else if (answer.matches("[-+]?\\d+.*")){
                    data = workWithArrs(answer);
                }
                else {
                    data = "Error command".getBytes();
                }
                datagram = new DatagramPacket(data, data.length, clientAddr, clientPort);
                servSocket.send(datagram);
                servSocket.close();
            }
            catch(SocketException e){
                System.err.println("Cant open socket : " + e);
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }

        }
    }

    public static void main(String[] args) {

        DatagramSocket  servSocket = null;
        DatagramPacket  datagram;
        byte[]          data;

        try {
            int serverPort = Integer.parseInt(args[0]);
            Properties properties = new Properties();

            File f = new File(properties.getProperty(Properties.SERVER_LOG));
            BufferedWriter journalFileWriter = new BufferedWriter(new FileWriter(f.getAbsolutePath(), true));
            while (true){
                servSocket = new DatagramSocket(serverPort);
                data = new byte[LENGTH_PACKET];
                datagram = new DatagramPacket(data, data.length);
                servSocket.receive(datagram);
                String answer = new String(data, StandardCharsets.UTF_8).trim();
                journalFileWriter.write(answer + "\n");
                clientAddr = datagram.getAddress();
                clientPort = datagram.getPort();
                System.out.println("Recieved from client: " + answer);
                if (answer.equals("start")){
                    data = startClient(datagram);
                }
                else if (answer.startsWith("size")){
                    data = getArraySize();
                }
                else if (answer.equals("finish")){
                    break;
                }
                else if (answer.matches("[-+]?\\d+.*")){
                    data = workWithArrs(answer);
                }
                else {
                    data = "Error command".getBytes();
                }
                datagram = new DatagramPacket(data, data.length, clientAddr, clientPort);
                servSocket.send(datagram);
                servSocket.close();
            }
            journalFileWriter.close();

        }
        catch(SocketException e){
            System.err.println("Cant open socket : " + e.toString());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    private static byte[] workWithArrs(String answer) {
        String[] array = answer.split(" ");
        String data = null;
        if (array.length == 3){
            data = checkArraysElem(array);
        }
        else if (array.length > 3){
            if (answer.indexOf(':') != -1){
                data = editArraysElementsToDefaultValue(answer);
            }
            else {
                data = editArraysElement(array);
            }
        }
        if (data == null)
            return ("Error command").getBytes();
        return data.getBytes();
    }

    private static String editArraysElementsToDefaultValue(String command) {
        String[] array = command.split(":");
        for (String elem : array) {
            elem = elem.trim();
            String[] elemStr = elem.split(" ");
            if (elemStr.length != 3)
                return null;
            int[] numbers = new int[3];
            for (int i = 0; i < 3; i++) {
                if (elemStr[i].matches("\\d+")){
                    numbers[i] = Integer.parseInt(elemStr[i]);
                }
                else {
                    return null;
                }
            }
            if (!putDefaultValue(numbers)){
                return null;
            }
        }
        return "1: integer array" + Arrays.deepToString(intArr) +
                "\n2: string array " + Arrays.deepToString(strArr) +
                "\n3: float array " + Arrays.deepToString(floatArr);
    }

    private static boolean putDefaultValue(int[] numbers) {
        if (numbers[0] > 3 || numbers[0] < 1)
            return false;
        if (numbers[1] > 24 || numbers[1] < 0 || numbers[2] > 24 || numbers[2] < 0)
            return false;
        if (numbers[0] == 1){
            intArr[numbers[1]][numbers[2]] = defaultIntValue;
        }
        else if (numbers[0] == 2){
            strArr[numbers[1]][numbers[2]] = defaultStrValue;
        }
        else {
            floatArr[numbers[1]][numbers[2]] = defaultFloatValue;
        }
        return true;
    }


    private static String editArraysElement(String[] array) {
        int[] commandNumbers = new int[3];
        for (int i = 0; i != 3; i++) {
            if (array[i].matches("[-+]?\\d+")){
                commandNumbers[i] = Integer.parseInt(array[i]);
            }
            else {
                return null;
            }
        }
        if (commandNumbers[1] > 4 || commandNumbers[2] > 4 || commandNumbers[0] < 0){
            return null;
        }
        if (commandNumbers[0] == 1){
            if (array[3].matches("[-+]?\\d+")){
                intArr[commandNumbers[1]][commandNumbers[2]] = Integer.parseInt(array[3]);
            }
            else {
                return null;
            }
            return Arrays.deepToString(intArr);
        }
        else if (commandNumbers[0] == 2){
            StringBuilder stringArg = new StringBuilder();
            for (int i = 3; i < array.length; i++){
                stringArg.append(array[i]).append(" ");
            }
            stringArg.deleteCharAt(stringArg.length() - 1);
            strArr[commandNumbers[1]][commandNumbers[2]] = stringArg.toString();
            return Arrays.deepToString(strArr);
        }
        else if (commandNumbers[0] == 3){
            if (array[3].matches("[-+]?\\d+\\.?\\d*")){
                floatArr[commandNumbers[1]][commandNumbers[2]] = Float.parseFloat(array[3]);
            }
            else {
                return null;
            }
            return Arrays.deepToString(floatArr);
        }
        return null;
    }

    private static String checkArraysElem(String[] array) {
        int[] comandNumbers = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            if (array[i].matches("[-+]?\\d+")){
                comandNumbers[i] = Integer.parseInt(array[i]);
            }
            else {
                return null;
            }
        }
        if (comandNumbers[1] > 4 || comandNumbers[2] > 4 || comandNumbers[0] < 0){
            return null;
        }
        else if (comandNumbers[0] == 1){
            System.out.println(comandNumbers);
            return "Integer array contain [" + comandNumbers[1] +
                    "][" + comandNumbers[2] + "]: "
                    + intArr[comandNumbers[1]][comandNumbers[2]];
        }
        else if (comandNumbers[0] == 2){
            return "String array contain [" + comandNumbers[1] +
                    "][" + comandNumbers[2] + "]: "
                    + strArr[comandNumbers[1]][comandNumbers[2]];
        }
        else if (comandNumbers[0] == 3){
            return "Float array contain [" + comandNumbers[1] +
                    "][" + comandNumbers[2] + "]: "
                    + floatArr[comandNumbers[1]][comandNumbers[2]];
        }
        return null;
    }

    private static byte[] getArraySize() {
        return "Array size - 5 to 5\n".getBytes();
    }


    private static byte[] startClient(DatagramPacket  datagram){
        return ("1: Integer array" + Arrays.deepToString(intArr) +
                "\n2: String array " + Arrays.deepToString(strArr) +
                "\n3:Float array " + Arrays.deepToString(floatArr) +
                "\nEnter: [1-3] [0-4] [0-4] [new meaning]\n").getBytes();
    }

}