
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


public class boss1 extends boss{

boolean right = true;


	public boss1(){
		super.size = 150;
	}

	public void move(){
		switch(phase){
		case 1:
		if(right){xPos += 1; if(xPos >= 1100){right = false;}}
		else{xPos -= 1; if(xPos <=180){right = true;}}
		break;
		case 2:
		break;
		case 3:
		break;
	}
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