public class testing
{
  public int myPublicInt;
  private int myPrivateInt;
  protected int myProtectedInt;

    public static void main(String[] args) {
      Myfirstclass myfirstclassobject = new Myfirstclass();
      myfirstclassobject.myPublicInt = 1;
      System.out.println(myfirstclassobject.myPublicInt);
    }


}
