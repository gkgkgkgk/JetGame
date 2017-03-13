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


public class explosion {
	
	int posX;
	int posY;
	public ArrayList < explosionParticle > particles;


	public explosion(int size, int x, int y){
		this.posX = x;
		this.posY = y;
		particles = new ArrayList<explosionParticle>();
		float angle = 1;
		for(int i = 0; i < size; i++){
			particles.add(new explosionParticle(angle, posX, posY));
			angle += 360/((float)size);
		}
	}


}