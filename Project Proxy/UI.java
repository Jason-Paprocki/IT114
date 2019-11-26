import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		frame.setSize(1500, 500);
		frame.setLocation(430, 100);
		JPanel panel = new JPanel();
		frame.add(panel);

		//label to specify server
		JLabel lbl = new JLabel("Select one server ");
		lbl.setVisible(true);
		panel.add(lbl);

		//add servers here
		HashMap<String, String> addresses  = new HashMap<>();
		addresses.put("Server 1", "128.235.211.21");

		//show choices with a combo box
		String[] choices = { "Server 1","Server 2", "Server 3","Server 4","Server 5","Server 6"};
		JComboBox<String> cb = new JComboBox<String>(choices);
		cb.setVisible(true);
		panel.add(cb);

		//show button for selecting
		JButton btn = new JButton();
		btn.setText("CONNECT");
		panel.add(btn);
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
						Client myServer = new Client(ipAddress, 443);
						myServer.listen();
					}
				};
				connectionThread.start();
			}
		});
		//packs the frame neatly
		frame.pack();
	}
}
