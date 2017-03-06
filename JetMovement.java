import javax.swing.*;
import java.awt.Graphics;
import java.util.*;
import java.awt.Color;
import java.awt.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.lang.Math;

public class JetMovement extends JPanel implements KeyListener, ActionListener{
	int time = 0;
	JFrame w;
	public player p;
	public static JetMovement j;
	public boolean left = false;
	public boolean right = false;
	public boolean forward = false;
	public boolean game = true;
	public double rotationSpeed = 1;
	public JetMovement(){
		Timer t = new Timer(10, this);
		t.start();
		p = new player();
		p.xPos = 400;
		p.yPos = 300;
		setBackground(Color.RED);
		w = new JFrame();
		w.setSize(800,600);
		w.setContentPane(this);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setVisible(true);
		w.addKeyListener(this);
	}


	public void actionPerformed(ActionEvent e)
  {
  	//System.out.println(p.rotation+" degrees");
	//System.out.print("Sin "+Math.sin(p.rotation));
	//System.out.print("Cos "+Math.cos(p.rotation));
  	//System.out.println("Runnning"); it works!
	if(right){
		System.out.println("right");
		p.rotation += rotationSpeed;
	}
	if(left){
		System.out.println("left");
		p.rotation -= rotationSpeed;
	}
	if(forward){
		p.xPos += 1 * Math.sin(Math.toRadians(p.rotation));
    	p.yPos += -1 * Math.cos(Math.toRadians(p.rotation)); 
    	System.out.println(p.rotation+" degrees");
		System.out.print("Sin "+Math.sin(Math.toRadians(p.rotation)));
		System.out.print("Cos "+Math.cos(Math.toRadians(p.rotation)));		
	}
    time++;
    repaint();
  }


 	public void keyPressed(KeyEvent e) { 
 		if(e.getKeyChar() == 'd'){
 			System.out.println("d pressed");
 			right = true;
 		}
 		if(e.getKeyChar() == 'a'){
 			System.out.println("a pressed");
 			left = true;
 		}
 		if(e.getKeyChar() == 'w'){
 			System.out.println("w pressed");
			forward = true;
		}
 		

 	}
    public void keyReleased(KeyEvent e) { 
    	if(e.getKeyChar() == 'd'){
 			System.out.println("d released");
 			right = false;
 		}
 		if(e.getKeyChar() == 'a'){
 			System.out.println("a released");
 			left = false;
 		}
 		if(e.getKeyChar() == 'w'){
 			System.out.println("w released");
			forward = false;
		}

    }
    public void keyTyped(KeyEvent e) { }



	class player{
		double xPos;
		double yPos;
		double rotation;
		Image img = new ImageIcon(this.getClass().getResource("player.png")).getImage();


		public player(){


		}
		public void draw(Graphics g){
			Rectangle r = new Rectangle((int)xPos, (int)yPos, 20,20);
 			Graphics2D g2d=(Graphics2D)g; // Create a Java2D version of g.
      		g2d.translate(xPos, yPos); // Translate the center of our coordinates.
      		g2d.rotate(Math.toRadians(p.rotation),5, 10);  // Rotate the image..rotate(Math.toRadians(angle), m_imageWidth/2, m_imageHeight/2);
      		g2d.drawImage(img, 0, 0, 10, 20, j);
			//g2d.draw(r);
		}
	}


	public void paintComponent(Graphics g){
		super.paintComponent(g);
		p.draw(g);
	}
	
	public static void main(String[] args){
		j = new JetMovement();
	}

}