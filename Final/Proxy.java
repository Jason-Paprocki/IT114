import java.util.*;
import java.net.*;
import java.io.*;

public class Proxy implements Runnable
{

  private ServerSocket serverSocket;
  private volatile boolean running = true;
  static ArrayList<Thread> servicingThreads;

  public static void main(String[] args)
  {
    Proxy myProxy = new Proxy(8080);
    myProxy.listen();
  }

  public Proxy(int port)
  {
    servicingThreads = new ArrayList<>();

    new Thread(this).start();

    try
    {
      serverSocket = new ServerSocket(port);

      System.out.println("Wating for client on port " + serverSocket.getLocalPort());
      running = true;
    }
    catch (SocketException se)
    {
      System.out.println("Socket Exception when connecting to client");
      se.printStackTrace();
		}
		catch (SocketTimeoutException ste)
    {
      System.out.println("Timeout occured while connecting to client");
		}
    catch (IOException io)
    {
      System.out.println("IO exception when connecting to client");
    }
  }

  public void listen()
  {
    while(running)
    {
      try
      {
        Socket socket = serverSocket.accept();

        Thread thread = new Thread(new RequestHandler(socket));

        servicingThreads.add(thread);
        thread.start();
      }
      catch(SocketException se)
      {
        System.out.println("Server closed");
      }
      catch(IOException io)
      {
        io.printStackTrace();
      }
    }
  }

  public void run()
  {

  }
}