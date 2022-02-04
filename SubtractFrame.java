import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class SubtractFrame extends JFrame implements ActionListener{
	File textFile;
	FileWriter out;
	BufferedWriter writeFile;
	FileReader in;
	BufferedReader readFile;
	String[] arr;
	String lineOfText;
    JScrollPane scroll;
    JLabel logo, enterstockPrompt, amountPrompt;
    JTextArea area;
    JButton back, subtract;
    JTextField itemField, numberField;
    JPanel addPanel,enterstockPanel,textarea;
    ApplicationFrame mainframe;
    SubtractFrame() {//this frame allows users to subtract amounts of items from inventory
		addWindowListener(new WindowAdapter() {//detects when user clicks the exit button
			public void windowClosing(WindowEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "By not properly exiting, your inventory could be wiped","Confirmation",JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        ImageIcon img = new ImageIcon(getClass().getResource("/nofrills.png"));
        Image image = img.getImage(); // make an Image object from the ImageIcon object
        Image newImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // transform it
        img = new ImageIcon(newImage);


        logo= new JLabel();
        logo.setIcon(img);

        subtract = new JButton("Subtract");
        subtract.addActionListener(this);
        back = new JButton("Back");
		back.addActionListener(a->{//back button closes this frame and reopens the main frame
			mainframe = new ApplicationFrame();this.dispose();
		});
        itemField = new JTextField(10);
        numberField = new JTextField(5);
        enterstockPrompt = new JLabel("Enter stock: ");
        amountPrompt = new JLabel("Amount: ");
        


        
        enterstockPanel = new JPanel();
        enterstockPanel.add(back);
        enterstockPanel.add(enterstockPrompt);
        enterstockPanel.add(itemField);
        enterstockPanel.add(amountPrompt);
        enterstockPanel.add(numberField);
        enterstockPanel.add(subtract);

		try {
			textFile = new File("inventory.txt");//reads from inventory.txt
			in = new FileReader(textFile);
			readFile = new BufferedReader(in);
			lineOfText = readFile.readLine();
			if(lineOfText==null) {
				lineOfText = " ";
			}
				arr = lineOfText.split(" ");	
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
		area = new JTextArea(10,20);
		for(int i = 0; i<arr.length-1;i=i+2) {
			area.setText(area.getText() + "\n" + arr[i] + " " + arr[i+1]);
		}
        scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		area.setEditable(false);
        addPanel = new JPanel();
        addPanel.add(logo);

        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        getContentPane().add(addPanel);
        getContentPane().add(enterstockPanel);
        getContentPane().add(scroll);

        setTitle("Add");
        setSize(500,400);
        setLocationRelativeTo(null);
        setVisible(true);

    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			out = new FileWriter(textFile);
			writeFile = new BufferedWriter(out);
			String itemID;
			int subtractAmount;
			try {
				itemID = itemField.getText().replaceAll(" ", ""); //grabs the item name and removes all spaces
				subtractAmount = Integer.parseInt(numberField.getText());//parses the string from the numberfield
				boolean itemDupe = false;//checks for dupes
				for(int i = 0; i < arr.length-1; i=i+2) {
					if(itemID.equals(arr[i])) {//if itemid is found in the array then subtract from amount
						itemDupe = true;
						if(Integer.parseInt(arr[i+1])-subtractAmount<0) {//catches if number goes below 0
							JOptionPane.showMessageDialog(null, "Item cannot be subtracted below 0","Error",JOptionPane.ERROR_MESSAGE);
						}
						else {
							arr[i+1] = String.valueOf(Integer.parseInt(arr[i+1])-subtractAmount);
						}
						lineOfText = "";
						for(int j = 0; j < arr.length-1;j=j+2) {//recreates lineOftext
							lineOfText = lineOfText + " " + arr[j] + " " + arr[j+1];//im creating an extra space at beginning
						}
						lineOfText = lineOfText.replaceFirst(" ", "");//removing the first space
					}
				}
				if(itemDupe==false) {//if item is not found then there is nothing to subtract from 
					JOptionPane.showMessageDialog(null, "Item not found","Missing Item", JOptionPane.ERROR_MESSAGE);
				}
			}
			catch(NumberFormatException err) {
				JOptionPane.showMessageDialog(null, "Invalid Number","Error",JOptionPane.ERROR_MESSAGE);
			}
			catch(Exception err) {
				JOptionPane.showMessageDialog(null, "Something wrong happened","Error",JOptionPane.ERROR_MESSAGE);
			}
			area.setText("");
			for(int i = 0; i<arr.length-1;i=i+2) {
				area.setText(area.getText() + "\n" + arr[i] + " " + arr[i+1]);
			}
			writeFile.write(lineOfText);//writes lineoftext to the file
			writeFile.close();
			out.close();
		}
		catch(IOException err){
			JOptionPane.showMessageDialog(null, "IOException","Error",JOptionPane.ERROR_MESSAGE);			
		}
	}
}