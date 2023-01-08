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

    public static String[] createTextFiles(int n, int seed, int bound) {
        String[] NameOfEach = new String[n];
        Random rand = new Random(seed);
        for (int i = 0; i < n; i++) {
            try {
                int RandomNumberLine = rand.nextInt(bound);
                FileWriter writer = new FileWriter("file_" + i + ".txt");
                for (int j = 0; j < RandomNumberLine; j++) {
                    writer.write("Hello Word i'm happy\n");
                }
                writer.close();
                NameOfEach[i] = "file_" + i + ".txt";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return NameOfEach;
    }

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

    public static int getNumOfLinesThreads(String[] fileNames) throws InterruptedException {
        int SumLines = 0;
        ThreadLines [] ArrayOfThread = new ThreadLines[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            ArrayOfThread[i] = new ThreadLines(fileNames[i]);
            ArrayOfThread[i].start();
        }
        for (ThreadLines EachThread : ArrayOfThread) {
            EachThread.join();
            SumLines += EachThread.getNumLines();
        }
        return SumLines;
    }

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

    // Function to delete text file when I am going to compare the Time Complexity of different functions
    private static void deleteFiles(String[] filenames) {
        for (String filename : filenames) {
            File file = new File(filename);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        int n = 500;
        int seed = 888;
        int bound = 1000;

        String[] namefile = createTextFiles(n, seed, bound);
        System.out.println("Comparing time with : "+n+" files with a maximum of : "+bound+" lines.");
        System.out.println("_________________________________________________________________________");
        //============================================ Second Function=========================================
        long startTime = System.currentTimeMillis();
        int sum1 = getNumOfLines(namefile);
        long endTime = System.currentTimeMillis();
        System.out.println("Time without Thread : "+(endTime - startTime)+" ms with a total of :"+sum1+" lines.\"");

        //============================================ Third Function=========================================
        startTime = System.currentTimeMillis();
        int sum2 =getNumOfLinesThreads(namefile);
        endTime = System.currentTimeMillis();
        System.out.println("Time with Thread : "+(endTime - startTime)+" ms with a total of :"+sum2+" lines.");

        //============================================ Four Function=========================================
        startTime = System.currentTimeMillis();
        int sum3 = getNumOfLinesThreadsPool(namefile);
        endTime = System.currentTimeMillis();
        System.out.println("Time with ThreadPool : "+(endTime - startTime)+" ms with a total of :"+sum3+" lines.\"");

        deleteFiles(namefile);


    }
}



