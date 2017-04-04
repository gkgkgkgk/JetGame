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


public class Helicopter extends enemy{
	
	int imageCounter = 0;
	Image img1 = new ImageIcon(this.getClass().getResource("images/heli.png")).getImage();
	Image img2 = new ImageIcon(this.getClass().getResource("images/heli2.png")).getImage();

	double targetX;
	double targetY;

	public Helicopter(int x, int y, player t, int r){
		this.yPos = y;
		this.xPos = x;
		this.playerTarget = t;
		this.reward = r;
		this.img = new ImageIcon(this.getClass().getResource("images/heli.png")).getImage();
		targetX = t.xPos;
		targetY = t.yPos;
	}

	
	public void move(){
		if(imageCounter > 5){
			if(this.img == img1){
			this.img = img2;
			imageCounter = 0;
			}
			else if(this.img == img2){
			this.img = img1;
			imageCounter = 0;
			}
		}
		else{
			imageCounter += 1;

		}
		if(Math.abs(xPos - targetX)<10){
			targetX = Math.random() * 1280;
		}
		if(Math.abs(yPos - targetY)<10){
			targetY = Math.random() * 760;
		}

		if(this.xPos <= targetX) {
			this.xPos += 1.5;
		}
		else{
			this.xPos -= 1.5;
		}

		if(this.yPos <= targetY) {
			this.yPos += 1.5;
		}
		else{
			this.yPos -= 1.5;
		}

		bounds = new Rectangle((int)xPos, (int)yPos, 30,30);

	}

	public void shoot(){

	}
}