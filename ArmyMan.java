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



public class ArmyMan {
	
	int xPos;
	int yPos;
	
	double speed = 2;
	
    Image img = new ImageIcon(this.getClass().getResource("images/armyMan.png")).getImage();
	
	public ArmyMan(int x, int y){
		yPos = y;
		xPos = x;
		
	}	
	
	
	public void move(){
	
		yPos += speed;		
		
	}
	
	
	public void	 Draw(Graphics g, VSMode v){
	
		g.drawImage(img, xPos, yPos, 30, 30, v);
		
		
	} 
	
	
}

