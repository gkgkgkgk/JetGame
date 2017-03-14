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


public class explosionParticle{
	
	Image img = new ImageIcon(this.getClass().getResource("images/player.png")).getImage();

	double posX;
	double posY;
	double rotation;
	double lifeTime = 1.0;
	double speed;
	public explosionParticle(double rotation, double posX, double posY){
		lifeTime = Math.random() + 1;
		speed = Math.random()*10;
		this.posY = posY;
		this.posX = posX;
		this.rotation = rotation;

	}

	public void move(){

	}

}