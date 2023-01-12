
## Project subject
This is the second project under 'OOP Course', this excercise splits into two main parts,  
### Part - A
The part is about file making with random number of lines in each file and using the "File making algorithm" trough three ways of action and the amount of time each action took.   
   * Using algo to count line in a text file by basic function.
   * Using algo to count line in a text file by using a thread.
   * Using algo to count line in a text file by using a threadpool.     
   
 ### Part - B
 In this part our main target was to create something that's "missing" from java functions and it was to create a Threadpool using recyceled threads and to send tasks  into the threadpool to be executed by the're priority.  

### Authors
* [Tal Malka](https://github.com/TalMaIka)    
* [Yann Chich](https://github.com/yannchich)

 ## Part-A Description
 
 ![Screenshot](https://iili.io/HYIuQFs.png)     
 The image above is breakdown diagram about part A.
 
 ### Ex2_1
 This class holds 5 main methods, including: 
 * createTextFiles(int __n__, int __seed__, int __bound__) -> __n__ - Number of files , __seed__ - generate a generator of random numbers , __bound__ - limit of lines
 * getNumOfLines(String[] __fileNames__) -> Returns num of lines.
 * getNumOfLinesThreads(String[] __fileNames__) -> Returns num of lines using a thread.
 * getNumOfLinesThreadsPool(String[] __fileNames__) -> Returns num of lines using a threadpool.
 * deleteFiles(String[] __filenames__) -> Deletes the created files.
 
 ### ThreadLines
 This class extends Thread, overriding the run() function to use as a thread by returning the number of lines in a file.
 
 ### ThreadLinesCallable
 This class extends Callable, overriding the call() function to use it as a Callable object (Thread that returns value). it's return the number of lines in a file.We use a Future type array to save every return of the Threads.
 
 ### Further project explanation
 
  We can say that the main idea here was to see the pros and cons of Thread, Threadpool and basic function abllities and consumptions. 
  As already said we can slpit the explanation into three parts:
  ```
        long startTime = System.currentTimeMillis();     
        int sum = getNumOfLines(namefile); / getNumOfLinesThreads(namefile) / getNumOfLinesThreadsPool(namefile)   
        long endTime = System.currentTimeMillis();      
        logger.info(()->"Time calculation : "+(endTime - startTime)+" ms with a total of :"+sum+" lines.");
 ```   
 * These are the results
 ![Screenshot](https://iili.io/HYIOWRR.png)     
 
 As we can see threads are not only in theory should preform better time wise, it actually happens and we see the power of threads working on with this project.
The main reason for these results is that a thread is able to execute in parallel with other threads, which means that it can use several cores of a processor to      perform tasks simultaneously.
  
### Testing
Using JUnit testing we made sure that every method works as intended and the time stamps and calculation was right for the tasks given.   


 ## Part-B Description
  ![Screenshot](https://iili.io/HYuHOV2.png) 
  The image above is breakdown diagram about part B.
    
  ### TaskType
  TaskType is a enum class used for classification and proirity of the tasks that should be added to "PriorityBlockingQueue" in the future.   
  Including the methods:
  * getPriorityValue() -> Returns the priority value of a task.
  * getType() -> Returns the task type.
  * validatePriority (int __priority__) -> Checks for valid priority value.
  * setPriority(int __priority__) -> Sets priority to a task.
  
  ### Task
  Task class main subject is to wrap and create a Task that will be inserted to a PriorityBlockingQueue working with Threadpool.      
  Task has three fields -> TaskType __Tp__ , Callable<T> __caltask__ , Future<T> __value__
  Including these methods:
  * getType() -> Returns the 'TaskType' of a Task.
  * getPriorityNumber() -> Returns the priority number of a Task.
  * getCaltask() -> Retrurns the Callable object.
  * setFuture(Future __future__) -> Attaches a Future object to 'catch' the returned value that the Callable object will return.
  
  ### CustomExecutor
  CustomExecutor class made to act as a Threadpool for the 'Tasks' including 'TaskType' mentioned above.      
  CustomExecutor has four fields -> PriorityBlockingQueue<Runnable> __queue__ , ThreadPoolExecutor __executor__ , int __processors__ , int __MaxPriority__    
  Including these methods:     
  * submit(Callable<T> __calTask__, TaskType __Tp__) -> Adds a 'Task' to the Threadpool using Callable object and TaskType.     
  * submit(Task<T> __task__) -> Adds a 'Task' to the Threadpool using only Task object.   
  * gracefullyTerminate() -> Will terminate the threadpool as soon as it the whole queue submited to it.
  * getSizeQueue() -> Returns the queue size.
  * updateMax(Task __task__) -> Updates the last sbmited Task with the highest priority.
  * getCurrentMax() -> using updateMax(Task __task__) Will return the highest proirity task in the queue.
  
One of the project reqirements was to write about the difficulties in the journey of making it happen and how we managed to get troght it,     
the main issue here was that using the CustomExecutor as a threadpool and with it PriorityBlockingQueue that will send Tasks to the threadpool by the're TaskType priority order.   
unfortunately it tool us longer than expected but finally we did managed to get trough it by adding the inner class I'm about to present below.
  
  #### TaskComparator
  CustomExecutor it's an  inner class that came to solve the main issue of the ptoject, the Casing issue that appears when trying to add the PriorityBlockingQueue to the CustomExecutor (tasks threadpool) and it lacked the Comperable interface and didn't had the abbility to get the [__CustomExecutor__ (tasks threadpool)] and [__PriorityBlockingQueue__ (Tasks queue sorted by TaskType)] to work together.     
  by implementing Comperator interface and overriding the compareTo method and using the fundementals of 'Extender Design pattern' in this situation we extended the CustomExecutor abbilities and made them work together. 
 
    ##### About the flexibility and performe of the code
  
  ### Testing
    add.
