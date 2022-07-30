package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Console_OUT implements IConsole_Output{
    String journal_path;

    Console_OUT(String journal_path){
        this.journal_path = journal_path;
    }
    @Override
    public void Handler() {
        //System.out.println("Обращение к потоку вывода на консоль!");
        try{
            FileWriter wr = new FileWriter(journal_path, true);
            BufferedWriter bw = new BufferedWriter(wr);
            bw.write("Обращение к потоку вывода на консоль!\n");
            bw.close();
        }
        catch(IOException ex){
            System.out.println(ex);
        }
    }
}
