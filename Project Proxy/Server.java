import java.io.*;
import java.util.*;
import java.net.*;

public class Server
{
  public static void main(String[] args)
  {

    try
    {

      int port = Integer.parseInt(args[0]);

      System.out.println("Starting proxy on port: " + port);
      runserver(port);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }


  public static void runserver(int port) throws IOException
  {
    ServerSocket serverSocket = new ServerSocket(port);

    // Create buffers for client-to-server and server-to-client communication.
    // We make one final so it can be used in an anonymous class below.
    // Note the assumptions about the volume of traffic in each direction...
    final byte[] request = new byte[1024];
    byte[] reply = new byte[4096];


    while (true)
    {
      //wait for connection on local port
      Socket client = serverSocket.accept();

      //initialize server for connection later
      Socket outserver = null;

      //gets data from the client
      final InputStream from_client = client.getInputStream();
      //gets data to the client
      final OutputStream to_client= client.getOutputStream();

      //makes a connection to the outside server
      try
      {
        outserver = new Socket("localhost", port);
      }

      //tells the client that the connection to that outside server can not be made
      catch (IOException e)
      {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(to_client));
        out.println("Proxy server cannot connect" + "\n"+ e);
        out.flush();
        client.close();
        continue;
      }
    }





  }
}
