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



public class cloud{
	
	ArrayList<Image> clouds = new ArrayList<Image>();

	double xPos = -100;
	double yPos = Math.random()*780;
	double size = 5;
	double speed = 1;

	Image image;

	public cloud(double size, int image){
		for(int i = 1; i < 5; i ++){
			clouds.add(new ImageIcon(this.getClass().getResource("images/cloud"+i+".png")).getImage());
		}
		this.image = clouds.get(image);
		this.size = size;
		speed = size/50.0;
	}

	public void move(){
		xPos += speed;
	}

}