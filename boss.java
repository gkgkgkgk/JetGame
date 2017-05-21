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


public class boss{

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
	Image img;
	Image particleImg = new ImageIcon(this.getClass().getResource("images/player.png")).getImage();
	ArrayList < bullet > bullets = new ArrayList < bullet > ();
	int shotCoolDown = 50; // 1 bullet per second
    int particleCounter = 0;
	Rectangle bounds = new Rectangle((int)xPos, (int)yPos,30,30);
	double elapsedTime = 0.016;
	boolean target = true;
	int size = 30;
	public int phase = 1; //3 phases of the boss
	double rotation = 0;
	double desiredRot;
	player playerTarget;
	int reward = 1000;
	String name;

	public boss(){

	}

	public void move(){
	}

	public void lerpRotation(){}


	public void checkCollision(ArrayList<bullet> bullets){}
	public void checkCollision(player p){}


}