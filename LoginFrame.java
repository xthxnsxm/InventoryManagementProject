import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
class LoginFrame extends JFrame{
	JPanel panelLeft,panelRight,usernamePanel,passwordPanel,buttonPanel, passwordField, usernameField;
	JTextField username;
	JPasswordField password;
	JLabel passwordPrompt,usernamePrompt,logo;
	JButton signin, exit;
	boolean validuser;
	boolean validpass;
	ApplicationFrame mainframe;
	LoginFrame(){
		//detects when user clicks the x button
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you would like you quit?","",JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		usernamePrompt=new JLabel("Username:");
		username =new JTextField(15);
		
		ImageIcon img = new ImageIcon(getClass().getResource("/nofrills.png"));//nofrills image
		Image image = img.getImage(); // make an Image object from the ImageIcon object
		Image newImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // transform it
		img = new ImageIcon(newImage);
		passwordPrompt= new JLabel("Password:");
		password = new JPasswordField(15);
		signin = new JButton("Sign In");
		
		signin.addActionListener(a->//if user gets the username and password right they instantiate a new frame and dispose old one
		{if(username.getText().equals("manoil")&&password.getText().equals("manoil")) {
		mainframe = new ApplicationFrame();this.dispose();
		}
		else {//wrong password/username message
			JOptionPane.showMessageDialog(null, "Wrong Username/Password", "Error",JOptionPane.ERROR_MESSAGE);
		}
		});
		exit = new JButton("Exit");
		exit.addActionListener(a->{//exit button
		int option = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION){
		    System.exit(0);
		}
		});
		logo = new JLabel();
		logo.setIcon(img);
		
		passwordField = new JPanel();
		passwordField.add(password);
		usernameField = new JPanel();
		usernameField.add(username);
		buttonPanel=new JPanel();
		buttonPanel.add(signin);
		buttonPanel.add(exit);

		panelLeft=new JPanel();
		logo = new JLabel();
        logo.setIcon(img);
        panelLeft.add(logo);
        
		
		panelRight=new JPanel();
		panelRight.setLayout(new BoxLayout(panelRight,BoxLayout.Y_AXIS));
		panelRight.add(usernamePrompt);
		usernamePrompt.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelRight.add(usernameField);
		panelRight.add(passwordPrompt);
		passwordPrompt.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelRight.add(passwordField);
		panelRight.add(buttonPanel);
		
		



		setTitle("Login");
		setVisible(true);
		setSize(300,200);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.X_AXIS));
		getContentPane().add(panelLeft);
		getContentPane().add(panelRight);
		

	}

	
}