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

class AddFrame extends JFrame implements ActionListener{
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
    JButton back, add;
    JTextField itemField, numberField;
    JPanel addPanel,enterstockPanel,textarea;
    ApplicationFrame mainframe;
    AddFrame() {//this frame allows users to add items or add to existing items
		addWindowListener(new WindowAdapter() {//detects when user attempts to close window
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

        add = new JButton("Add");
        add.addActionListener(this);
        back = new JButton("Back");
		back.addActionListener(a->{//back button closes current frame and reopens the main frame
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
        enterstockPanel.add(add);

		try {
			textFile = new File("inventory.txt");//reads from inventory
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
		area.setEditable(false);
        scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);



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
	public void actionPerformed(ActionEvent e) {//detects when add button is pressed
		// TODO Auto-generated method stub
		try {
			out = new FileWriter(textFile);//prepares to rewrite the inventory.txt file
			writeFile = new BufferedWriter(out);
			String itemID;
			int itemAmount;
			try {
				itemID = itemField.getText().replaceAll(" ", ""); //grabs the item name and removes all spaces
				itemAmount = Integer.parseInt(numberField.getText());//grabs the number and parses into int
				boolean itemDupe = false;//temp variable to detect when item is already in inventory
				for(int i = 0; i < arr.length-1; i=i+2) {//loops through array
					if(itemID.equals(arr[i])) {
						itemDupe = true;//tracks if there is a duplicate
						arr[i+1] = String.valueOf(Integer.parseInt(arr[i+1])+itemAmount);//adds amount to the value next to string
						//rewrites lineOfText 
						lineOfText = "";
						for(int j = 0; j < arr.length-1;j=j+2) {
							lineOfText = lineOfText + " " + arr[j] + " " + arr[j+1];
						}
						lineOfText = lineOfText.replaceFirst(" ", "");
					}
				}
				if(itemDupe==false) {//only runs when item is not a duplicate
					lineOfText = lineOfText + " " + itemID + " " + itemAmount;//adds items to lineoftext
					arr = lineOfText.split(" ");//rewrites array with new item
				
					
				}
			}
			catch(NumberFormatException err) {
				JOptionPane.showMessageDialog(null, "Invalid Input. Please input a proper number!","Input Error",JOptionPane.ERROR_MESSAGE);
			}
			catch(Exception err) {
				JOptionPane.showMessageDialog(null, "Something wrong happened","Error",JOptionPane.ERROR_MESSAGE);
			}
			//rewrites the textarea according to the new array
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