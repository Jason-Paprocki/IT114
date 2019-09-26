import java.util.*;
import java.util.Random;

public class Homework1
{
  public static void main(String[] args)
  {
    List<String> myStringList = new ArrayList<String>();
    //String[] myString = new String[3];

    myStringList.add("Eel");
    myStringList.add("Cat");
    myStringList.add("Dog");
    myStringList.add("Fish");
    myStringList.add("Fish");
    myStringList.add("Ant");

    //print OG list
    System.out.println("OG list: " + myStringList);
    //sort
    Collections.sort(myStringList);
    System.out.println("Sort: " + myStringList);
    //reverse sort
    Collections.reverse(myStringList);
    System.out.println("Reverse: " + myStringList);
    //shuffle
    Collections.shuffle(myStringList);
    System.out.println("Shuffle: " + myStringList);


    List<Integer> myIntList = new ArrayList<Integer>(10);
    //add index to match index
    int total = 0;
    for (int i = 0; i < 10; i++)
    {
      myIntList.add(i, i);
      total += myIntList.get(i);
    }

    System.out.println(total);

    for (int i = 0; i < 10; i++)
    {
      if (myIntList.get(i)%2 == 0)
      {
        System.out.println(myIntList.get(i) + ": Its even");
      }
      else
      {
        System.out.println(myIntList.get(i) + ": Its odd");
      }
    }

/*
    System.out.println(myString[0] + myString[1]);

    String temp = myString[0];
    myString[0] = myString[1];
    myString[1] = temp;
*/


    System.out.println("Un-shuffled list " + myStringList);

    Random rando = new Random();

    int n = myStringList.size();
    for (int i = n-1; i > 0; i--)
    {
      // Pick a random index from 0 to i
      int index = rando.nextInt(i+1);

      // Swap arr[i] with the element at random index
      String temp = myStringList.get(i);
      myStringList.set(i, myStringList.get(index));
      myStringList.set(index, temp);
    }

    System.out.println("Shuffled list " + myStringList);


  }
}
