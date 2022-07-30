package com.company;
import java.io.*;

public class Changing_VAR implements IChanging_Variable{
    String journal_path;

    Changing_VAR(String journal_path){
        this.journal_path = journal_path;
    }

    @Override
    public void Handler() {
        //System.out.println("Изменение указанной переменной!");
        try{
            FileWriter wr = new FileWriter(journal_path, true);
            BufferedWriter bw = new BufferedWriter(wr);
            bw.write("Изменение переменной счетчика!\n");
            bw.close();
        }
        catch(IOException ex){
            System.out.println(ex);
        }
    }
}
