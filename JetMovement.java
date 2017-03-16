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
    ArrayList < explosion > explosions = new ArrayList < explosion > ();
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
        enemies.add(new enemy(300,100));
    }


    public void actionPerformed(ActionEvent e) {
        //particle trails and bullets for enemies
        for (int i = 0; i < enemies.size(); i++) { 
            // needed to switch to a regular for loop because the foreach loop 
            // has issues with iterators and removing objects. Instead of using the iterator i just switched directly
            // to a regular for loop.    
            enemy en = enemies.get(i);
            if(p != null){
            en.targetPosX = p.xPos;
            en.targetPosY = p.yPos;
            p.checkCollision(en.bullets); //check player collision with enemy bullets

        	}
        	else{
        		en.targetPosX = 640;
           		en.targetPosY = 360;
           		en.target = false;
        	}
            en.move();
            en.checkCollision(bullets); //every enemy should check for a collision with bullets
            if (en.particleCounter <= 100) {
                en.particleCounter += 1;
                en.trail.add(new particleTrail(en));
            } else {
                en.trail.remove(0);
                en.particleCounter -= 1;
            }
            //bullet particles/trails
            for (bullet b: en.bullets) {
                if (b.particleCounter <= 10) {
                    b.particleCounter += 1;
                    b.trail.add(new particleTrail(b));
                } else {
                    b.trail.remove(0);
                    b.particleCounter -= 1;
                }
                b.xPos += b.speed * b.xRot;
                b.yPos += b.speed * b.yRot;
                b.bounds = new Rectangle((int)b.xPos, (int)b.yPos, 5,5);
            }
            if(en.health <= 0){
                explosions.add(new explosion(50, (int)en.xPos, (int)en.yPos));
                enemies.remove(en);
            }
        }
        //explosion stuff
        for(int i = 0; i < explosions.size(); i++){
            explosion ex = explosions.get(i);
            if(ex.particles.size() != 0){
            for(int z = 0; z < ex.particles.size(); z++){
                explosionParticle ep = ex.particles.get(z);
                if(ep.lifeTime >=0){
                ep.posX +=  ep.speed * Math.sin(Math.toRadians(ep.rotation));
                ep.posY -= ep.speed * Math.cos(Math.toRadians(ep.rotation));
                ep.lifeTime -= 0.01;
                if(ep.trailBool){
                if (ep.particleCounter <= 10) {
                ep.particleCounter += 1;
                ep.trail.add(new particleTrail(ep));
            } else {
                ep.trail.remove(0);
                ep.particleCounter -= 1;
            }
        }
            }
            else{
                ex.particles.remove(ep);
            }
            }
        }
        else{
            explosions.remove(ex);
        }
        
        }

		
	    if(p != null){ //if the player is not dead
        //manage Particles
        if (particleCounter <= 100) {
            particleCounter += 1;
            trail.add(new particleTrail(p));
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
        b.bounds = new Rectangle((int)b.xPos, (int)b.yPos, 5,5);
        b.xPos += b.speed * b.xRot;
            b.yPos += b.speed * b.yRot;
        }
        if (p.rotation > 360) {
            p.rotation -= 360;
        } else if (p.rotation < -360) {
            p.rotation += 360;
        }
        //image stuff
        if ((Math.abs(p.rotation) > 315) || (Math.abs(p.rotation) < 45) || (Math.abs(p.rotation) > 135 && Math.abs(p.rotation) < 225)) {
            p.img = new ImageIcon(this.getClass().getResource("images/plane-1.png")).getImage();
            s = state.UP;
        } else if ((p.rotation <= 135 && p.rotation >= 45) || (p.rotation >= -315 && p.rotation <= -225)) {
            p.img = new ImageIcon(this.getClass().getResource("images/planeRight.png")).getImage();
            s = state.RIGHT;
        } else {
            p.img = new ImageIcon(this.getClass().getResource("images/planeLeft.png")).getImage();
            s = state.LEFT;
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
            forceX = 350 * Math.sin(Math.toRadians(p.rotation)); // the number 500 is the thrust
            forceY = -350 * Math.cos(Math.toRadians(p.rotation));
        }
        p.regenerateHealth();
        //if player is dead....
        if(p.health <= 0){
            explosions.add(new explosion(50, (int)p.xPos, (int)p.yPos));
			p = null;
	    }
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
        bullet b = new bullet(p, 20);
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
        if(p != null){
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

                g2d.drawImage(b.particleImg, (int) part.xPos, (int) part.yPos, part.size, part.size, j);
            }
            g2d.drawImage(b.img, (int) b.xPos, (int) b.yPos, 5, 5, j);
        }
    }
        //draw enemies
        for (enemy e: enemies) {
            //System.out.println("enemies are being drawn");
            g2d.translate(e.xPos, e.yPos); // Translate the center of our coordinates.
            g2d.rotate(Math.toRadians(e.rotation), 15, 15);
            g2d.drawImage(e.img, 0, 0, e.width, e.height, j);
            pCounter = e.trail.size();
            g2d.setTransform(old);
            for (particleTrail part: e.trail) {
                c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1f / pCounter));
                g2d.setComposite(c);
                pCounter -= 1;
                g2d.drawImage(part.img, (int) part.xPos, (int) part.yPos, part.size, part.size, j);
            }
            for (bullet b: e.bullets) {
                pCounter = b.trail.size();
                for (particleTrail part: b.trail) {
                    c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1f / pCounter));
                    g2d.setComposite(c);
                    pCounter -= 1;
                    g2d.drawImage(b.particleImg, (int) part.xPos, (int) part.yPos, part.size, part.size, j);
                }
                g2d.drawImage(b.img, (int) b.xPos, (int) b.yPos, 5, 5, j);
            }
        }
        g2d.setTransform(old);

        for(explosion ex : explosions){
            for(explosionParticle ep : ex.particles){
                g2d.drawImage(ep.img, (int) ep.posX, (int) ep.posY, 5, 5, j);
                pCounter = ep.trail.size();
                for(particleTrail p : ep.trail){
                	c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1f / pCounter));
                	g2d.setComposite(c);
                pCounter -= 1;
                g2d.drawImage(p.img, (int) p.xPos, (int) p.yPos, p.size, p.size, j);
            }
            }
        }

    }
    /*
    public static void main(String[] args) {
        j = new JetMovement();
    }*/

}