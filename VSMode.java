import javax.swing.*;
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
	
	ArrayList<explosion> explosions = new ArrayList<explosion>();

	Color bgColor = new Color(108,164,200);
	Timer t = new Timer();
	int refreshRate = 16;
	
	ArrayList < cloud > clouds = new ArrayList < cloud > ();

	double cloudAmount;
	Font font, fontBig, fontBiggest;
    
    double elapsedTime = 0.016;
    
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
    for (int i = 0; i < explosions.size(); i++) {
            explosion ex = explosions.get(i);
            if (ex.particles.size() != 0) {
                for (int z = 0; z < ex.particles.size(); z++) {
                    explosionParticle ep = ex.particles.get(z);
                    if (ep.lifeTime >= 0) {
                        ep.posX += ep.speed * Math.sin(Math.toRadians(ep.rotation));
                        ep.posY -= ep.speed * Math.cos(Math.toRadians(ep.rotation));
                        ep.lifeTime -= elapsedTime;
                        if (ep.trailBool) {
                            if (ep.particleCounter <= 5) {
                                ep.particleCounter += 1;
                                ep.trail.add(new particleTrail(ep));
                            } else {
                                ep.trail.remove(0);
                                ep.particleCounter -= 1;
                            }
                        }
                    } else {
                        ex.particles.remove(ep);
                    }
                }
            } else {
                explosions.remove(ex);
            }

        }

				for(int i = 0; i < players.size(); i++){
					multiplayer p = players.get(i);
					p.move();
					
					for(multiplayer pb : players){
						if(pb != p){
							p.checkCollision(pb.bullets, pb.playerNum);
							p.checkCollision(pb, pb.playerNum);
						}
					}
					if(p.health <= 0){
						explosions.add(new explosion(100,(int)p.xPos,(int)p.yPos));
						players.remove(p);
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
                for (int x = 0; x < b.trail.size(); x++) {
                particleTrail part = b.trail.get(x);   
	            g2d.setColor(Color.WHITE);
                g.fillRect((int)part.xPos,(int)part.yPos,part.size, part.size);
            }
                g2d.drawImage(b.img, (int) b.xPos, (int) b.yPos, 5, 5, j);
            }
		}
		
		for(int i = 0; i < clouds.size(); i ++){
			if(clouds.get(i).size >= 90){
        	c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1f / (100/clouds.get(i).size)));
            g2d.setComposite(c);
        	g2d.drawImage(clouds.get(i).image, (int)clouds.get(i).xPos, (int)clouds.get(i).yPos, (int)clouds.get(i).size, (int)clouds.get(i).size,j);
        }}
        ArrayList<explosionParticle> combinedEParticles = new ArrayList<explosionParticle>();
        ArrayList<particleTrail> combinedParticles = new ArrayList<particleTrail>();

        for (int o = 0; o < explosions.size(); o++) {
            explosion ex = explosions.get(o);
            combinedEParticles.addAll(ex.particles);         
        }
        for (int z = 0; z < combinedEParticles.size(); z++) {
                explosionParticle ep = combinedEParticles.get(z);
                g2d.drawImage(ep.img, (int) ep.posX, (int) ep.posY, 5, 5, j);
                combinedParticles.addAll(ep.trail);
        }
        for (int n = 0; n < combinedParticles.size(); n++) {
                particleTrail part = combinedParticles.get(n);

                g2d.drawImage(part.img, (int) part.xPos, (int) part.yPos, part.size, part.size, j);
            }

	}
	
	
	
} 