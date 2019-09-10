public class testing
{
  public static void main(String[] args) {
    int count = 0;
    float floatcount = 0.0f;

    float total = 0f;
    for(int i = 0; i < 10; i++)
    {
      count++;
      floatcount += 0.1f;
    }

    System.out.print("Count " + count+ "\n");
    System.out.print("Float Count " + floatcount + "\n");

  }


}
