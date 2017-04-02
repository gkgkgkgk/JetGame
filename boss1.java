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
Image rightImage = new ImageIcon(this.getClass().getResource("images/boss1.png")).getImage();
Image left = new ImageIcon(this.getClass().getResource("images/boss1Left.png")).getImage();
int turret = 1;
double shotCooldown = 50;
double counter = 0;

	public boss1(player p){
		super.size = 150;
		super.img = rightImage;
		super.playerTarget = p;
		super.name = "Blimp of Blood (BOB)";
		super.maxHealth = 500;
		super.health = 500;
		this.reward = 1000;
	}

	public void move(){
		targetPosX = playerTarget.xPos;
		targetPosY = playerTarget.yPos;
		lerpRotation();
		switch(phase){
		case 1:
		if(right){xPos += 1; if(xPos >= 1100){right = false; super.img = left;}}
		else{xPos -= 1; if(xPos <=180){right = true; super.img = rightImage;}}
		break;
		case 2:
		if(right){xPos += 1.5; if(xPos >= 1100){right = false; super.img = left;}}
		else{xPos -= 1.5; if(xPos <=180){right = true; super.img = rightImage;}}
		break;
		case 3:
		if(right){xPos += 2; if(xPos >= 1100){right = false; super.img = left;}}
		else{xPos -= 2; if(xPos <=180){right = true; super.img = rightImage;}}
		break;
	}
	shotCooldown -= 1;
	if(shotCooldown <= 0){
		shoot();
	}
	if(health < 350){
		phase = 2;
	}
	if(health < 200){
		phase = 3;
	}
	}

	public void shoot(){
		switch(phase){
		case 1:
		if(counter < 5){
			bullets.add(new bullet(this, 20, xPos+(20*turret), yPos+75));
			shotCooldown = 10;
			counter += 1;
		}
		else{
			if(turret < 6){
			turret += 1;
		}
		else{
			turret = 1;
		}
			shotCooldown = 100;
			counter = 0;
		}
		break;
		case 2:
		bullets.add(new bullet(this, 30, xPos+(20*turret), yPos+75));
		if(turret < 6){
			turret += 1;
		}
		else{
			turret = 1;
		}
		shotCooldown = 20;
		break;
		case 3:
		turret = 3;
		for(int i = 1; i < 6; i++){
		bullets.add(new bullet(this, 40, xPos+(20*i), yPos+75));
		}
		shotCooldown = 10;
		break;
	}
	}

	public void checkCollision(ArrayList<bullet> bullets){
		for(int i = 0; i < bullets.size(); i++){
			bullet b = bullets.get(i);
			Rectangle r2 = b.bounds;
			 if (r2.intersects(bounds)) {
                System.out.println("Boss Collision with bullet at " + xPos+", "+yPos +" " +health);
                health -= 25;
                bullets.remove(b);
            }
		}
	}

public void checkCollision(player p){
			Rectangle r2 = p.bounds;
			 if (r2.intersects(bounds) && p.boost) {
                System.out.println("Boss Collision with bullet at" + xPos+", "+yPos);
                health -= 50;
                p.health -= 50;//p.maxHealth; //remember to take health away from player for colliding (wtihout crashing everything please!)
            }
		}

	public void lerpRotation(){
		if(targetPosX >= xPos+(20*turret)){
			//remember to use xPos+(20*turret), yPos+75
		super.desiredRot = 90 + Math.toDegrees(Math.atan((targetPosY - yPos+75) / (targetPosX - xPos+(20*turret))));
		}
		else{
			//adding 270 for some rotation reason....  i think
			super.desiredRot = 270 + Math.toDegrees(Math.atan((targetPosY - yPos+75) / (targetPosX - xPos+(20*turret))));	
		}
		super.rotation = desiredRot;
	}

}