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
	double xPos;
	double yPos;
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
	double rotationSpeed;
	double targetPosX;
	double targetPosY;
	
	
	Image img = new ImageIcon(this.getClass().getResource("plane-1.png")).getImage();	
	public enemy (){ //hehe
	}
	
	
	
	}
