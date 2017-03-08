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
    double maxVelocity = 50.0;
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
    ArrayList<bullet> bullets = new ArrayList<bullet>();


    public JetMovement() {
        Timer t = new Timer(10, this);
        t.start();
        p.xPos = 400;
        p.yPos = 300;
        setBackground(Color.RED);
        w = new JFrame();
        w.setSize(800, 600);
        w.setContentPane(this);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setVisible(true);
        w.addKeyListener(this);
    }


    public void actionPerformed(ActionEvent e) {
        for(bullet b : bullets){
        b.xPos += 10*b.xRot;
        b.yPos += 10* b.yRot;
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
        p.yPos += velocityY * 0.01;
        p.xPos += velocityX * 0.01;
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
        //System.out.println("(X,Y) | Acceleration " + accelerationX + ", " + accelerationY + " | Velocity " + velocityX + ", " + velocityY + " | Force " + forceX + ", " + forceY);
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
            accelerationY = 9.8;
        } else {
            accelerationY = (forceY / mass);
        }
    }
    public void findAccelerationX() {
        accelerationX = forceX / mass;
    }


    public void shoot(){
        bullet b = new bullet();
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
        if(e.getKeyChar() == 's'){
            System.out.println("Shoot");
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
        double rotation;
        Image img = new ImageIcon(this.getClass().getResource("player.png")).getImage();


        public player() {


        }
        
    }

    class bullet {
        double xPos;
        double yPos;
        double rotation;
        double yRot;
        double xRot;
        Image img = new ImageIcon(this.getClass().getResource("player.png")).getImage();

        public bullet(){
            xPos = p.xPos;
            yPos = p.yPos;
            yRot = -Math.cos(Math.toRadians(p.rotation));
            xRot = Math.sin(Math.toRadians(p.rotation));
        }
        

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; // Create a Java2D version of g.
        AffineTransform old = g2d.getTransform();
        g2d.translate(p.xPos, p.yPos); // Translate the center of our coordinates.
        g2d.rotate(Math.toRadians(p.rotation), 5, 10); // Rotate the image..rotate(Math.toRadians(angle), m_imageWidth/2, m_imageHeight/2);
        g2d.drawImage(p.img, 0, 0, 10, 20, j);
        
        g2d.setTransform(old);
        for(bullet b : bullets){
        g2d.drawImage(b.img, (int)b.xPos, (int)b.yPos, 10, 10, j);
        }
    }

    public static void main(String[] args) {
        j = new JetMovement();
    }

}
