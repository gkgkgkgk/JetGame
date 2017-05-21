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

public class bullet {
        double xPos;
        double yPos;
        double rotation;
        double yRot;
        double xRot;
        double speed;
        Image img = new ImageIcon(this.getClass().getResource("images/bullet.png")).getImage();
        Image particleImg = new ImageIcon(this.getClass().getResource("images/player.png")).getImage();
        Rectangle bounds = new Rectangle((int)xPos, (int)yPos, 5,5);
        double lifeTime;
        ArrayList < particleTrail > trail = new ArrayList < particleTrail > ();

        int particleCounter = 0;
        public bullet(player p, double speed) {
            this.speed = speed;
            xPos = 10 + p.xPos + (15 * (Math.sin(Math.toRadians(p.rotation))));
            yPos = 10 + p.yPos + (15 * (-Math.cos(Math.toRadians(p.rotation))));

            yRot = -Math.cos(Math.toRadians(p.rotation));
            xRot = Math.sin(Math.toRadians(p.rotation));
        }
        public bullet(multiplayer p, double speed) {
        	bounds = new Rectangle((int)xPos-5, (int)yPos-5, 10,10);
            this.speed = speed;
            xPos = 10 + p.xPos + (15 * (Math.sin(Math.toRadians(p.rotation))));
            yPos = 10 + p.yPos + (15 * (-Math.cos(Math.toRadians(p.rotation))));

            yRot = -Math.cos(Math.toRadians(p.rotation));
            xRot = Math.sin(Math.toRadians(p.rotation));
        }
        public bullet(enemy p, double speed) {
            this.speed = speed;
            xPos = 10 + p.xPos + (15 * (Math.sin(Math.toRadians(p.rotation))));
            yPos = 10 + p.yPos + (15 * (-Math.cos(Math.toRadians(p.rotation))));

            yRot = -Math.cos(Math.toRadians(p.rotation));
            xRot = Math.sin(Math.toRadians(p.rotation));
        }
        public bullet(boss b, double speed, double posX, double posY){
            this.speed = speed;
            xPos = posX;
            yPos = posY;

            yRot = -Math.cos(Math.toRadians(b.rotation));
            xRot = Math.sin(Math.toRadians(b.rotation));
        }


    }
