package com.company;

public class Algo implements Algo_Interface_1 {
    private int even_neg_count;
    private int odd_posit_count;
    private int others_count;
    IChanging_Variable ICV;
    IConsole_Input ICI;
    IConsole_Output ICO;

    public Algo(IChanging_Variable ICV, IConsole_Input ICI, IConsole_Output ICO) {
        even_neg_count = 0;
        odd_posit_count = 0;
        others_count = 0;
        this.ICV = ICV;
        this.ICI = ICI;
        this.ICO = ICO;
    }

    public void gen_ICV(){
        ICV.Handler();
    }

    public void gen_ICI(){
        ICI.Handler();
    }

    public void gen_ICO(){
        ICO.Handler();
    }

    @Override
    public void print_res() {
        if (even_neg_count == 0 && odd_posit_count == 0) {
            System.out.println("Нечего сравнивать!");
            gen_ICO();
        } else if (even_neg_count > odd_posit_count) {
            System.out.println("Четных отрицательных чисел больше нечетных положительных чисел!");
            gen_ICO();
        } else if (even_neg_count < odd_posit_count) {
            System.out.println("Нечетных положительных чисел больше четных отрицательных чисел!");
            gen_ICO();
        } else {
            System.out.println("Ничья!");
            gen_ICO();
        }
        System.out.println("[Результаты]:");
        gen_ICO();
        System.out.println("Четных отрицательных чисел -> " + even_neg_count);
        gen_ICO();
        System.out.println("Нечетных положительных чисел -> " + odd_posit_count);
        gen_ICO();
        System.out.println("Остальных -> " + others_count);
        gen_ICO();
    }

    @Override
    public void algo(int[] mass) {
        even_neg_count = 0;
        odd_posit_count = 0;
        others_count = 0;
        gen_ICV();
        gen_ICV();
        gen_ICV();
        for (int i = 0; i < mass.length; i++) {
            if (mass[i] < 0 && mass[i] % 2 == 0) {
                even_neg_count++;
                gen_ICV();
            } else if (mass[i] > 0 && mass[i] % 2 != 0) {
                odd_posit_count++;
                gen_ICV();
            } else {
                others_count++;
                gen_ICV();
            }
        }
        print_res();
    }

    public int get_enc(){
        return even_neg_count;
    }
    public int get_opc(){
        return odd_posit_count;
    }
    public int get_oc(){
        return others_count;
    }
}