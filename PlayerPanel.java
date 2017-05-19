import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class PlayerPanel extends JPanel{

	JLabel playerNum;
	int num;
	private JButton chooseColor = new JButton();
	Color color;
	char controlRight = 'a';
	char controlLeft = 'a';
	char controlShoot = 'a';
	char controlBoost = 'a';
	char controlF = 'a';

			Character[] lettersF = new Character[] {'w', 'i', 't', '8', 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
			Character[] lettersL = new Character[] {'a', 'j', 'f', '4', 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
			Character[] lettersR = new Character[] {'d', 'l', 'h','6', 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
			Character[] lettersShoot = new Character[] {'q', 'o', 'y','7', 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
			Character[] lettersBoost = new Character[] {'s', 'k', 'g','5', 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

	JComboBox<Character> controlsF = new JComboBox<Character>(lettersF);
	JComboBox<Character> controlsL = new JComboBox<Character>(lettersL);
 	JComboBox<Character> controlsR = new JComboBox<Character>(lettersR);
 	JComboBox<Character> controlsShoot = new JComboBox<Character>(lettersShoot);
	JComboBox<Character> controlsBoost = new JComboBox<Character>(lettersBoost);


	
	public PlayerPanel(int num, Color c, char conF, char conL, char conR, char conShoot, char conBoost){
		this.num = num;
		chooseColor.setOpaque(true);
		chooseColor.setBackground(c);
		color = c;
		controlRight = conR;
		controlLeft = conL;
		controlShoot = conShoot;
		playerNum = new JLabel("Player "+num);
		add(playerNum);
		add(chooseColor);
		add(new JLabel("Forward:"));
		add(controlsF);
		add(new JLabel("Left:"));
		add(controlsL);
		add(new JLabel("Right:"));
		add(controlsR);
		add(new JLabel("Shoot:"));
		add(controlsShoot);
		add(new JLabel("Boost:"));
		add(controlsBoost);
		chooseColor.setBorderPainted(false);
		chooseColor.addActionListener(new ButtonListener());
		controlsF.setSelectedItem(conF);
		controlsL.setSelectedItem(conL);
		controlsR.setSelectedItem(conR);
		controlsShoot.setSelectedItem(conShoot);
		controlsBoost.setSelectedItem(conBoost);

		
	}

	public int getPlayerNum(){return num;}
	public Color getColor(){return color;}
	public char getControlR(){return controlsR.getSelectedItem().toString().charAt(0);}
	public char getControlL(){return controlsL.getSelectedItem().toString().charAt(0);}
	public char getControlShoot(){return controlsShoot.getSelectedItem().toString().charAt(0);}
	public char getControlBoost(){return controlsBoost.getSelectedItem().toString().charAt(0);}
	public char getControlF(){return controlsF.getSelectedItem().toString().charAt(0);}

	
	private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
		if(e.getSource() == chooseColor){
      Color c = JColorChooser.showDialog(null, "Choose a Color", chooseColor.getForeground());
      if (c != null){
	      
	  
	    chooseColor.setBackground(c);
		color = c;
    }}
}
	
  }

}