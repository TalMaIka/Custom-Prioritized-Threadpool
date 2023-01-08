package PartB;

import java.util.Comparator;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class Task <T> implements Comparator<Task<T>> {
    // Filed that accepts lambda function as a parameter.
    private TaskType Tp;
    private Callable<T> caltask;
    private Future<T> value;


    // Two Task constructors.
    private Task(Callable<T> caltask, TaskType Tp) {
        this.caltask = caltask;
        this.Tp = Tp;
    }
    private Task(Callable<T> caltask) {
        this.caltask = caltask;
        this.Tp = TaskType.OTHER;
    }

     //Factory method that provides a way of safe Tasks creation.
    public static <T> Task<T> createTask(Callable<T> caltask, TaskType Tp){
        if(caltask!=null && Tp == null){
            return new Task<T>(caltask);
        }
        else if(caltask!=null && Tp != null){
            return new Task<T>(caltask,Tp);
        }
        System.out.println("Error in Task creation.");
        return null;
    }

    //3. Represents a task with a TaskType and may return a value of some type
    public TaskType getType(){
        return Tp;
    }
    public int getPriorityNumber(){
        return Tp.getPriorityValue();
    }
    public T get() {
        try {
            return value.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    public T get(long timeout, TimeUnit unit) {
        try {
            return value.get(timeout,unit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public int compare(Task<T> o1, Task<T> o2) {
        if(o1.Tp.getPriorityValue() > o2.Tp.getPriorityValue()){
            return 1;
        }
        if(o1.Tp.getPriorityValue() < o2.Tp.getPriorityValue()){
            return -1;
        }
        return 0;
    }
}
