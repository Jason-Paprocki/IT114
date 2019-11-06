import java.util.*;
import java.net.*;
import java.io.*;
import java.net.ServerSocket;

public class RequestHandler implements Runnable
{
	/**
	 * Socket connected to client passed by Proxy server
	 */
	Socket clientSocket;

	/**
	 * Read data client sends to proxy
	 */
	BufferedReader proxyToClientBr;

	/**
	 * Send data from proxy to client
	 */
	BufferedWriter proxyToClientBw;


	/**
	 * Thread that is used to transmit data read from client to server when using HTTPS
	 * Reference to this is required so it can be closed once completed.
	 */
	private Thread httpsClientToServer;


	/**
	 * Creates a RequestHandler object capable of servicing HTTP(S) GET requests
	 * @param clientSocket socket connected to the client
	 */
	public RequestHandler(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
		try
		{
			this.clientSocket.setSoTimeout(2000);
			proxyToClientBr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			proxyToClientBw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		}
		catch (IOException e)
		{
			System.out.println("Error on IO Exception");
			e.printStackTrace();
		}
	}

	/**
	 * Reads and examines the requestString and calls the appropriate method based
	 * on the request type.
	 */
	@Override
	public void run() 
	{
		// Get Request from client
		String requestString;
		try
		{
			requestString = proxyToClientBr.readLine();
			System.out.println(requestString);
			sendToClient(requestString);
			System.out.println("Beep");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("Error reading request from client");
			return;
		}
	}
	
	public void sendToClient(String url) throws IOException
	{
		clientSocket = new Socket("192.168.0.226", 8080);
		proxyToClientBw.write(url);
		proxyToClientBw.flush();

	}















}