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

public class particleTrail {
        Image img = new ImageIcon(this.getClass().getResource("images/player.png")).getImage();
        double xPos;
        double yPos;
        int size = 5;
                //particle trail for plane/player
        public particleTrail(player p) {
                //its 10 so it is behind the plane, make it look like it is coming out of the plane.
                xPos = 10 + p.xPos - (15 * (Math.sin(Math.toRadians(p.rotation))));
                yPos = 10 + p.yPos - (15 * (-Math.cos(Math.toRadians(p.rotation))));
            }
            //particle trail for bullet
        public particleTrail(bullet b) {
            xPos = 2.5 + b.xPos - (2.5 * (Math.sin(Math.toRadians(b.rotation))));
            yPos = 2.5 + b.yPos - (2.5 * (-Math.cos(Math.toRadians(b.rotation))));
        }
        public particleTrail(enemy e) {
        	    img = new ImageIcon(this.getClass().getResource("images/player.png")).getImage();
        	     if((e.health/e.maxHealth)-Math.random() < 0 && (e.health/e.maxHealth)-Math.random() > -0.25){
        	     	size = (int)Math.random()*3+8;
                	img = new ImageIcon(this.getClass().getResource("images/black.png")).getImage();
                }
                else if((e.health/e.maxHealth)-Math.random() < -0.25){
                	double d = Math.random();
                	if(d > 0.5){
                		size = 7;
                	img = new ImageIcon(this.getClass().getResource("images/red.png")).getImage();
                	}
                	else{
                		size = 5;
                		img = new ImageIcon(this.getClass().getResource("images/orange.png")).getImage();
                	}
                }
                xPos = 10 + e.xPos - (15 * (Math.sin(Math.toRadians(e.rotation))));
                yPos = 10 + e.yPos - (15 * (-Math.cos(Math.toRadians(e.rotation))));
            }
        public particleTrail(explosionParticle e) {
            //particle trail for plane/player
            xPos = 1 + e.posX - (1 * (Math.sin(Math.toRadians(e.rotation))));
            yPos = 1 + e.posY - (1 * (-Math.cos(Math.toRadians(e.rotation))));
            img = new ImageIcon(this.getClass().getResource("images/red.png")).getImage();
        }
    }