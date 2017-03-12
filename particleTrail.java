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
        Image img = new ImageIcon(this.getClass().getResource("player.png")).getImage();
        double xPos;
        double yPos;

        public particleTrail(player p) {
                //particle trail for plane/player
                xPos = 10 + p.xPos - (15 * (Math.sin(Math.toRadians(p.rotation))));
                yPos = 10 + p.yPos - (15 * (-Math.cos(Math.toRadians(p.rotation))));
            }
            //particle trail for bullet
        public particleTrail(bullet b) {
            xPos = 2.5 + b.xPos - (2.5 * (Math.sin(Math.toRadians(b.rotation))));
            yPos = 2.5 + b.yPos - (2.5 * (-Math.cos(Math.toRadians(b.rotation))));
        }
        public particleTrail(enemy e) {
                //particle trail for plane/player
                xPos = 10 + e.xPos - (15 * (Math.sin(Math.toRadians(e.rotation))));
                yPos = 10 + e.yPos - (15 * (-Math.cos(Math.toRadians(e.rotation))));
            }
    }