/**

 A class that extends Thread to count the number of lines in a specified file.
 */
package PartA;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ThreadLines extends Thread {
    /** The name of the file to be read */
    private  String NameOfFile;

    /** The number of lines in the file */
    private int NumLines;

    /**
     * Constructor for creating a ThreadLines object.
     * @param NameMyFile the name of the file to be read
     */
    public ThreadLines(String NameMyFile) {
        this.NameOfFile = NameMyFile;
        this.NumLines = 0;
    }

    /**
     * Gets the number of lines in the file.
     * @return the number of lines in the file
     */
    public int getNumLines() {
        return NumLines;
    }

    /**
     * Overrides the run method from the Thread class to count the number of lines in the specified file.
     */
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