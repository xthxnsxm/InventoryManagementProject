
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
class AboutFrame extends JFrame{
	ApplicationFrame mainframe;
	JButton back;
	JPanel panel;
	JTextArea area;

	JScrollPane scroll;
	AboutFrame() {//a simple about page that tells u more about the application
		addWindowListener(new WindowAdapter() {//detects when user clicks x
			public void windowClosing(WindowEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you would like you quit?","",JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(300,300);
		setVisible(true);
		setLocationRelativeTo(null);
		back = new JButton("back");
		back.addActionListener(a->{
			mainframe = new ApplicationFrame();this.dispose();
		});
		//code for gui and panel
		area = new JTextArea(30,20);
		area.setEditable(false);
		area.setText("Software created by John Luc and Ethan Sum\n");
		area.setText(area.getText()+ "For any inquiries please contact us at imaginaryemail@gmail.com\n");
		area.setText(area.getText()+ "Created in 2021\n");
		area.setText(area.getText()+ "For instructions please see the \"readme\" text file\n");
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(area);
		panel.add(back);
		setContentPane(panel);
	}

}