import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import javax.swing.*;

class DeleteFrame extends JFrame implements ActionListener{
	File textFile;
	FileReader in;
	FileWriter out;
	BufferedReader readFile; 
	BufferedWriter writeFile;
	String[] arr;
	String lineOfText;
    JTextField removeTextField;
    JButton back,deleteButton;
    JPanel topPanel,bottomPanel, deletePanel, removeTextFieldPanel;
    JTextArea area;
    JScrollPane scroll;
    ApplicationFrame mainframe;
    DeleteFrame() {//delete frame allows user to completely delete items from inventory
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "By not properly exiting, your inventory could be wiped","Confirmation",JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        topPanel = new JPanel();

        topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.X_AXIS));
        back=new JButton("Back");
		back.addActionListener(a->{//back button closes current frame and creates a new instance of main frame
			mainframe = new ApplicationFrame();this.dispose();
		});
		deleteButton = new JButton("Remove");
		deleteButton.addActionListener(this);
        removeTextFieldPanel = new JPanel();
        removeTextField = new JTextField(30);
        removeTextFieldPanel.add(removeTextField);
        topPanel.add(back);
        topPanel.add(removeTextFieldPanel);
        topPanel.add(deleteButton);


		try {
			textFile = new File("inventory.txt");//reads from inventory.txt
			in = new FileReader(textFile);
			readFile = new BufferedReader(in);
			lineOfText = readFile.readLine();
			if(lineOfText==null) {//if inventory.txt is empty it will start with an extra space
				lineOfText = " ";
			}
				arr = lineOfText.split(" ");	
			readFile.close();
			in.close();
		}
		catch(FileNotFoundException e){
			JOptionPane.showMessageDialog(null, "File Not Found","Error",JOptionPane.ERROR_MESSAGE);
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, "IOException","Error",JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "An error has occured","Error",JOptionPane.ERROR_MESSAGE);

		}
        area = new JTextArea(30,20);
		area.setEditable(false);
		for(int i = 0; i<arr.length-1;i=i+2) {
			area.setText(area.getText() + "\n" + arr[i] + " " + arr[i+1]);
		}
		area.setEditable(false);
        scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        bottomPanel = new JPanel();
        bottomPanel.add(scroll);

        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        getContentPane().add(topPanel);
        getContentPane().add(scroll);

        setTitle("Delete");
        setSize(500,300);
        setLocationRelativeTo(null);
        setVisible(true);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			out = new FileWriter(textFile);
			writeFile = new BufferedWriter(out);
			boolean itemConfirmed = false;
			for(int i = 0; i < arr.length; i=i+2) {
				if(removeTextField.getText().replaceAll(" ","").equals(arr[i])) {//finding the item that matches what the user typed in
					itemConfirmed = true;
					int confirm = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete this item?","Confirm",JOptionPane.YES_NO_OPTION);
					if(confirm==JOptionPane.YES_OPTION) {
						arr = removeElement(arr,i);//returns a new array without the element i and i+1
						lineOfText = "";
						for(int j = 0; j < arr.length-1;j=j+2) {
							lineOfText = lineOfText + " " + arr[j] + " " + arr[j+1];//im creating an extra space at beginning
						}
						lineOfText = lineOfText.replaceFirst(" ", "");
						area.setText("");//rewriting JTextArea
						for(int j = 0; j<arr.length-1;j=j+2) {
							area.setText(area.getText() + "\n" + arr[j] + " " + arr[j+1]);
						}
						writeFile.write(lineOfText);//writes lineoftext to the file
						writeFile.close();
						out.close();
					}
				}
			}
			if(itemConfirmed==false) {
				JOptionPane.showMessageDialog(null, "Item cannot be founnd","Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		catch(IOException err) {
			JOptionPane.showMessageDialog(null, "IOException","Error",JOptionPane.ERROR_MESSAGE);
		}

	}
	//this method returns an array without elements i and i + 1
	public static String[] removeElement(String[]arr,int index) {
		String[] copy = new String[arr.length-2];
		for(int i = 0; i<index;i++) {
			copy[i] = arr[i];
		}
		for(int i = index+2;i<arr.length;i++) {
			copy[i-2] = arr[i];
		}
		return copy;
		
	}
}


