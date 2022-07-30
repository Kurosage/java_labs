package com.company;
import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        int choice, len;
        int[] mass;
        String file, journal, cur;
        Scanner in = new Scanner(System.in);
        while (true){
            System.out.print("Введите путь к журналу -> ");
            journal = in.nextLine();
            File f = new File(journal);
            if (f.exists())
                break;
            else
                continue;
        }
        Console_IN CIN = new Console_IN(journal);
        Changing_VAR CV = new Changing_VAR(journal);
        Console_OUT C_OUT = new Console_OUT(journal);
        while(true){
            Algo al = new Algo(CV, CIN, C_OUT);
            System.out.print("Ввод данных происходит из консоли[1] или из файла[2] -> ");
            al.gen_ICO();
            choice = in.nextInt();
            al.gen_ICI();
            if (choice == 1){
                System.out.println("[Ввод с консоли]");
                System.out.print("Введите количество чисел -> ");
                al.gen_ICO();
                al.gen_ICO();
                in.nextLine();
                len = in.nextInt();
                al.gen_ICI();
                mass = new int[len];
                for (int i = 0; i < len; i++){
                    mass[i] = in.nextInt();
                }
                al.gen_ICI();
                al.algo(mass);
            }
            else if (choice == 2){
                System.out.println("[Ввод с файла]");
                System.out.print("Введите путь к файлу -> ");
                al.gen_ICO();
                al.gen_ICO();
                in.nextLine();
                file = in.nextLine();
                al.gen_ICI();
                File f = new File(file);
                StringBuilder sb = new StringBuilder();
                if (f.exists()){
                    try{
                        BufferedReader br = new BufferedReader(new FileReader(f.getAbsoluteFile()));
                        try{
                            String[] nums = br.readLine().split(" ");
                            mass = new int [nums.length];
                            for (int i = 0; i < nums.length; i++)
                                mass[i] = Integer.parseInt(nums[i]);
                        }
                        finally {
                            br.close();
                        }
                    }
                    catch (IOException exc){
                        throw new RuntimeException();
                    }
                    al.algo(mass);
                }
            }
            else{
                continue;
            }
            System.out.print("Продолжить работу? -> ");
            al.gen_ICO();
            //in.nextLine();
            if (in.nextInt() == 1)
                continue;
            else
                break;

        }

    }
}


