package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Console_IN implements IConsole_Input{
    String journal_path;

    Console_IN(String journal_path){
        this.journal_path = journal_path;
    }
    @Override
    public void Handler() {
        //System.out.println("Обращение к потоку ввода с консоли!");
        try{
            FileWriter wr = new FileWriter(journal_path, true);
            BufferedWriter bw = new BufferedWriter(wr);
            bw.write("Обращение к потоку ввода с консоли!\n");
            bw.close();
        }
        catch(IOException ex){
            System.out.println(ex);
        }
    }
}
