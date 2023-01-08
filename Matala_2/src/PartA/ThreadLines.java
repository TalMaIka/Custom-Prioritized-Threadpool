package PartA;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ThreadLines extends Thread {

    private  String NameOfFile;
    private int NumLines;

    public ThreadLines(String NameMyFile) {
        this.NameOfFile = NameMyFile;
        this.NumLines = 0;
    }

    public int getNumLines() {
        return NumLines;
    }

    @Override
    public void run() {
        File file = new File(NameOfFile);
        try {
            Scanner FileIn = new Scanner(file);
            while (FileIn.hasNextLine()) {
                FileIn.nextLine();
                NumLines++;
            }
            FileIn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
