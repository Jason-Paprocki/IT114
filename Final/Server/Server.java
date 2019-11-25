/*

-----------------------------------------------------------------------------------------------
STOLEN FROM THIS MAN ON GITHUB
https://github.com/stefano-lupo/Java-Proxy-Server/blob/master/src/RequestHandler.java

-----------------------------------------------------------------------------------------------
*/

import java.util.*;
import java.net.*;
import java.io.*;

public class Server implements Runnable
{

  private ServerSocket serverSocket;
  private volatile boolean running = true;
  static ArrayList<Thread> servicingThreads;

  public static void main(String[] args)
  {
    Server myServer = new Server(8080);
    myServer.listen();
  }

  public Server(int port)
  {
    servicingThreads = new ArrayList<>();

    new Thread(this).start();

    try
    {
      serverSocket = new ServerSocket(port);
      System.out.println("Wating for client to connect on port " + serverSocket.getLocalPort());
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

@Override
  public void run()
  {

  }
}
