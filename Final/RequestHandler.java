import java.util.*;
import java.net.*;
import java.io.*;

public class RequestHandler implements Runnable
{

  Socket clientSocket;
  BufferedReader proxyToClientBr;
  BufferedWriter proxyToClientBw;

  private Thread httpsClientToServer;

  public RequestHandler (Socket clientSocket)
  {
    this.clientSocket = clientSocket;

    try
    {
      this.clientSocket.setSoTimeout(2000);
      proxyToClientBr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			proxyToClientBw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }
    catch (IOException io)
    {
      io.printStackTrace();
    }
  }

  public void run()
  {
    // Get Request from client
		String requestString;

		try
    {
			requestString = proxyToClientBr.readLine();
		}
    catch (IOException e)
    {
      System.out.println("Error reading request from client");
      e.printStackTrace();
			return;
		}

    // Parse out URL
    System.out.println("Reuest Received " + requestString);

    // Get the Request type
    String request = requestString.substring(0,requestString.indexOf(' '));

    // remove request type and space
    String urlString = requestString.substring(requestString.indexOf(' ')+1);

    // Remove everything past next space
    urlString = urlString.substring(0, urlString.indexOf(' '));

    // Prepend http:// if necessary to create correct URL
    if(!urlString.substring(0,4).equals("http"))
    {
      String temp = "http://";
      urlString = temp + urlString;
    }
    else
    {
      System.out.println("HTTP GET for : " + urlString + "\n");
    	sendToClient(urlString);
    }
  }

  private void sendToClient(String urlString)
  {
    try
    {
      // Compute a logical file name as per schema
			// This allows the files on stored on disk to resemble that of the URL it was taken from
			int fileExtensionIndex = urlString.lastIndexOf(".");
			String fileExtension;

			// Get the type of file
			fileExtension = urlString.substring(fileExtensionIndex, urlString.length());

			// Get the initial file name
			String fileName = urlString.substring(0,fileExtensionIndex);


			// Trim off http://www. as no need for it in file name
			fileName = fileName.substring(fileName.indexOf('.')+1);

			// Remove any illegal characters from file name
			fileName = fileName.replace("/", "__");
			fileName = fileName.replace('.','_');

			// Trailing / result in index.html of that directory being fetched
			if(fileExtension.contains("/")){
				fileExtension = fileExtension.replace("/", "__");
				fileExtension = fileExtension.replace('.','_');
				fileExtension += ".html";
			}

			fileName = fileName + fileExtension;























    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }





}
