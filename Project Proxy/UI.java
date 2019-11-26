import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

		JLabel lbl = new JLabel("Select one server ");
		lbl.setVisible(true);

		panel.add(lbl);

		String[] choices = { "CHOICE 1","CHOICE 2", "CHOICE 3","CHOICE 4","CHOICE 5","CHOICE 6"};

		JComboBox<String> cb = new JComboBox<String>(choices);

		cb.setVisible(true);
		panel.add(cb);

		JButton btn = new JButton();
		btn.setText("CONECT");
		panel.add(btn);

		btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				server = choice;
			}

		});

	}
}
