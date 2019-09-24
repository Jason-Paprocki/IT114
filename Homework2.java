import java.util.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Homework2
{

  public static void main(String[] args)
  {
    //create queue
    Queue<String> StringQueue = new LinkedList<>();

     StringQueue.add("Element 1");
     StringQueue.add("Element 2");
     StringQueue.add("Element 3");
     StringQueue.add("Element 4");
     StringQueue.add("Element 5");
     

     //peek at element 1
     System.out.println(StringQueue.peek());

     //removing an element of the Queue
     //returns NoSuchElementException if the queue is empty
     String removed = StringQueue.remove();
     System.out.println("Removed: " + removed + "\nnew queue is " + StringQueue);

     //poll is the same as remove
     //returns null if the queue is empty
     String polled = StringQueue.poll();
     System.out.println("Polled: " + polled + "\nnew queue is " + StringQueue);


     // Check is a Queue is empty
     System.out.println("is StringQueue empty? : " + StringQueue.isEmpty());

     // Find the size of the Queue
     System.out.println("Size of StringQueue : " + StringQueue.size());

     // Check if the Queue contains an element
     String element = "Element 4";
     if(StringQueue.contains(element))
     {
       System.out.println("StringQueue contains " + element);
      }
      else
      {
          System.out.println("StringQueue doesn't contain " + element);
      }

      System.out.println(StringQueue.toString());

  }


  //overrriding the tostring
  public String toString()
  {
    //so somehow this works from stack overflow
    return super.toString();

    //but this is my atempt that I made that doesnt work
    /*
    String s = "";
    for (String item : StringQueue)
    {
      s += item;
      s += " ";
    }
    return s;
    */

  }
}
