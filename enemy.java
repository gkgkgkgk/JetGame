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
	double maxHealth = 50;
	double health = 50;
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
	Image img = new ImageIcon(this.getClass().getResource("images/plane-1Enemy.png")).getImage();	
	Image particleImg = new ImageIcon(this.getClass().getResource("images/player.png")).getImage();
	ArrayList < bullet > bullets = new ArrayList < bullet > ();
	int shotCoolDown = 100; // 1 bullet per second
    int particleCounter = 0;
	Rectangle bounds = new Rectangle((int)xPos, (int)yPos,30,30);

	boolean target = true;

	player playerTarget;
	enemy enemyTarget;



	public enemy (int x, int y, player t){ //hehe
		this.yPos = y;
		this.xPos = x;
		this.playerTarget = t;
	}
	public enemy (int x, int y, enemy t){ //hehe
		this.yPos = y;
		this.xPos = x;
		this.enemyTarget = t;
		//enemy constructer for targeting enemies for AI testing
	}
	public enemy (int x, int y){ //hehe
		this.yPos = y;
		this.xPos = x;
	}
	
	public void move(){	
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
        if(Math.abs(desiredRot - rotation) < 5 && shotCoolDown <= 0 && target){ // if target is within 5 degress, change to velocity later
        	shoot();
		}
		bounds = new Rectangle((int)xPos, (int)yPos, 30,30);
	}
	

	public void shoot(){
        bullets.add(new bullet(this, 20));
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
		for(int i = 0; i < bullets.size(); i++){
			bullet b = bullets.get(i);
			Rectangle r2 = b.bounds;
			 if (r2.intersects(bounds)) {
                //System.out.println("Enemy Collision with bullet at" + xPos+", "+yPos);
                health -= 10;
                bullets.remove(b);
            }
		}
	}

public void checkCollision(player p){
			Rectangle r2 = p.bounds;
			 if (r2.intersects(bounds) && p.boost) {
                //System.out.println("Enemy Collision with bullet at" + xPos+", "+yPos);
                health -= 25;
                p.health -= 0;//p.maxHealth; //remember to take health away from player for colliding (wtihout crashing everything please!)
            }
		}
	
}
