import java.util.*;

public class testing
{
  public static void main(String[] args)
  {
    List<String> myStringList = new ArrayList<String>();
    String[] myString = new String[3];



    myStringList.add("Hello");
    myStringList.add("Bye");
    myStringList.add("Yay");

    //print OG list
    System.out.println(myStringList);
    //sort
    Collections.sort(myStringList);
    System.out.println(myStringList);

    //reverse sort
    Collections.reverse(myStringList);
    System.out.println(myStringList);

    //shuffle
    Collections.shuffle(myStringList);
    System.out.println(myStringList);


    List<Integer> myIntList = new ArrayList<Integer>(10);
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

  }
}
