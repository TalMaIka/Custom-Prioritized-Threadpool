package PartA;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class ThreadLinesCallable implements Callable {

    private String NameOfFile;


    public ThreadLinesCallable(String NameMyFile) {
        this.NameOfFile = NameMyFile;

    }

    @Override
    public Integer call() throws Exception {
        int NumLines = 0;
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

        return NumLines;
    }
}
