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
	double maxVelocity = 50.0;
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

    //ArrayList < particleTrail > trail = new ArrayList < particleTrail > ();

    int particleCounter = 0;
	public enemy (){ //hehe
	
	}
	
	public void move(){	
        findVelocityY();
        findAccelerationX();
        findVelocityX();
        findAccelerationY();
        yPos += velocityY * 0.02; 
        xPos += velocityX * 0.02;
        lerpRotation(); //smoother, more inaccurate rotation
		System.out.println(Math.toDegrees(Math.atan((targetPosY - this.yPos) / (targetPosX - this.xPos))));
		forceX = 100 * Math.sin(Math.toRadians(rotation));
        forceY = -100 * Math.cos(Math.toRadians(rotation));
        if(Math.abs(desiredRot - rotation) < 5){ // if target is within 5 degress, change to velocity later
        	shoot();
		}
	}
	

	public void shoot(){
		bullet b = new bullet(this, 20);
        bullets.add(b);
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
			rotation += 1;
		}
		else if(rotation > desiredRot){
			rotation -= 1;
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

  

	}
