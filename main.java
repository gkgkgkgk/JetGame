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
import java.awt.geom.AffineTransform;
import java.awt.FontMetrics;
import java.awt.Font;
import java.io.*;
import java.io.InputStream;

public class main extends JPanel implements ActionListener{
	
	JFrame w = new JFrame();
	JButton start;
	Font font;
	    int time = 0;

	enemy en = new enemy(900,400);
	public main(){
		Timer t = new Timer(10, this);
		        t.start();

		getFonts();
		w.setContentPane(this);
		w.setSize(800,600);
		w.setVisible(true);
		setBackground(new Color(35, 106, 135)); //todo: add moving clouds
		start = new JButton("Start");
		start.setBounds(350,275,100,50); // x y size
		start.setFont(font);
		start.setBackground(Color.RED);
        start.setForeground(Color.RED);
		start.setFocusPainted(false);
		start.addActionListener(this);
		add(start);

	}


	public void getFonts(){
		try {
            font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/mainFont.ttf"))).deriveFont(Font.PLAIN, 24);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}


	public void actionPerformed(ActionEvent e){
			if(e.getSource() == start){
				new JetMovement();
				w.setVisible(false); //what...
				timer.stop();
			}
			en.targetPosX = 400;
           	en.targetPosY = 300;
           	en.target = false;
            en.move();
            if (en.particleCounter <= 100) {
                en.particleCounter += 1;
                en.trail.add(new particleTrail(en));
            } else {
                en.trail.remove(0);
                en.particleCounter -= 1;
            }
            time++;
            repaint();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; // Create a Java2D version of g.
        //store old rotation
        AffineTransform old = g2d.getTransform();	
        Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
            .1f);
                float pCounter = en.trail.size();

        g2d.translate(en.xPos, en.yPos); // Translate the center of our coordinates.
        g2d.rotate(Math.toRadians(en.rotation), 15, 15);
            g2d.drawImage(en.img, 0, 0, en.width, en.height, this);
            pCounter = en.trail.size();
            g2d.setTransform(old);
            for (particleTrail part: en.trail) {
                c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1f / pCounter));
                g2d.setComposite(c);
                pCounter -= 1;
                g2d.drawImage(part.img, (int) part.xPos, (int) part.yPos, part.size, part.size, this);
            }
           
        }

    

	public static void main(String[] args){
		new main();
	}

}