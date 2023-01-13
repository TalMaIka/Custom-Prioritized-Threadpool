/**

 Class representing a task with a type and a possible return value of generic type T.
 @param <T> The type of the task's return value.
 */
package PartB;
import java.util.concurrent.*;

public class Task <T> {
    /** The type of the task */
    private TaskType Tp;

    /** The callable task */
    private Callable<T> caltask;

    /** The future value of the task */
    private Future<T> value;

    /**
     * Constructor for creating a task with a specific task type.
     * @param CallTask the callable task
     * @param Tp the type of the task
     */
    private Task(Callable<T> CallTask, TaskType Tp) {
        this.caltask = CallTask;
        this.Tp = Tp;
    }

    /**
     * Constructor for creating a task with the default task type of "OTHER".
     * @param CallTask the callable task
     */
    private Task(Callable<T> CallTask) {
        this.caltask = CallTask;
        this.Tp = TaskType.OTHER;
    }

    /**
     * Factory method that provides a way of safely creating tasks.
     * @param CallTask the callable task
     * @param Tp the type of the task
     * @return a new task with the specified task type and callable task.
     */
    public static <T> Task<T> createTask(Callable<T> CallTask, TaskType Tp){
        if(CallTask!=null && Tp == null){
            return new Task<T>(CallTask);
        }
        else if(CallTask!=null && Tp != null){
            return new Task<T>(CallTask,Tp);
        }
        System.out.println("Error in Task creation.");
        return null;
    }

    /**
     * Gets the type of the task.
     * @return the task's type
     */
    public TaskType getType(){

        return Tp;
    }

    /**
     * Gets the priority value of the task's type.
     * @return the priority value of the task's type
     */
    public int getPriorityNumber(){

        return Tp.getPriorityValue();
    }

    /**
     * Gets the callable task.
     * @return the callable task
     */
    public Callable<T> getCaltask() {
        return caltask;
    }

    /**
     * Sets the future value of the task.
     * @param future the future value of the task
     */
    public void setFuture(Future future) {
        this.value = future;
    }

    /**
     * Returns a string representation of the task.
     * @return a string representation of the task
     */
    @Override
    public String toString(){
        return "Task of type : "+Tp+" with a priority of :"+Tp.getPriorityValue();
    }
}