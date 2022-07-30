package lab3;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

class FunctionRecieve extends Observable {

    void notifyObs() {
        setChanged();
        notifyObservers("Получение результата работы функции\n");
    }
}

class MassivLength extends Observable {

    void notifyObs() {
        setChanged();
        notifyObservers("В массиве число элементов равно указанному\n");
    }
}

class ChangeNumber extends Observable {

    void notifyObs() {
        setChanged();
        notifyObservers("Изменение указанной переменной\n");
    }
}

class Watcher implements Observer {

    public void update(Observable obs, Object arg) {
        System.out.println("Обозреватель получил сообщение - " + arg );

    }
}
public class laba3 {
    static BufferedWriter FileWrite;

    public static void main(String[] args) throws IOException {

        Watcher watcher = new Watcher();
        FunctionRecieve func_recive = new FunctionRecieve();
        ChangeNumber changenumber = new ChangeNumber();
        MassivLength mas_len = new MassivLength();
        func_recive.addObserver(watcher);
        changenumber.addObserver(watcher);
        mas_len.addObserver(watcher);
        counter_cl arr = null;

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("\nВведите путь к файлу журналу : ");
        try {

            String filename = consoleReader.readLine();
            FileWrite = new BufferedWriter(new FileWriter(filename));
            System.out.print("\nВведите путь к файлу, где находится элемент N :");
            String filePath = consoleReader.readLine();
            BufferedReader fileReader = new BufferedReader(new FileReader(filePath));

            System.out.print("\nВведите элементы : ");
            String argsLine = consoleReader.readLine();
            String[] arrArgs = argsLine.split(" ");

            String line = fileReader.readLine();
            int argsNumber = Integer.parseInt(line);
            if (argsNumber == arrArgs.length) {
                mas_len.notifyObs();
                FileWrite.write("В массиве число элементов равно указанному\n");
            }
            for (String num: arrArgs)
            {
                if (Integer.parseInt(num) == argsNumber)
                {
                    changenumber.notifyObs();
                    FileWrite.write("Изменение указанной переменной\n");
                }
            }
            arr = new counter_cl(arrArgs);
            arr.podschet();
            if (arr.count_ch_ot == arr.count_nch_ot && arr.count_ch_ot != 0)
            {
                System.out.println("Odd and even negative numbers equally");
                }
            else if (arr.count_ch_ot > arr.count_nch_ot){
                System.out.println("There are more even negative numbers");
                }
            else if (arr.count_ch_ot < arr.count_nch_ot){
                System.out.println("There are more odd negative numbers");
                }
            else{
                System.out.println("There are no negative numbers in the sequence");
                }
            func_recive.notifyObs();
            FileWrite.write("Получение результата работы функции\n");

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (NumberFormatException e) {
            System.out.println("Введено не число ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            FileWrite.close();
        }
    }

    public static class counter_cl {
        int count_ch_ot;
        int count_nch_ot;
        int count_mas;
        ArrayList<Integer> num_arg = new ArrayList<>();

        public counter_cl(String[] num_ar) {
            count_mas = num_ar.length;
            for (String elem : num_ar) {
                num_arg.add(Integer.parseInt(elem));
            }
        }

        public void odd_negative() {
            count_nch_ot++;
        }

        public void even_negative() {
            count_ch_ot++;
        }

        public void podschet() {
            for (Integer num : num_arg) {
                //System.out.println(numArr[i] % 2 + " ");
                if (num % 2 == 0 && num < 0) {
                    even_negative();
                    //System.out.println("chet: " + numArr[i] + " ");
                } else if (num % 2 == -1 && num < 0) {
                    odd_negative();
                    //System.out.println("chet: " + numArr[i] + " ");
                }
            }
        }
    }
}