import javax.swing.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.imageio.*;

public class UI
{

	public static void main(String[] args)
	{
		//crete the Java frame and panel
		JFrame frame = new JFrame("A Simple GUI");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create the new panel		
		JPanel panel = new JPanel();
		frame.add(panel);

		//create box to vertically align things
		Box box = Box.createVerticalBox();
		panel.add(box);

		//label to specify server
		JLabel lbl = new JLabel("Select one server: ");
		lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.add(lbl);

		//add servers here
		HashMap<String, String> addresses  = new HashMap<>();
		addresses.put("Server 1", "192.168.0.224");
		addresses.put("Server 2", "128.235.211.21");

		//show choices with a combo box
		String[] choices = { "Server 1","Server 2", "Server 3","Server 4","Server 5","Server 6"};
		JComboBox<String> cb = new JComboBox<String>(choices);
		cb.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.add(cb);

		//show button for selecting
		JButton btn = new JButton();
		btn.setText("CONNECT");
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.add(btn);


		//onclick action
		btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Thread connectionThread = new Thread()
				{
					@Override
					public void run()
					{
						String whichServer = cb.getSelectedItem().toString();
						String ipAddress = addresses.get(whichServer);
						Client myServer = new Client(ipAddress, 8080);
						myServer.listen();
					}
				};
				connectionThread.start();
			}
		});
		
		//check status
		String server;
		for(int i = 0; i < addresses.size(); i++)
		{
			server = addresses.get("Server " + (i+1)).toString();
			JLabel onlineServer = new JLabel(server + " is " + hostAvailabilityCheck(server));
			onlineServer.setAlignmentX(Component.CENTER_ALIGNMENT);
			box.add(onlineServer);
		}
		
		

		//packs the frame neatly and resizes it
		frame.pack();
		frame.setSize(500, 800);
		frame.setLocation(430, 100);
	}

	public static String hostAvailabilityCheck(String inServer)
    {
		try 
		{
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(inServer, 8080), 2000);
			return "UP";
		}
		catch (IOException ex) 
		{
			ex.printStackTrace();
			return "DOWN";
		}
		
    } 
}
