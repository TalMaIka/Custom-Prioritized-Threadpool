# Ex2-OOP

## About the project
This is the second project under 'OOP Course', this excercise splits into two main parts,  
### Part - A
The part is about file making with random number of lines in each file and using the "File making algorithm" trough three ways of action and the amount of time each action took.   
   * Using 'File making algo' by basic function using the main thread.
   * Using 'File making algo' by main thread.
   * Using 'File making algo' by main thread.

### Authors
* [Tal Malka](https://github.com/TalMaIka)    
* [Yann Chich](https://github.com/yannchich)

 ## Project Description
 
 ![Screenshot](https://i.ibb.co/mJRbB2X/Diagram.png)     
 The image above breaks down the project by classes interfaces and methods.
 
 ### ConcreteMember
 This class resembles the 'Observerable' under the 'Observer' Design Pattern, ConcreteMembers gets updates and holds a shallow copy of the same Database there 'GroupAdmin' object holds.
 
 ### GroupAdmin
 This class resembles the 'Observers' under the 'Observer' Design Pattern, GroupAdmin notifies every member that registered to the group that there was an 
 update/change been made on the 'UndoableStringBuilder' object it holds.
 
 
 ### Further explanation + how to run our project.
 
 As shown 'GroupAdmin' implements 'Sender' and 'ConcreteMember' implements 'Member' Interface.
 'GroupAdmin' resembles the 'Observer' who sends the updates and 'ConcreteMember' resembles the 'Observerable' who gets updates.
 We will explain that project as an Youtube channel: 
 * 'UndoableStringBuilder' -> as Video   
 * 'GroupAdmin' -> as Channel Owner      
 * 'ConcreteMember' -> As User who subscribed to the channel.
 
 * First we create a "Channel" named ABC.      
   ```GroupAdmin ABC = new GroupAdmin();```
   
 *  We'll create a "Member" named FirstUser.     
 ```ConcreteMember FirstUser = new ConcreteMember("User");```  
   
   
 *  Than We'll make the registration proccess to the channel.    
 ```ABC.register(FirstUser);```

 * The procces will make sure that every change/update made by the channel owner will get to the registered members.
   For example we will append a string by ABC.append("First Video") (resembles to new video beeing uploaded to the channel)   
 ```ABC.append("First Video");```
   
 * Then the method execured after every change made notifyMembers()   
    ```for (Member m:MemberList) {m.update(USB);}```    
    
 That will make sure that the members registered to the channel will be notified in every update made.

### Time complexity
GroupAdmin methods ->
  ```
  register() -> O(1)
  unregister() -> 0(n)
  undo() -> O(1)
  notifyMembers() -> O(m) -> m - MemberList size.
  
  append(), delete(), insert() -> Are Java StringBuilder built-in method.
  https://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html
  ```
  
  Concretemember method ->    
  
  ```update() -> O(1)```


### Testing
Using JUnit testing we made sure that every method works as intended.   
There are the JUnit files:
![Screenshot](https://i.postimg.cc/vB4wGF4S/Tests.png)

## Version History

* 0.2 - Utillazaion of 'UndoableStringBuilder' based on 'Observer OOP'
    * :speech_balloon: Notifing members on changes made for the 'USB' they ase registered for.
    
* 0.1 - Recreation of the StringBuilder -> UndoableStringBuilder including:
    * append(), delete(), insert(), replace(), reverse(), toString()
    * undo() -> Main subject of 0.1
