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



public class Missile{
	
	double xPos;
	double yPos;
	double targetX;
	double targetY;
	double rotation;
	Image image;
	player p;

	public Missile(player p){
		this.p = p;
	}

	public void move(){
		targetX = p.xPos;
		targetY = p.yPos;
	}

}