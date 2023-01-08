package PartB;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;


import java.util.concurrent.*;

public class CustomExecutor {
    private final ExecutorService executor;
    private PriorityBlockingQueue<Runnable> queue;

    public CustomExecutor() {
        this.queue = new PriorityBlockingQueue<>();
        this.executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, queue);
    }
//    public <T> Task<T> submit(Callable<T> calTask, TaskType Tp){
//        Task<T> newTask = Task.createTask(calTask,Tp);
//        executor.submit(newTask);
//        return newTask;
//    }
    public <T> Task<T> submit(Task<T> task){
        Future future = executor.submit((Runnable) task);
        return task;
    }

    public void gracefullyTerminate() {
        // Shutdown the executor
        executor.shutdown();
    }
}
