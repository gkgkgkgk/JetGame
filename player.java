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

public class player {
		double maxHealth = 100;
		double health = 100;
        double xPos;
        double yPos;
        double width = 30.0;
        double height = 30.0;
        double rotation;
        Image img = new ImageIcon(this.getClass().getResource("images/plane-1.png")).getImage();
        Rectangle bounds = new Rectangle(30,30, (int)xPos, (int)yPos);
        
        public player() {


        }

        public void checkCollision(ArrayList<bullet> bullets){
		for(bullet b : bullets){
			Rectangle r2 = b.bounds;
			bounds = new Rectangle((int)xPos, (int)yPos,30,30);
			 if (r2.intersects(bounds) || bounds.intersects(r2)) {
			 	System.out.println("hit!");
                health -= 10;
            }
		}
	}  
    }