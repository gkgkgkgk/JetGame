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
import java.lang.Math;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.awt.FontMetrics;
import java.awt.Font;
import java.io.*;
import java.io.InputStream;




public class VSMode extends JPanel implements KeyListener{
	
	
	main main;
	
	
	ArrayList<multiplayer> players = new ArrayList<multiplayer>();
	
	
	Color bgColor = new Color(108,164,200);
	Timer t = new Timer();
	int refreshRate = 16;
	
	ArrayList < cloud > clouds = new ArrayList < cloud > ();

	double cloudAmount;
	Font font, fontBig, fontBiggest;
    
	JFrame w;
	public static VSMode j;
	
	public VSMode(main m, ArrayList<multiplayer> p){
		cloudAmount = Math.random()*125; //random amount of clouds
		players = p;
		main = m;
		w = new JFrame();
		w.setSize(1280, 720);
		w.setContentPane(this);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.addKeyListener(this);
        setLayout(null);
        w.setVisible(true);
        setBackground(bgColor);
        
		loop();
		for(multiplayer pl : players){
			
			System.out.println("fire" + pl.fireChar);
			
		}
		
	}	
	
	
	public void getFonts() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/mainFont.ttf"))).deriveFont(Font.PLAIN, 24);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
         try {
            fontBig = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/mainFont.ttf"))).deriveFont(Font.PLAIN, 48);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            fontBiggest = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/mainFont.ttf"))).deriveFont(Font.PLAIN, 72);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
	public void loop(){
		t.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				 if(clouds.size() < cloudAmount){
    	clouds.add(new cloud(10+Math.random()*90, (int)(4*Math.random())));
    }
    else{
    	for(int i = 0; i < clouds.size(); i++){
    		clouds.get(i).move();
    		if(clouds.get(i).xPos > 1300){
    			clouds.remove(clouds.get(i));
    		}
    	}
    }
				for(multiplayer p : players){
					p.move();
					for(multiplayer pb : players){
						if(pb != p){
							p.checkCollision(pb.bullets, pb.playerNum);
						}
					}
				}

				repaint();
				}	
		}, 0, refreshRate);}
	
	public void keyPressed(KeyEvent e) {
		for(multiplayer p : players){
				 if(e.getKeyChar() == p.rightChar){
					p.right = true;
				 }
				 if(e.getKeyChar() == p.leftChar){
					p.left = true;
				 }
				 if(e.getKeyChar() == p.forwardChar){
					p.forward = true;
				 }
				 if(e.getKeyChar() == p.boostChar){
					p.boost = true;
				 }
				 if (e.getKeyChar() == p.fireChar) { // cant shoot while boosting
					System.out.println("Shoot");
					p.shoot();
				}
			 }
        
    }
    public void keyReleased(KeyEvent e) {
    	for(multiplayer p : players){
				 if(e.getKeyChar() == p.rightChar){
					p.right = false;
				 }
				 if(e.getKeyChar() == p.leftChar){
					p.left = false;
				 }
				 if(e.getKeyChar() == p.forwardChar){
					p.forward = false;
				 }
				 if(e.getKeyChar() == p.boostChar){
					p.boost = false;
				 }
				 
			 }
    }
    public void keyTyped(KeyEvent e) {}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();
for(multiplayer p: players){ // draw markers
			g.setColor(p.c);
			g.fillOval((int)p.xPos-15, (int)p.yPos-15, 60,60);
		}		
	     Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
            0.1f);
		for(int i = 0; i < clouds.size(); i ++){
        	if(clouds.get(i).size < 90){
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1f / (100/clouds.get(i).size)));
            g2d.setComposite(c);
        	g2d.drawImage(clouds.get(i).image, (int)clouds.get(i).xPos, (int)clouds.get(i).yPos, (int)clouds.get(i).size, (int)clouds.get(i).size,j);
			}
        }
		c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
            1f);
			g2d.setComposite(c);
		
		
		for(multiplayer p : players){
			for (int i = 0; i < p.trail.size(); i++) {
                particleTrail part = p.trail.get(i);
                
	                g2d.setColor(part.color);
                g2d.fillRect((int)part.xPos,(int)part.yPos,part.size, part.size);
            }
            //draw player
            g2d.translate(p.xPos, p.yPos); // Translate the center of our coordinates.
            g2d.rotate(Math.toRadians(p.rotation), 15, 15);
            g2d.drawImage(p.img, 0, 0, 30, 30, j);
			g.setColor(p.c);
            g2d.setTransform(old);
            for (int i = 0; i < p.bullets.size(); i++) {
                bullet b = p.bullets.get(i);
                g2d.setTransform(old);
                g2d.drawImage(b.img, (int) b.xPos, (int) b.yPos, 5, 5, j);
            }
		}
		
		for(int i = 0; i < clouds.size(); i ++){
			if(clouds.get(i).size >= 90){
        	c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1f / (100/clouds.get(i).size)));
            g2d.setComposite(c);
        	g2d.drawImage(clouds.get(i).image, (int)clouds.get(i).xPos, (int)clouds.get(i).yPos, (int)clouds.get(i).size, (int)clouds.get(i).size,j);
        }}

	}
	
	
} 