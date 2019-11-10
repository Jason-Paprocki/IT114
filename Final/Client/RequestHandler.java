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

public class RequestHandler implements Runnable
{

	/**
	 * Socket connected to client passed by Proxy server
	 */
	Socket clientSocket;

	/**
	 * Read data client sends to proxy
	 */
	BufferedReader clientToClientBr;

	/**
	 * Send data from proxy to client
	 */
	BufferedWriter clientToClientBw;


	/**
	 * Thread that is used to transmit data read from client to server when using HTTPS
	 * Reference to this is required so it can be closed once completed.
	 */=
	private Thread threads;


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
			clientToClientBr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			clientToClientBw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
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
			requestString = clientToClientBr.readLine();
      System.out.println(requestString);
      sendToServer(requestString);

		}
    catch (IOException e)
    {
			e.printStackTrace();
			System.out.println("Error reading request from client");
			return;
		}
	}

	private void sendToServer(String urlString) throws IOException
  {

			// Open a socket to the remote server
			Socket proxyToServerSocket = new Socket("192.168.0.226", 8080);
			proxyToServerSocket.setSoTimeout(5000);


			// Client and Remote will both start sending data to proxy at this point
			// Proxy needs to asynchronously read data from each party and send it to the other party


			//Create a Buffered Writer betwen proxy and remote
			BufferedWriter proxyToServerBW = new BufferedWriter(new OutputStreamWriter(proxyToServerSocket.getOutputStream()));

			// Create Buffered Reader from proxy and remote
			BufferedReader proxyToServerBR = new BufferedReader(new InputStreamReader(proxyToServerSocket.getInputStream()));



			// Create a new thread to listen to client and transmit to server
			ClientToServerHttpsTransmit clientToServerHttps = new ClientToServerHttpsTransmit(clientSocket.getInputStream(), proxyToServerSocket.getOutputStream());

			threads = new Thread(clientToServerHttps);
			threads.start();


			// Listen to remote server and relay to client
			try {
				byte[] buffer = new byte[4096];
				int read;
				do
        {
					read = proxyToServerSocket.getInputStream().read(buffer);
					if (read > 0)
          {
						clientSocket.getOutputStream().write(buffer, 0, read);
						if (proxyToServerSocket.getInputStream().available() < 1)
            {
							clientSocket.getOutputStream().flush();
						}
					}
				} while (read >= 0);
			}
			catch (SocketTimeoutException e) {

			}
			catch (IOException e) {
				e.printStackTrace();
			}


			// Close Down Resources
			if(proxyToServerSocket != null){
				proxyToServerSocket.close();
			}

			if(proxyToServerBR != null){
				proxyToServerBR.close();
			}

			if(proxyToServerBW != null){
				proxyToServerBW.close();
			}

			if(clientToClientBw != null){
				clientToClientBw.close();
			}


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
		public void run()
    {
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
