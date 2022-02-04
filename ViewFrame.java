import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import javax.swing.*;

public class ViewFrame extends JFrame {
	File textFile;
	FileReader in;
	BufferedReader readFile;
	JPanel panel;
	JScrollPane scroll;
	JTextArea area;
	String[] arr;
	String lineOfText;
	JButton back;
	ApplicationFrame mainframe;
	ViewFrame(){//this is the frame where the stock can be viewed from
		addWindowListener(new WindowAdapter() {//detects when users click x
			public void windowClosing(WindowEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "By not properly exiting, your inventory could be wiped","Confirmation",JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(300,300);
		setVisible(true);
		setLocationRelativeTo(null);
		try {
			textFile = new File("inventory.txt");//reads from a text file called "inventory"
			in = new FileReader(textFile);
			readFile = new BufferedReader(in);
			lineOfText = readFile.readLine(); //stores the text into a single string
			if(lineOfText==null) {//if file is empty it will fill the string with a single space which prevents the program from crashing
				lineOfText = " ";
			}
			arr = lineOfText.split(" ");	//tracks inventory within an array 
			//even number indexes = itemid
			//odd number indexes = itemamount
			readFile.close();//close filereaders
			in.close();
		}
		catch(FileNotFoundException e){//file not found exception
			JOptionPane.showMessageDialog(null, "File not found","Error",JOptionPane.ERROR_MESSAGE);
		}
		catch(IOException e){//io exception
			JOptionPane.showMessageDialog(null, "IOException","Error",JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e) {//catches everything else
			JOptionPane.showMessageDialog(null, "An error has occured","Error",JOptionPane.ERROR_MESSAGE);
		}
        area = new JTextArea(30,20);
		for(int i = 0; i<arr.length-1;i=i+2) {
			area.setText(area.getText() + "\n" + arr[i] + " " + arr[i+1]);//sets the textarea
		}
		area.setEditable(false);
		back = new JButton("back");
		back.addActionListener(a->{//back button closes current frame and brings user back to the main frame
			mainframe = new ApplicationFrame();this.dispose();
		});
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(scroll);
        panel.add(back);
        setContentPane(panel);	
	}
}
