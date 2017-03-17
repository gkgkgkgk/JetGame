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
		double maxHealth = 200;
		double health = 200;
        double xPos;
        double yPos;
        double width = 30.0;
        double height = 30.0;
        double rotation;
        Image img = new ImageIcon(this.getClass().getResource("images/plane-1.png")).getImage();
        Rectangle bounds = new Rectangle(30,30, (int)xPos, (int)yPos);        
        //regenrate health var
        double lastHitTime = 5.0;
        boolean boost = false;
        public player() {


        }

        public void checkCollision(ArrayList<bullet> bullets){
            for(int i = 0; i < bullets.size(); i++){
            bullet b = bullets.get(i);
            Rectangle r2 = b.bounds;
            bounds = new Rectangle((int)xPos, (int)yPos,30,30);
             if (r2.intersects(bounds) || bounds.intersects(r2)) {
                System.out.println("Player Collision with bullet at" + xPos+", "+yPos);
                health -= 10;
                bullets.remove(b);
                lastHitTime = 5.0;
            }
        }
	}  

	public void regenerateHealth(){
		//System.out.println(health);
		lastHitTime -= 0.01;
		if(lastHitTime <= 0 && health <= maxHealth){
			health += 0.05;
		}
	}

    }