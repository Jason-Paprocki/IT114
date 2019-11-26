/*

-----------------------------------------------------------------------------------------------
STOLEN FROM THIS MAN ON GITHUB
https://github.com/stefano-lupo/Java-Proxy-Server/blob/master/src/RequestHandler.java

-----------------------------------------------------------------------------------------------
*/
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.imageio.*;

public class RequestHandler implements Runnable {

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

	private Thread httpsClientToServer;

	/**
	 * Creates a ReuqestHandler object capable of servicing HTTP(S) GET requests
	 * @param clientSocket socket connected to the client
	 */
	public RequestHandler(Socket clientSocket){
		this.clientSocket = clientSocket;
		try{
			this.clientSocket.setSoTimeout(200000);
			proxyToClientBr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			proxyToClientBw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		}
		catch (IOException e) {
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
		try{
			requestString = proxyToClientBr.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error reading request from client");
			return;
		}


		System.out.println("Request Received " + requestString);
		sendPageToClient(requestString);
	}


	/*
	 * Handles HTTPS requests between client and remote server
	 * @param urlString desired file to be transmitted over https
	 */
	private void sendPageToClient(String urlString)
	{
		try
		{
			// Open a socket to the remote server
			Socket proxyToServerSocket = new Socket("192.168.0.175", 8080);
			proxyToServerSocket.setSoTimeout(10000);

			//Create a Buffered Writer betwen proxy and remote
			BufferedWriter proxyToServerBW = new BufferedWriter(new OutputStreamWriter(proxyToServerSocket.getOutputStream()));

			// Create Buffered Reader from proxy and remote
			BufferedReader proxyToServerBR = new BufferedReader(new InputStreamReader(proxyToServerSocket.getInputStream()));

			PrintWriter out = new PrintWriter(proxyToServerSocket.getOutputStream(), true);

			try
			{
				System.out.println("sending to server: " + urlString );
				out.println(urlString);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}



			ClientToServerHttpsTransmit clientToServerHttps =
					new ClientToServerHttpsTransmit(clientSocket.getInputStream(), proxyToServerSocket.getOutputStream());

			httpsClientToServer = new Thread(clientToServerHttps);
			httpsClientToServer.start();

			// Listen to remote server and relay to client
			try {
				byte[] buffer = new byte[4096];
				int read;
				do {
					read = proxyToServerSocket.getInputStream().read(buffer);
					if (read > 0) {
						clientSocket.getOutputStream().write(buffer, 0, read);
						if (proxyToServerSocket.getInputStream().available() < 1) {
							clientSocket.getOutputStream().flush();
						}
					}
				} while (read >= 0);
			}
			catch (SocketTimeoutException e)
			{
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}

			/*if(out != null)
			{
				//out.close();
			}
			// Close Down Resources
			if(proxyToServerSocket != null)
			{
				proxyToServerSocket.close();
			}

			if(proxyToServerBR != null)
			{
				proxyToServerBR.close();
			}

			if(proxyToServerBW != null)
			{
				proxyToServerBW.close();
			}

			if(proxyToClientBw != null)
			{
				proxyToClientBw.close();
			}*/
		}
		catch (SocketTimeoutException e)
		{
			String line = "HTTP/1.0 504 Timeout Occured after 10s\n" +
					"User-Agent: ProxyServer/1.0\n" +
					"\r\n";
			try
			{
				proxyToClientBw.write(line);
				proxyToClientBw.flush();
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
		catch (Exception e)
		{
			System.out.println("Error on HTTPS : " + urlString );
			e.printStackTrace();
		}
	}

	/**
	 * Listen to data from client and transmits it to server.
	 * This is done on a separate thread as must be done
	 * asynchronously to reading data from server and transmitting
	 * that data to the client.
	 */
	class ClientToServerHttpsTransmit implements Runnable
	{

		InputStream proxyToClientIS;
		OutputStream proxyToServerOS;

		/**
		 * Creates Object to Listen to Client and Transmit that data to the server
		 * @param proxyToClientIS Stream that proxy uses to receive data from client
		 * @param proxyToServerOS Stream that proxy uses to transmit data to remote server
		 */
		public ClientToServerHttpsTransmit(InputStream proxyToClientIS, OutputStream proxyToServerOS)
		{
			this.proxyToClientIS = proxyToClientIS;
			this.proxyToServerOS = proxyToServerOS;
		}

		@Override
		public void run(){
			try
			{
				// Read byte by byte from client and send directly to server
				byte[] buffer = new byte[4096];
				int read;
				do
				{
					read = proxyToClientIS.read(buffer);
					if (read > 0)
					{
						proxyToServerOS.write(buffer, 0, read);
						if (proxyToClientIS.available() < 1)
						{
							proxyToServerOS.flush();
						}
					}
				} while (read >= 0);
			}
			catch (SocketTimeoutException ste)
			{
				ste.printStackTrace();
			}
			catch (IOException e)
			{
				System.out.println("Proxy to client HTTPS read timed out");
				e.printStackTrace();
			}
		}
	}


}
