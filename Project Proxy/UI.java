import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UI
{

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("A Simple GUI");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setLocation(430, 100);

		JPanel panel = new JPanel();

		frame.add(panel);

		JLabel lbl = new JLabel("Select one server and click OK");
		lbl.setVisible(true);

		panel.add(lbl);

		String[] choices = { "CHOICE 1","CHOICE 2", "CHOICE 3","CHOICE 4","CHOICE 5","CHOICE 6"};

		JComboBox<String> cb = new JComboBox<String>(choices);

		cb.setVisible(true);
		panel.add(cb);

		JButton btn = new JButton("OK");
		panel.add(btn);


	}
}
