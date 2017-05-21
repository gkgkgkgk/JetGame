import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MultiPlayerPanel extends JPanel implements ActionListener{

	JFrame w;
	int height = 600;
	int width = 800;
	int playerAmount = 0;
	JButton start = new JButton("Start!");
	JButton addPlayer = new JButton("Add Player!");
	ArrayList<PlayerPanel> playerPanels = new ArrayList<PlayerPanel>();
	ArrayList<multiplayer> players = new ArrayList<multiplayer>();
	
	main main;
	
	public MultiPlayerPanel(main main){
		this.main = main;
		JScrollPane scrollPane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		w = new JFrame();
		w.setSize(width, height);
		w.setContentPane(scrollPane);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setTitle("Select Players!");
		addPlayer.setAlignmentX( Component.CENTER_ALIGNMENT );
		addPlayer.addActionListener(this);
		start.setAlignmentX( Component.CENTER_ALIGNMENT );
		start.addActionListener(this);
		add(addPlayer);
		add(start);
		w.setVisible(true);
		}
		
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == addPlayer){addPlayerPanel();}
				double counter = 0;
				int amount = playerPanels.size();
			if(e.getSource() == start){for(PlayerPanel p : playerPanels){
				counter += 1;
				players.add(new multiplayer(p.getPlayerNum(), p.getColor(), p.getControlF(),  p.getControlL(), p.getControlR(), p.getControlBoost(), p.getControlShoot(), (int)((float)counter * (1280.0/(float)(amount+1)))));
				System.out.println((int)((float)counter * (1280.0/(float)amount)));
				}
			new VSMode(main, players);
		}
			
		}
		
		
		void addPlayerPanel(){
			playerAmount += 1;
			Character[] lettersF = new Character[] {'w', 'i', 't', '8', 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}; //cuz why not add every letter
			Character[] lettersL = new Character[] {'a', 'j', 'f', '4', 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
			Character[] lettersR = new Character[] {'d', 'l', 'h','6', 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
			Character[] lettersShoot = new Character[] {'q', 'o', 'y','7', 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
			Character[] lettersBoost = new Character[] {'s', 'k', 'g','5', 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
			Color[] colors = new Color[]{Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.PINK, Color.GRAY, Color.BLACK, Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.PINK, Color.GRAY, Color.BLACK, Color.YELLOW}; // now you can ass 16 players, cuz that is useful i guess.
			PlayerPanel p = new PlayerPanel(playerAmount, colors[playerAmount-1], lettersF[playerAmount-1], lettersL[playerAmount-1], lettersR[playerAmount-1], lettersShoot[playerAmount-1], lettersBoost[playerAmount-1]);
			add(p);
			playerPanels.add(p);
			revalidate();
			/*if(playerAmount >= 8){
				 remove(addPlayer);
			}*/
		}

}