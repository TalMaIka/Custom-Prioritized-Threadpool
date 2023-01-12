# Ex2-OOP

## About the project
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

# About the Part-A : 

 ## Project Description
 
 ![Screenshot](https://iili.io/HYIuQFs.png)     
 The image above is breakdown diagram about part A.
 
 ### Ex2_1
 This class holds 5 main methods, including: 
 * createTextFiles(int __n__, int __seed__, int __bound__) -> __n__ - Number of files , __seed__ - lowest num of lines , __bound__ - max num of lines.
 * getNumOfLines(String[] __fileNames__) -> Returns num of lines.
 * getNumOfLinesThreads(String[] __fileNames__) -> Returns num of lines using a thread.
 * getNumOfLinesThreadsPool(String[] __fileNames__) -> Returns num of lines using a threadpool.
 * deleteFiles(String[] __filenames__) -> Delets the created files.
 
 ### ThreadLines
 This class extends Thread, to use as a thread by returning the number of lines in a file.
 
 ### ThreadLinesCallable
 This class extends Callable, overriding the call() function to use it as a Callable object (Thread that returns value).
 
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
  
  
### Testing
Using JUnit testing we made sure that every method works as intended and the time stamps and calculation was right for the tasks given.   

