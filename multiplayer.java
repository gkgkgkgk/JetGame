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

public class multiplayer {
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
        double lastHitTime = 1.0;
        double elapsedTime = 0.016;
	    int particleCounter = 0;

        boolean boost = false;
        boolean forward = false;
        boolean right = false;
        boolean left = false;
        public char forwardChar, leftChar, rightChar, boostChar, fireChar;
        
        
        double gravity = 9.87;
    	double maxVelocity = 160.0;
    	double velocityX;
    	double velocityY;
    	double accelerationY;
    	double accelerationX;
    	double forceY;
    	double forceX;
    	double inputForceY;
    	double inputForceX;
    	double mass = 1.0;
        double rotationSpeed = 5;
    	double lastX;
    	double lastY;
    	ArrayList < bullet > bullets = new ArrayList < bullet > ();
    	ArrayList < particleTrail > trail = new ArrayList < particleTrail > ();
        
		double shotCooldown = 0.25;
		final double shotCooldownStore = 0.25;
        
        SoundEffect pew = SoundEffect.HIT;

		int playerNum = 0;
		Color c;
        
        public multiplayer(int num, Color c, char f, char l, char r, char b, char fire, int xPos) {
			this.xPos = xPos;
			playerNum = num;
			this.c = c;
			forwardChar = f;
			leftChar = l;
			rightChar = r;
			boostChar = b;
			fireChar = fire;

        }

		public void move(){
			shotCooldown -= 0.016;
			if (particleCounter <= 50) {
                particleCounter += 1;
                trail.add(new particleTrail(this));
            } else {
                trail.remove(0);
                particleCounter -= 1;
            }
            //bullet particles/trails
            for (int bu = 0; bu < bullets.size(); bu++) {
                bullet b = bullets.get(bu);
                if (b.particleCounter <= 10) {
                    b.particleCounter += 1;
                    b.trail.add(new particleTrail(b));
                } else {
                    b.trail.remove(0);
                    b.particleCounter -= 1;
                }
                b.bounds = new Rectangle((int) b.xPos, (int) b.yPos, 5, 5);
                b.xPos += b.speed * b.xRot;
                b.yPos += b.speed * b.yRot;
                if(b.xPos < -50 || b.xPos > 1330 || b.yPos < -50 || b.yPos > 770){
                    bullets.remove(bu);
                }
            }
			if (rotation > 360) {
                rotation -= 360;
            } else if (rotation < -360) {
                rotation += 360;
            }
            //image stuff
            if ((Math.abs(rotation) > 315) || (Math.abs(rotation) < 45) || (Math.abs(rotation) > 135 && Math.abs(rotation) < 225)) {
                img = new ImageIcon(this.getClass().getResource("images/plane-1.png")).getImage(); //this is bad practice
            } else if ((rotation <= 135 && rotation >= 45) || (rotation >= -315 && rotation <= -225)) {
                img = new ImageIcon(this.getClass().getResource("images/planeRight.png")).getImage(); //this is bad practice
            } else {
                img = new ImageIcon(this.getClass().getResource("images/planeLeft.png")).getImage(); //this is bad practice
            }

			findAccelerationY();
            findVelocityY();
            findAccelerationX();
            findVelocityX();
             yPos += velocityY * elapsedTime*2; //realistacally should be 0.01 not 0.02, 
            xPos += velocityX * elapsedTime*2; //but 0.02 makes the game quicker and the plane more manueverable
            bounds = new Rectangle((int)xPos, (int)yPos, 30,30);
            //boost!
            if(boost) {
                maxVelocity = 300;
                velocityX = 300 * Math.sin(Math.toRadians(rotation));
                velocityY = -300 * Math.cos(Math.toRadians(rotation));
            } else {
                maxVelocity = 100;
                if (right) {
                    //System.out.println("right");
                    rotation += rotationSpeed;
                }
                if (left) {
                    //System.out.println("left");
                    rotation -= rotationSpeed;
                }
                if (forward) {
                forceX = 350 * Math.sin(Math.toRadians(rotation)); // the number 500 is the thrust
                forceY = -350 * Math.cos(Math.toRadians(rotation));
            } else {
                forceX = 0;
                forceY = 0;
            }
            }
            if(xPos < 0){
                xPos = 1280;
            }
            if (xPos > 1280){
                xPos = 0;
            }
            if (yPos < 0){
                yPos = 720;
            }
            if (yPos > 720){
                yPos = 0;
            }
            
            regenerateHealth();
		
		}


        public void checkCollision(ArrayList<bullet> bullets, int enemyNum){
            for(int i = 0; i < bullets.size(); i++){
            bullet b = bullets.get(i);
            Rectangle r2 = b.bounds;
            bounds = new Rectangle((int)xPos, (int)yPos,30,30);
             if (r2.intersects(bounds) || bounds.intersects(r2)) {
				System.out.println("Hit on Player" + playerNum +" by player" + enemyNum);
                health -= 10;
                bullets.remove(b);
                lastHitTime = 1.0;
            }
        }
	}  

	public void regenerateHealth(){
		//System.out.println(health);
		lastHitTime -= 0.02;
		if(lastHitTime <= 0 && health <= maxHealth){
			health += 0.05;
		}
	}
	
	
	public void findVelocityY() {
        if (forward) {
            if (Math.abs(velocityY) < maxVelocity) {
                velocityY += accelerationY * elapsedTime;
            } else if (velocityY > 0) {
                velocityY = maxVelocity - 1;
            } else if (velocityY < 0) {
                velocityY = (-maxVelocity) + 1;
            }
        } else {
            if(Math.abs(velocityY) < maxVelocity){
            velocityY += accelerationY * elapsedTime;
            }
            else if(velocityY < 0){
                velocityY = -maxVelocity;
            }
            else if(velocityY >= 0){
                velocityY = maxVelocity;
            }
        }
    }
    public void findVelocityX() {
        if (forward) {
            if (Math.abs(velocityX) < maxVelocity) {
                velocityX += accelerationX * elapsedTime;
            } else if (velocityX > 0) {
                velocityX = maxVelocity - 1;
            } else if (velocityX < 0) {
                velocityX = (-maxVelocity) + 1;
            }
        } else {
        	if(maxVelocity < Math.abs(velocityX)){
        		if(velocityX < 0){
                velocityX = -maxVelocity;
            }
            else if(velocityX >= 0){
                velocityX = maxVelocity;
            }
        	}
			if (velocityX >= 0) {
                velocityX -= mass * elapsedTime;
            } else if (velocityX < 0) {
                velocityX += mass * elapsedTime;
            }

        }
    }


    public void findAccelerationY() {
        if (!forward) {
            accelerationY = gravity;
        } else {
            accelerationY = gravity + (forceY / mass);
        }
    }
    public void findAccelerationX() {
        accelerationX = (forceX / mass);
    }


    public void shoot() {
		if(shotCooldown <= 0.0){
        bullet b = new bullet(this, 50);
        bullets.add(b);
        pew.play(false);
		shotCooldown = shotCooldownStore;
		}
    }
	


    }