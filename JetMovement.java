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


public class JetMovement extends JPanel implements KeyListener, ActionListener {

    int time = 0;
    JFrame w;

    double gravity = 9.87;
    double maxVelocity = 100.0;
    double velocityX;
    double velocityY;
    double accelerationY;
    double accelerationX;
    double forceY;
    double forceX;
    double inputForceY;
    double inputForceX;
    double mass = 1.0;
    public player p = new player();
    public static JetMovement j;
    boolean left = false;
    boolean right = false;
    boolean forward = false;
    boolean game = true;
    double rotationSpeed = 2.5;
    double lastX;
    double lastY;
    ArrayList < bullet > bullets = new ArrayList < bullet > ();
    ArrayList < particleTrail > trail = new ArrayList < particleTrail > ();
    ArrayList < enemy > enemies = new ArrayList < enemy > ();
    int particleCounter = 0;
    public enum state {
        UP,
        DOWN,
        RIGHT,
        LEFT
    };
    public state s;

    Color bgColor = new Color(35, 106, 135);


    public JetMovement() {
        Timer t = new Timer(10, this);
        t.start();
        p.xPos = 640;
        p.yPos = 360;
        setBackground(bgColor);
        w = new JFrame();
        w.setSize(1280, 720);
        w.setContentPane(this);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setVisible(true);
        w.addKeyListener(this);
        enemies.add(new enemy());
    }


    public void actionPerformed(ActionEvent e) {
        //manage Particles
        if (particleCounter <= 100) {
            particleCounter += 1;
            trail.add(new particleTrail());
        } else {
            trail.remove(0);
            particleCounter -= 1;
        }
        //bullet particles/trails
        for (bullet b: bullets) {
            if (b.particleCounter <= 10) {
                b.particleCounter += 1;
                b.trail.add(new particleTrail(b));
            } else {
                b.trail.remove(0);
                b.particleCounter -= 1;
            }
        }
        
        for(enemy en : enemies){
        	en.targetPosX = p.xPos;
        	en.targetPosY = p.yPos;
        
        }


        //Normalize the rotations (map to 0-360)
        if (p.rotation > 360) {
            p.rotation -= 360;
        } else if (p.rotation < -360) {
            p.rotation += 360;
        }
        //image stuff
        if ((Math.abs(p.rotation) > 315) || (Math.abs(p.rotation) < 45) || (Math.abs(p.rotation) > 135 && Math.abs(p.rotation) < 225)) {
            p.img = new ImageIcon(this.getClass().getResource("plane-1.png")).getImage();
            s = state.UP;
        } else if ((p.rotation <= 135 && p.rotation >= 45) || (p.rotation >= -315 && p.rotation <= -225)) {
            p.img = new ImageIcon(this.getClass().getResource("planeRight.png")).getImage();
            s = state.RIGHT;
        } else {
            p.img = new ImageIcon(this.getClass().getResource("planeLeft.png")).getImage();
            s = state.LEFT;
        }
        for (bullet b: bullets) {
            b.xPos += b.speed * b.xRot;
            b.yPos += b.speed * b.yRot;
        }
        //System.out.println(p.rotation+" degrees");
        //System.out.print("Sin "+Math.sin(p.rotation));
        //System.out.print("Cos "+Math.cos(p.rotation));
        //System.out.println("Runnning"); it works
        findAccelerationY();
        findVelocityY();
        findAccelerationX();
        findVelocityX();
        //System.out.println("position:"+ p.yPos + "  velocity: "+ velocityY);
        p.yPos += velocityY * 0.02; //realistacally should be 0.01 not 0.02, 
        p.xPos += velocityX * 0.02; //but 0.02 makes the game quicker and the plane more manueverable
        if (right) {
            //System.out.println("right");
            p.rotation += rotationSpeed;
        }
        if (left) {
            //System.out.println("left");
            p.rotation -= rotationSpeed;
        }
        if (forward) {
            forceX = 100 * Math.sin(Math.toRadians(p.rotation));
            forceY = -100 * Math.cos(Math.toRadians(p.rotation));
        }
        //	/System.out.println("Velocty: "+velocity);
        time++;
        repaint();
        //System.out.println("(X,Y) | Acceleration " + accelerationX + ", " + accelerationY + " 
        //| Velocity " + velocityX + ", " + velocityY + " | Force " + forceX + ", " + forceY);
        //System.out.println(velocityX);
    }




    public void findVelocityY() {
        if (forward) {
            if (Math.abs(velocityY) < maxVelocity) {
                velocityY += accelerationY * 0.01;
            } else if (velocityY > 0) {
                velocityY = maxVelocity - 1;
            } else if (velocityX < 0) {
                velocityY = (-maxVelocity) + 1;
            }
        } else {
            velocityY += accelerationY * 0.01;
        }
    }
    public void findVelocityX() {
        if (Math.abs(velocityX) < maxVelocity) {
            velocityX += accelerationX * 0.01;
        } else if (velocityX > 0) {
            velocityX = maxVelocity - 1;
        } else if (velocityX < 0) {
            velocityX = (-maxVelocity) + 1;
        }
    }


    public void findAccelerationY() {
        if (!forward) {
            accelerationY = gravity;
        } else {
            accelerationY = gravity + (forceY / mass);
        }
    }
    public void findAccelerationX() {
        accelerationX = (forceX / mass);
    }


    public void shoot() {
        bullet b = new bullet(20);
        bullets.add(b);
    }


    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'd') {
            //System.out.println("d pressed");
            right = true;
        }
        if (e.getKeyChar() == 'a') {
            //System.out.println("a pressed");
            left = true;
        }
        if (e.getKeyChar() == 'w') {
            //System.out.println("w pressed");
            forward = true;
        }
        if (e.getKeyChar() == 's') {
            //System.out.println("Shoot");
            shoot();
        }

    }
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'd') {
            //System.out.println("d released");
            right = false;
        }
        if (e.getKeyChar() == 'a') {
            //System.out.println("a released");
            left = false;
        }
        if (e.getKeyChar() == 'w') {
            //System.out.println("w released");
            forward = false;
        }


    }
    public void keyTyped(KeyEvent e) {}



    class player {
        double xPos;
        double yPos;
        double width = 30.0;
        double height = 30.0;
        double rotation;
        Image img = new ImageIcon(this.getClass().getResource("plane-1.png")).getImage();
        public player() {


        }

    }

    class bullet {
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
        public bullet(double speed) {
            this.speed = speed;
            xPos = 10 + p.xPos + (15 * (Math.sin(Math.toRadians(p.rotation))));
            yPos = 10 + p.yPos + (15 * (-Math.cos(Math.toRadians(p.rotation))));

            yRot = -Math.cos(Math.toRadians(p.rotation));
            xRot = Math.sin(Math.toRadians(p.rotation));
        }


    }



    class particleTrail {
        Image img = new ImageIcon(this.getClass().getResource("player.png")).getImage();
        double xPos;
        double yPos;

        public particleTrail() {
                //particle trail for plane/player
                xPos = 10 + p.xPos - (15 * (Math.sin(Math.toRadians(p.rotation))));
                yPos = 10 + p.yPos - (15 * (-Math.cos(Math.toRadians(p.rotation))));
            }
            //particle trail for bullet
        public particleTrail(bullet b) {
            xPos = 2.5 + b.xPos - (2.5 * (Math.sin(Math.toRadians(p.rotation))));
            yPos = 2.5 + b.yPos - (2.5 * (-Math.cos(Math.toRadians(p.rotation))));
        }
    }




    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; // Create a Java2D version of g.
        //store old rotation
        AffineTransform old = g2d.getTransform();
        float pCounter = trail.size();
        //change transparency
        //draw trail
        Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
            .1f);
        for (particleTrail p: trail) {
            c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                (float)(1f / pCounter));
            g2d.setComposite(c);
            pCounter -= 1;
            g2d.drawImage(p.img, (int) p.xPos, (int) p.yPos, 5, 5, j);
        }
        //draw player
        g2d.translate(p.xPos, p.yPos); // Translate the center of our coordinates.
        g2d.rotate(Math.toRadians(p.rotation), 15, 15);
        g2d.drawImage(p.img, 0, 0, 30, 30, j);
        //draw bullets
        g2d.setTransform(old);
        for (bullet b: bullets) {
            g2d.setTransform(old);
            pCounter = b.trail.size();
            for (particleTrail part: b.trail) {
                c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1f / pCounter));
                g2d.setComposite(c);
                pCounter -= 1;

                g2d.drawImage(b.particleImg, (int) part.xPos, (int) part.yPos, 5, 5, j);
            }
            g2d.drawImage(b.img, (int) b.xPos, (int) b.yPos, 5, 5, j);
        }

		//draw enemies
		for(enemy e : enemies){
			g2d.drawImage(e.img, (int) e.xPos, (int) e.yPos, e.width, e.height, j);	
		}


    }

    public static void main(String[] args) {
        j = new JetMovement();
    }

}