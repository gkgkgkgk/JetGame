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




public class enemy {
	//variables to control AI
	double mass = 1.0;
	double xPos = 300;
	double yPos = 100;
	int width = 30;
	int height = 30;
	double maxVelocity = 100.0;
    double velocityX;
    double velocityY;
    double accelerationY;
    double accelerationX;
    double forceY;
    double forceX;
	double rotation;
	double desiredRot;
	double rotationSpeed;
	double targetPosX;
	double targetPosY;
	double gravity = 9.87;
	ArrayList < particleTrail > trail = new ArrayList < particleTrail > ();
	Image img = new ImageIcon(this.getClass().getResource("plane-1.png")).getImage();	
	Image particleImg = new ImageIcon(this.getClass().getResource("player.png")).getImage();
	ArrayList < bullet > bullets = new ArrayList < bullet > ();
	int shotCoolDown = 100; // 1 bullet per second
    int particleCounter = 0;
	Rectangle bounds = new Rectangle(30,30, (int)xPos, (int)yPos);

	public enemy (){ //hehe
	
	}
	
	public void move(){	
		//System.out.println(shotCoolDown);
        findVelocityY();
        findAccelerationX();
        findVelocityX();
        findAccelerationY();
        yPos += velocityY * 0.02; 
        xPos += velocityX * 0.02;
        lerpRotation(); //smoother, more inaccurate rotation
		//System.out.println(Math.toDegrees(Math.atan((targetPosY - this.yPos) / (targetPosX - this.xPos))));
		forceX = 350 * Math.sin(Math.toRadians(rotation));
        forceY = -350 * Math.cos(Math.toRadians(rotation));
        shotCoolDown -= 1;
        if(Math.abs(desiredRot - rotation) < 5 && shotCoolDown <= 0){ // if target is within 5 degress, change to velocity later
        	shoot();
		}
		bounds = new Rectangle((int)xPos, (int)yPos, 30,30);
	}
	

	public void shoot(){
		bullet b = new bullet(this, 20);
        bullets.add(b);
        shotCoolDown = 100;
	}


	public void lerpRotation(){
		if(targetPosX >= xPos){
		desiredRot = 90 + Math.toDegrees(Math.atan((targetPosY - this.yPos) / (targetPosX - this.xPos)));
		}
		else{
			//adding 270 for some rotation reason....  i think
			desiredRot = 270 + Math.toDegrees(Math.atan((targetPosY - this.yPos) / (targetPosX - this.xPos)));	
		}
		if(rotation < desiredRot){
			rotation += 2;
		}
		else if(rotation > desiredRot){
			rotation -= 2;
		}
	}

 public void findVelocityY() {
       
            if (Math.abs(velocityY) < maxVelocity) {
                velocityY += accelerationY * 0.01;
            } else if (velocityY > 0) {
                velocityY = maxVelocity - 1;
            } else if (velocityX < 0) {
                velocityY = (-maxVelocity) + 1;
            }
        
    }
    public void findVelocityX() {
        if (Math.abs(velocityX) < maxVelocity) {
            velocityX += accelerationX * 0.01;
        } else if (velocityX > 0) {
            velocityX = maxVelocity - 1;
        } else if (velocityX < 0) {
            velocityX = (-maxVelocity) + 1;
        }
    }


    public void findAccelerationY() {
       
            accelerationY = gravity + (forceY / mass);
        
    }
    public void findAccelerationX() {
        accelerationX = (forceX / mass);
    }

	public void checkCollision(ArrayList<bullet> bullets){
		for(bullet b : bullets){
			Rectangle r2 = b.bounds;
			 if (r2.intersects(bounds)) {
                System.out.println("Enemy Collision with bullet at" + xPos+", "+yPos);
            }
		}
	}  

	}
