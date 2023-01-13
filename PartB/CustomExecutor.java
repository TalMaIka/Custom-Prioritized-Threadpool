package PartB;

import java.util.Comparator;
import java.util.concurrent.*;

/**
 * The CustomExecutor class is a custom implementation of an ExecutorService that uses a
 * PriorityBlockingQueue to hold the tasks and a ThreadPoolExecutor to execute them.
 * The tasks are prioritized based on their TaskType's priority value.
 *
 * @param <T> the type of the result of the task
 * @author Yann Chich & Tal Malka
 * @version Final ver.0.1 - 12.01.23
 */
public class CustomExecutor<T> {

    /**
     * The queue that holds the tasks in order of priority
     */
    private PriorityBlockingQueue<Runnable> queue;
    /**
     * The ThreadPoolExecutor that executes the tasks
     */
    private ThreadPoolExecutor executor;
    /**
     * The number of processors available
     */
    private int processors;
    /**
     * The maximum priority value of tasks in the queue
     */
    private int MaxPriority;

    /**
     * Constructs a CustomExecutor with the number of processors available.
     * The queue is initialized with the available processors minus one as the initial capacity,
     * and the TaskComparator as the comparator.
     * The ThreadPoolExecutor is initialized with (processors / 2) as the core pool size,
     * (processors - 1) as the maximum pool size, 300 milliseconds as the keep-alive time,
     * TimeUnit.MILLISECONDS as the time unit, and the queue as the work queue.
     * The maximum priority value is set to 10.
     */
    public CustomExecutor() {
        this.processors = Runtime.getRuntime().availableProcessors();
        this.queue = new PriorityBlockingQueue(processors - 1, new TaskComparator());
        this.executor = new ThreadPoolExecutor(processors / 2, processors - 1, 300, TimeUnit.MILLISECONDS, queue);
        this.MaxPriority = 10;
    }

    /**
     * Submits a callable task and a TaskType to the executor.
     * If the callable task is null, a NullPointerException is thrown.
     * If the queue is empty, the maximum priority is set to the priority value of the TaskType.
     * The task is wrapped in a Task object, and its Future is returned.
     * The maximum priority is also updated with the task's priority number.
     *
     * @param calTask the callable task to be executed
     * @param Tp      the TaskType of the task
     * @return the Future representing the task
     * @throws Exception if the task cannot be submitted
     */
    public <T> Future<T> submit(Callable<T> calTask, TaskType Tp) throws Exception {
        if (calTask == null) {
            throw new NullPointerException();
        }
        if (queue.size() == 0) {
            MaxPriority = Tp.getPriorityValue();
        }
        Task NewTask = Task.createTask(calTask, Tp);
        Future future = executor.submit(NewTask.getCaltask());
        NewTask.setFuture(future);
        updateMax(NewTask);
        return future;
    }

    /**
     * Submits a Task to the executor.
     * If the task is null, a NullPointerException is thrown.
     * If the queue is empty, the maximum priority is set to the priority number of the task.
     * The Future of the task is returned.
     * The maximum priority is also updated with the task's priority number.
     *
     * @param task the task to be executed
     * @return the Future representing the task
     * @throws Exception if the task cannot be submitted
     */
    public <T> Future<T> submit(Task<T> task) throws Exception {
        if (task == null) {
            throw new NullPointerException();
        }
        if (queue.size() == 0) {
            MaxPriority = task.getPriorityNumber();
        }
        Future<T> future = executor.submit(task.getCaltask());
        task.setFuture(future);
        updateMax(task);
        return future;
    }

    /**
     * Gracefully terminates the executor by shutting it down.
     */
    public void gracefullyTerminate() {
        executor.shutdown();
    }

    /**
     * Gets the current maximum priority value of tasks in the queue.
     *
     * @return the current maximum priority value
     */
    public int getCurrentMax() {
        return MaxPriority;
    }

    /**
     * Gets the size of the queue
     *
     * @return the size of the queue
     */
    public int getSizeQueue() {
        return queue.size();
    }

    /**
     * Updates the maximum priority value with the task's priority number if it is less than the current maximum.
     *
     * @param task the task to update the maximum priority with
     */
    private void updateMax(Task task) {
        if (MaxPriority > task.getPriorityNumber()) {
            MaxPriority = task.getPriorityNumber();
        }
    }

    /**
     * The TaskComparator is used to compare tasks based on their priority numbers
     */
    private class TaskComparator implements Comparator<Runnable> {
        @Override
        public int compare(Runnable o1, Runnable o2) {
            if (o1 instanceof Task && o2 instanceof Task) {
                Task task1 = (Task) o1;
                Task task2 = (Task) o2;
                return Integer.compare(task1.getPriorityNumber(), task2.getPriorityNumber());
            }
            return 0;
        }
    }
}
