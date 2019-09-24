import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SampleSocketServer2 {
	int port = 3002;
	public SampleSocketServer2() {
	}
	private void start(int port) {
		this.port = port;
		System.out.println("Waiting for client");
		try (ServerSocket serverSocket = new ServerSocket(port);
				Socket client = serverSocket.accept();
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));) {

			System.out.println("Client connected, waiting for message");
			String fromClient = "";
			String toClient = "";
			while ((fromClient = in.readLine()) != null) {
				System.out.println("From client: " + fromClient);
				List<String> reversedInput = Arrays.asList(fromClient.split(""));
				Collections.reverse(reversedInput);
				toClient = String.join("", reversedInput);
				System.out.println("Sending to client: " + toClient);

				if ("kill server".equalsIgnoreCase(fromClient)) {
					out.println("Server received kill command, disconnecting");
					break;
				}
				else {
					out.println(toClient);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("closing server socket");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}



	public static void main(String[] arg) {
		System.out.println("Starting Server");
		SampleSocketServer2 server = new SampleSocketServer2();
    int port = -1;
    if(arg.length > 0)
    {
      try
      {
        port = Integer.parseInt(arg[0]);
      }
      catch(Exception e)
      {
        System.out.println("Invalid Port " + arg[0]);
      }
    }
		server.start(port);
		System.out.println("Server Stopped");
	}
}
