/**
 * A class that implements the Callable interface to count the number of lines in a specified file.
 */
package PartA;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class ThreadLinesCallable implements Callable {
    /** The name of the file to be read */
    private String NameOfFile;

    /**
     * Constructor for creating a ThreadLinesCallable object.
     * @param NameMyFile the name of the file to be read
     */
    public ThreadLinesCallable(String NameMyFile) {
        this.NameOfFile = NameMyFile;

    }

    /**
     * Overrides the call method from the Callable interface to count the number of lines in the specified file.
     * @return the number of lines in the file
     * @throws Exception if an error occurs while reading the file
     */
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