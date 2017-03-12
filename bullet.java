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
        Image img = new ImageIcon(this.getClass().getResource("bullet.png")).getImage();
        Image particleImg = new ImageIcon(this.getClass().getResource("player.png")).getImage();

        ArrayList < particleTrail > trail = new ArrayList < particleTrail > ();

        int particleCounter = 0;
        public bullet(player p, double speed) {
            this.speed = speed;
            xPos = 10 + p.xPos + (15 * (Math.sin(Math.toRadians(p.rotation))));
            yPos = 10 + p.yPos + (15 * (-Math.cos(Math.toRadians(p.rotation))));

            yRot = -Math.cos(Math.toRadians(p.rotation));
            xRot = Math.sin(Math.toRadians(p.rotation));
        }


    }
