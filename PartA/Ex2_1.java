/**
 A class that contains several methods for counting the number of lines in a set of text files.
 * @author Yann Chich & Tal Malka
 * @version Final ver.0.1 - 12.01.23
 */
package PartA;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Ex2_1 {
    /**
     * Creates a specified number of text files, each with a random number of lines.
     * @param n the number of files to create
     * @param seed the seed for the random number generator
     * @param bound the upper bound for the number of lines in each file
     * @return an array of the names of the created files
     */
    public static String[] createTextFiles(int n, int seed, int bound) {
        String[] NameOfEach = new String[n];
        Random rand = new Random(seed);
        for (int i = 0; i < n; i++) {
            try {
                int RandomNumberLine = rand.nextInt(bound);
                FileWriter writer = new FileWriter("file_" + i);
                for (int j = 0; j < RandomNumberLine; j++) {
                    writer.write("Hello Word i'm happy\n");
                }
                writer.close();
                NameOfEach[i] = "file_" + i;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return NameOfEach;
    }

    /**
     * Counts the number of lines in a set of files using a single thread.
     * @param fileNames an array of the names of the files to be read
     * @return the total number of lines in the files
     */
    public static int getNumOfLines(String[] fileNames) {
        int SumLines = 0;
        for (String filename : fileNames) {
            File file = new File(filename);
            int lines = 0;
            try {
                Scanner FileIn = new Scanner(file);
                while (FileIn.hasNextLine()) {
                    FileIn.nextLine();
                    lines++;
                }
                FileIn.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            SumLines += lines;
        }
        return SumLines;
    }

    /**
     * Counts the number of lines in a set of files using multiple threads.
     * @param fileNames an array of the names of the files to be read
     * @return the total number of lines in the files
     * @throws InterruptedException if a thread is interrupted while running
     */
    public static int getNumOfLinesThreads(String[] fileNames) throws InterruptedException {
        int SumLines = 0;
        ThreadLines[] ArrayOfThread = new ThreadLines[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            ArrayOfThread[i] = new ThreadLines(fileNames[i]);
            ArrayOfThread[i].start();
        }
        for (ThreadLines EachThread : ArrayOfThread) {
            EachThread.join();
            SumLines += EachThread.getNumLines();
        }
        return SumLines;
    }/**
     * Counts the number of lines in a set of files using a thread pool.
     * @param fileNames an array of the names of the files to be read
     * @return the total number of lines in the files
     * @throws ExecutionException if an error occurs while executing the threads
     * @throws InterruptedException if a thread is interrupted while running
     */
    public static int getNumOfLinesThreadsPool(String[] fileNames) throws ExecutionException, InterruptedException {
        int SumLines = 0;
        // Pool of Threading size of : fileNames
        ExecutorService MyThreadPool = Executors.newFixedThreadPool(fileNames.length);
        //create an Array to hold the Future Integer that call() return;
        Future<Integer>[] list = new Future[fileNames.length];
        // Going to start my pool
        for (int i = 0; i < fileNames.length; i++) {
            ThreadLinesCallable NumOfLine = new ThreadLinesCallable(fileNames[i]);
            // .submit return an object Future
            list[i] = MyThreadPool.submit(NumOfLine);
        }
        // Take back every result return from my different thread
        for (Future<Integer> future : list) {
            SumLines += future.get();
        }
        // Stop my Pool
        MyThreadPool.shutdown();

        return SumLines;
    }

    /**
     * Deletes a set of text files.
     * @param filenames an array of the names of the files to be deleted
     */
    public static void deleteFiles(String[] filenames) {
        for (String filename : filenames) {
            File file = new File(filename);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}