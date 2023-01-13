package PartA;

import PartB.Tests;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import static PartA.Ex2_1.*;
import static org.junit.jupiter.api.Assertions.*;

class Test {

    public static final Logger logger = LoggerFactory.getLogger(Tests.class);


    @org.junit.jupiter.api.Test
    public void Test() throws Exception  {

        int n = 500;
        int seed = 888;
        int bound = 1000;

        String[] namefile  = createTextFiles(n, seed, bound);
        assertEquals(namefile.length,500);
        assertEquals(namefile[0],"file_0");
        assertEquals(namefile[5],"file_5");
        logger.info(()-> "Comparing time with : "+n+" files with a maximum of : "+bound+" lines.");
        logger.info(()-> "________________________________________________________________________");
        //============================================ Second Function=========================================
        long startTime = System.currentTimeMillis();
        int sum1 = getNumOfLines(namefile);
        long endTime = System.currentTimeMillis();
        logger.info(()->"Time without Thread : "+(endTime - startTime)+" ms with a total of :"+sum1+" lines.");

        //============================================ Third Function=========================================
        long startTime1 = System.currentTimeMillis();
        int sum2 =getNumOfLinesThreads(namefile);
        long endTime1 = System.currentTimeMillis();
        logger.info(()-> "Time with Thread : "+(endTime1 - startTime1)+" ms with a total of :"+sum2+" lines.");

        //============================================ Four Function=========================================
        long startTime2 = System.currentTimeMillis();
        int sum3 = getNumOfLinesThreadsPool(namefile);
        long endTime2 = System.currentTimeMillis();
        logger.info(()-> "Time with ThreadPool : "+(endTime2 - startTime2)+" ms with a total of :"+sum3+" lines.");

        assertEquals(sum1,sum2);
        assertEquals(sum2,sum3);
        assertEquals(sum3,sum1);



        deleteFiles(namefile);
    }
}