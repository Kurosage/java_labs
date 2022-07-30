import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class clientt {
    public static final int LENGTH_PACKET = 1000;
    public static String host;
    public static int port;
    static String journalFilePath;
    public static void main(String[] args) {
        try{
            journalFilePath = args[2];
            String message = "";
            File f = new File(journalFilePath);
            BufferedWriter journalFileWriter = new BufferedWriter(new FileWriter(f.getAbsolutePath(), true));
            byte[] data = ("start").getBytes();
            System.out.println("Connecting...");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            host = args[0];
            port = Integer.parseInt(args[1]);
            InetAddress addr = InetAddress.getByName(host);
            DatagramSocket socket = new DatagramSocket();
            while (true){
                DatagramPacket packet = new DatagramPacket(data, data.length, addr, port);
                socket.send(packet);
                if (message.equals("finish"))
                    break;
//-----------------------------------------------------------------
//приём сообщения с сервера
//-----------------------------------------------------------------
                byte[] data2 = new byte[LENGTH_PACKET];
                packet = new DatagramPacket(data2, data2.length);
                socket.receive(packet);
                String servResponse = (new String(packet.getData())).trim();
                System.out.println(servResponse);
                journalFileWriter.write(servResponse + "\n");
                System.out.print("Enter command\n>> ");
                message = reader.readLine();
                data = message.getBytes();
                if (message.isEmpty())
                    break;
            }
//-----------------------------------------------------------------
//закрытие сокета
//-----------------------------------------------------------------
            journalFileWriter.close();
            socket.close();
        } catch(IOException e){e.printStackTrace();
        }
    }

    public static void getPortAndAddressFromUser() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter port\n>> ");
        port = Integer.parseInt(reader.readLine());
        System.out.print("Enter host\n>> ");
        host = reader.readLine();
        System.out.print("Enter path to journal client\n>> ");
        journalFilePath = reader.readLine();
    }
}