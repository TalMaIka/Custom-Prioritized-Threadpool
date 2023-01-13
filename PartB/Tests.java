package PartB;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.*;
public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);

    @Test
    public void partialTest() throws Exception {
        CustomExecutor customExecutor = new CustomExecutor();

//============================= Put 30000 task in the PriorityBlockingQueue and verify the GetCurrentMax =====================
//================================ First Task with priority of 2 ===========================================================
        Callable<String> callable2 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };
        Task<Integer> task = Task.createTask(() -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        assertEquals(task.getType(),TaskType.COMPUTATIONAL);
        Future reverseTask = customExecutor.submit(callable2, TaskType.IO);
        for (int i = 0; i <20000 ; i++) {
            customExecutor.submit(callable2,TaskType.IO); // There is a lot of Task with a priority of 2
        }
        int sizeAtmoment = customExecutor.getSizeQueue();
        int maxAtMoment = customExecutor.getCurrentMax();
        Future sumTask = customExecutor.submit(task);
        for (int i = 0; i <10000 ; i++) { // insert a lot of task with a priority of 1 to check if my PriorityBlockingQueue is working
            customExecutor.submit(task);
        }
        int sizeAtMoment = customExecutor.getSizeQueue();
        int MaxAtMoment = customExecutor.getCurrentMax();
        assertEquals(2,maxAtMoment);
        logger.info(()-> "Current maximum priority = " +maxAtMoment);
        assertNotNull(sizeAtmoment);
        logger.info(()-> "Size of Pool : "+sizeAtmoment);
        logger.info(()-> "Current maximum priority after adding 10000 (Task,1) = " +MaxAtMoment);
        logger.info(() -> "Size of Pool : "+sizeAtMoment);
        assertNotNull(sizeAtMoment);
        assertEquals(1,MaxAtMoment);
//============================ Task with priority : 3 ====================================================
        Thread.sleep(2000); // waiting 2 second to clean the queue
        Callable<String> callableOther = () -> { return "hello world";};
        Task Other = Task.createTask(callableOther,TaskType.OTHER);
        assertEquals(Other.getPriorityNumber(),3);
        assertEquals(Other.getCaltask(),callableOther);
        Future RsltOther = customExecutor.submit(Other);
        String hello = (String) RsltOther.get();
        assertEquals(hello,"hello world");
        logger.info(()-> "Output of the Task Other : "+hello);
        logger.info(()->"Current maximum priority = " +customExecutor.getCurrentMax());
//============================ Third Task ================================================================
        final int sum;
        try {
            sum = (int) sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        logger.info(() -> "Sum of 1 through 10 = " + sum);
        Callable<Double> callable1 = () -> {
            return 1000 * Math.pow(1.02, 5);
        };
        Future calcul = customExecutor.submit(callable1,TaskType.COMPUTATIONAL);
        Future priceTask = customExecutor.submit(() -> {
            return 1000 * Math.pow(1.02, 5);
        }, TaskType.COMPUTATIONAL);
        assertEquals(calcul.get(),priceTask.get());
        final Double totalPrice;
        final String reversed;
        try {
            totalPrice = (Double) priceTask.get();
            assertEquals(totalPrice,calcul.get());
            reversed = (String) reverseTask.get();
            assertEquals(reversed,"ZYXWVUTSRQPONMLKJIHGFEDCBA");
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info(() -> "Reversed String = " + reversed);
        logger.info(() -> String.valueOf("Total Price = " + totalPrice));
        logger.info(() -> "Current maximum priority = " +customExecutor.getCurrentMax());
        assertEquals(customExecutor.getSizeQueue(),0);
        customExecutor.gracefullyTerminate();
    }
}