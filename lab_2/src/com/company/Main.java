package com.company;
//Дана последовательность целых чисел. Программа должна сообщить каких чисел
//встретилось больше: «чётных и отрицательных», «нечётных и отрицательных», либо их
//поровну.
import java.util.Scanner;

public class Main
{

   public static void main(String[] args)
   {
            int count_ch_ot = 0, count_nch_ot = 0;
            System.out.print("Введите последовательность целых чисел через пробел: ");
            Scanner scanner = new Scanner(System.in);
            String text = scanner.nextLine();
            String[] arr_int = text.split(" ");
            int[] numArr = new int[arr_int.length];
            for (int i = 0; i < arr_int.length; i++)
            {
                numArr[i] = Integer.parseInt(arr_int[i]);
                //System.out.print(numArr[i] + " ");
            }

            for (int i = 0; i < numArr.length; i++)
            {
                //System.out.println(numArr[i] % 2 + " ");
                if (numArr[i] % 2 == 0 && numArr[i] < 0) {
                    count_ch_ot++;
                    //System.out.println("chet: " + numArr[i] + " ");
                }
                else if (numArr[i] % 2 == -1 && numArr[i] < 0) {
                    count_nch_ot++;
                    //System.out.println("chet: " + numArr[i] + " ");
                }
            }
            if (count_ch_ot == count_nch_ot && count_ch_ot != 0)
                System.out.println("Нечетных и четных отрицательных чисел поровну");
            else if (count_ch_ot > count_nch_ot)
                System.out.println("Четных отрицательных чисел больше");
            else if (count_ch_ot < count_nch_ot)
                System.out.println("Нечетных отрицательных чисел больше");
            else
                System.out.println("В последовательности нет отрицательных чисел");
   }
}
