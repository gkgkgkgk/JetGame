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
	
	Image img = new ImageIcon(this.getClass().getResource("player.png")).getImage();

	double posX;
	double posY;
	double rotation;
	double lifeTime;

	public explosionParticle(double rotation, double posX, double posY){
		this.posY = posY;
		this.posX = posX;
		this.rotation = rotation;

	}

	public void move(){

	}

}