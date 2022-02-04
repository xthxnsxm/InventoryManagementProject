
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
class ApplicationFrame extends JFrame{
	JLabel logo2;
	JPanel panelMain,buttonPanel1,buttonPanel2,buttonPanel3,buttonPanel4,buttonPanel5,buttonPanel6;
	JButton view, add, subtract, delete, about, exit;
	ViewFrame viewframe;
	AddFrame addframe;
	AboutFrame aboutframe;
	DeleteFrame deleteframe;
	SubtractFrame subtractframe;
	ApplicationFrame() {//main home frame
		addWindowListener(new WindowAdapter() {//detects when user clicks x
			public void windowClosing(WindowEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you would like you quit?","",JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		ImageIcon img = new ImageIcon(getClass().getResource("/nofrills.png"));//nofrills image
		Image image = img.getImage(); // make an Image object from the ImageIcon object
		Image newImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // transform it
		img = new ImageIcon(newImage);
		
		
		logo2= new JLabel();
		logo2.setIcon(img);
		
		//following buttons will create new frames and dispose of the main frame
		view = new JButton("View Stock");
		view.setPreferredSize(new Dimension(400,50));
		view.addActionListener(a->{viewframe = new ViewFrame();this.dispose();});
		add = new JButton("Add Stock");
		add.setPreferredSize(new Dimension(400,50));
		add.addActionListener(a->{addframe = new AddFrame();this.dispose();});
		subtract = new JButton("Subtract Stock");
		subtract.setPreferredSize(new Dimension(400,50));
		subtract.addActionListener(a->{subtractframe = new SubtractFrame();this.dispose();});
		delete = new JButton("Delete Stock");
		delete.setPreferredSize(new Dimension(400,50));
		delete.addActionListener(a->{deleteframe = new DeleteFrame();this.dispose();});
		about = new JButton("About");
		about.setPreferredSize(new Dimension(400,50));
		about.addActionListener(a->{aboutframe = new AboutFrame();this.dispose();});
		//exit button
		exit = new JButton("Exit");
		exit.addActionListener(a->{
			int option = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION){
			    System.exit(0);
			}
			});
	
		exit.setPreferredSize(new Dimension(400,50));
		
		buttonPanel1 = new JPanel();
		buttonPanel2 = new JPanel();
		buttonPanel3 = new JPanel();
		buttonPanel4 = new JPanel();
		buttonPanel5 = new JPanel();
		buttonPanel6 = new JPanel();
		buttonPanel1.add(view);
		buttonPanel2.add(add);
		buttonPanel3.add(subtract);
		buttonPanel4.add(delete);
		buttonPanel5.add(about);
		buttonPanel6.add(exit);
		
		
		
		panelMain = new JPanel();
		panelMain.setLayout(new BoxLayout(panelMain,BoxLayout.Y_AXIS));
		panelMain.add(logo2);
		logo2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelMain.add(buttonPanel1);
		panelMain.add(buttonPanel2);
		panelMain.add(buttonPanel3);
		panelMain.add(buttonPanel4);
		panelMain.add(buttonPanel5);
		panelMain.add(buttonPanel6);
		
		getContentPane().add(panelMain);
		
		
		
		
	setVisible(true);
	setTitle("StockManagement");
	setSize(1000,700);
	//align in the center of screen
	setLocationRelativeTo(null);

	}

}
