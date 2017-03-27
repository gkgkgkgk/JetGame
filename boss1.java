
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


public class boss1{
	//boss one is a blimp with two rotating turrets on it
	double maxHealth = 500;
	double health = 500;
	//variables to control AI
	double xPos = 300;
	double yPos = 100;
	int width = 30;
	int height = 30;
	double targetPosX;
	double targetPosY;
	double gravity = 9.87;
	ArrayList < particleTrail > trail = new ArrayList < particleTrail > ();
	Image img = new ImageIcon(this.getClass().getResource("images/plane-1Enemy.png")).getImage();	
	Image particleImg = new ImageIcon(this.getClass().getResource("images/player.png")).getImage();
	ArrayList < bullet > bullets = new ArrayList < bullet > ();
	int shotCoolDown = 50; // 1 bullet per second
    int particleCounter = 0;
	Rectangle bounds = new Rectangle((int)xPos, (int)yPos,30,30);
	double elapsedTime = 0.016;
	boolean target = true;

	player playerTarget;
	int reward = 1000;

	public boss1(){

	}

	public void move(){

	}

	public void shoot(){

	}

	public void checkCollision(ArrayList<bullet> bullets){
		for(int i = 0; i < bullets.size(); i++){
			bullet b = bullets.get(i);
			Rectangle r2 = b.bounds;
			 if (r2.intersects(bounds)) {
                //System.out.println("Enemy Collision with bullet at" + xPos+", "+yPos);
                health -= 25;
                bullets.remove(b);
            }
		}
	}

public void checkCollision(player p){
			Rectangle r2 = p.bounds;
			 if (r2.intersects(bounds) && p.boost) {
                //System.out.println("Enemy Collision with bullet at" + xPos+", "+yPos);
                health -= 50;
                p.health -= 25;//p.maxHealth; //remember to take health away from player for colliding (wtihout crashing everything please!)
            }
		}

}