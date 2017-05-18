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
	
	Color bgColor = new Color(108,164,200);
	Timer t = new Timer();
	int refreshRate = 16;
	
	multiplayer p1 = new multiplayer('w', 'a', 'd', 's', '1');
	multiplayer p2 = new multiplayer('i', 'j', 'l', 'k', 'p');

	JFrame w;
	public static VSMode j;
	
	public VSMode(main m){
		main = m;
		w = new JFrame();
		w.setSize(800,600);
		w.setContentPane(this);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.addKeyListener(this);
        setLayout(null);
        w.setVisible(true);
        setBackground(bgColor);
        p1.xPos = 540;
        p1.yPos = 360;
        p2.xPos = 740;
        p2.yPos = 360;
		loop();
		
	}	
	
	
	
	
	public void loop(){
		t.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				p1.move();
				p2.move();
				
				repaint();
				}	
		}, 0, refreshRate);}
	
	public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == p1.rightChar) {
            p1.right = true;
        }
        if (e.getKeyChar() == p1.leftChar) {
            p1.left = true;
        }
        if (e.getKeyChar() == p1.forwardChar) {
            p1.forward = true;
        }
        if (e.getKeyChar() == p1.boostChar) {
            p1.boost = true;
        }
        if (e.getKeyChar() == p2.rightChar) {
            p2.right = true;
        }
        if (e.getKeyChar() == p2.leftChar) {
            p2.left = true;
        }
        if (e.getKeyChar() == p2.forwardChar) {
            p2.forward = true;
        }
        if (e.getKeyChar() == p2.boostChar) {
            p2.boost = true;
        }
        if (e.getKeyCode() == p1.fireChar && !p1.boost && p1 !=null) { // cant shoot while boosting
            System.out.println("Shoot");
            p1.shoot();
        }
        if (e.getKeyCode() == p2.fireChar && !p2.boost && p2 !=null) { // cant shoot while boosting
            //System.out.println("Shoot");
            p2.shoot();
        }
        
    }
    public void keyReleased(KeyEvent e) {
    	if (e.getKeyChar() == p1.rightChar) {
            p1.right = false;
        }
        if (e.getKeyChar() == p1.leftChar) {
            p1.left = false;
        }
        if (e.getKeyChar() == p1.forwardChar) {
            p1.forward = false;
        }
        if (e.getKeyChar() == p1.boostChar) {
            p1.boost = false;
        }
        if (e.getKeyChar() == p2.rightChar) {
            p2.right = false;
        }
        if (e.getKeyChar() == p2.leftChar) {
            p2.left = false;
        }
        if (e.getKeyChar() == p2.forwardChar) {
            p2.forward = false;
        }
        if (e.getKeyChar() == p2.boostChar) {
            p2.boost = false;
        }
    }
    public void keyTyped(KeyEvent e) {}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();	
		
		if (p1 != null) {
            for (int i = 0; i < p1.trail.size(); i++) {
                particleTrail p = p1.trail.get(i);
                
	                g2d.setColor(p.color);
                g2d.fillRect((int)p.xPos,(int)p.yPos,p.size, p.size);
            }
            //draw player
            g2d.translate(p1.xPos, p1.yPos); // Translate the center of our coordinates.
            g2d.rotate(Math.toRadians(p1.rotation), 15, 15);
            g2d.drawImage(p1.img, 0, 0, 30, 30, j);
            //draw bullets
            g2d.setTransform(old);
            for (int i = 0; i < p1.bullets.size(); i++) {
                bullet b = p1.bullets.get(i);
                g2d.setTransform(old);
                g2d.drawImage(b.img, (int) b.xPos, (int) b.yPos, 5, 5, j);
            }
        }

	
	if (p2 != null) {
            for (int i = 0; i < p2.trail.size(); i++) {
                particleTrail p = p2.trail.get(i);
                //c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1f / pCounter));
                //g2d.setComposite(c);
                //g2d.drawImage(p.img, (int) p.xPos, (int) p.yPos, 5, 5, j);
                g2d.setColor(p.color);
                g2d.fillRect((int)p.xPos,(int)p.yPos,p.size, p.size);
            }
            //draw player
            g2d.translate(p2.xPos, p2.yPos); // Translate the center of our coordinates.
            g2d.rotate(Math.toRadians(p2.rotation), 15, 15);
            g2d.drawImage(p2.img, 0, 0, 30, 30, j);
            //draw bullets
            g2d.setTransform(old);
            for (int i = 0; i < p2.bullets.size(); i++) {
                bullet b = p2.bullets.get(i);
                g2d.setTransform(old);
                g2d.drawImage(b.img, (int) b.xPos, (int) b.yPos, 5, 5, j);
            }
        }

	}
	
	
} 