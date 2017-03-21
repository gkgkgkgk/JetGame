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


public class Survival extends JPanel implements KeyListener, ActionListener {

    saveToXML save = new saveToXML();
    //you lose! stuff
    JLabel youLose = new JLabel("You Lose!");
    JButton restart = new JButton("Restart!");


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
    public static Survival j;
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
    boolean boost = false;


    JLabel wave;
    int waveNumber = 0;
    boolean waveOver = false;
    JLabel scoreText;
    int score;
    JLabel comboText;
    int combo = 1;
    double comboCounter = 3.0;


    public enum state {
        UP,
        DOWN,
        RIGHT,
        LEFT
    };
    public state s;

    Color bgColor = new Color(35, 106, 135);


    public Survival() {
        wave = new JLabel("Wave " + waveNumber);
        scoreText = new JLabel("Score: 0");
        comboText = new JLabel("Combo: " + combo);
        Timer t = new Timer(10, this);
        t.start();
        p.xPos = 640;
        p.yPos = 360;
        setBackground(bgColor);
        w = new JFrame();
        w.setSize(1280, 720);
        w.setContentPane(this);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.addKeyListener(this);
        add(wave);
        add(scoreText);
        add(comboText);
        w.setResizable(false);
        w.setVisible(true);
       
    }


    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == restart){
            //this = new Survival();
        }
    	if(comboCounter <= 0){
    		combo = 1;
    		comboText.setText("Combo: "+combo);
    	}
    	else{
    	comboCounter -= 0.01;
    	}
        if(enemies.size() == 0){
            wave.setText("Wave " + waveNumber);
            waveNumber += 1;
            int x = ((1280/waveNumber +1));
            for(int i = 0; i < waveNumber-1; i++){
                enemies.add(new enemy(x, 0, p, 100));
                 x += (1280/waveNumber +1);
            }
        }
        //particle trails and bullets for enemies
        for (int i = 0; i < enemies.size(); i++) {
            // needed to switch to a regular for loop because the foreach loop 
            // has issues with iterators and removing objects. Instead of using the iterator i just switched directly
            // to a regular for loop.    
            enemy en = enemies.get(i);
            if (en.playerTarget != null) {
                            System.out.println(en.playerTarget == null);

                en.targetPosX = en.playerTarget.xPos;
                en.targetPosY = en.playerTarget.yPos;
                en.checkCollision(bullets); //every enemy should check for a collision with bullets
                en.checkCollision(en.playerTarget);
                en.playerTarget.checkCollision(en.bullets); //check player collision with enemy bullets
                //WHY DOES IT CRASH WHEN THE PLAYER DIES?!?!?!?!?!?!?!?!?!? NVM I FIXED IT
            } else {
                en.targetPosX = 640;
                en.targetPosY = 360;
                en.target = false;
            }
            en.move();
            
            if (en.particleCounter <= 100) {
                en.particleCounter += 1;
                en.trail.add(new particleTrail(en));
            } else {
                en.trail.remove(0);
                en.particleCounter -= 1;
            }
            //bullet particles/trails
            for (int f = 0; f < en.bullets.size(); f++) {
                bullet b = en.bullets.get(f);
                if (b.particleCounter <= 10) {
                    b.particleCounter += 1;
                    b.trail.add(new particleTrail(b));
                } else {
                    b.trail.remove(0);
                    b.particleCounter -= 1;
                }
                b.xPos += b.speed * b.xRot;
                b.yPos += b.speed * b.yRot;
                b.bounds = new Rectangle((int) b.xPos, (int) b.yPos, 5, 5);
                //remove stray bullets
                b.lifeTime += 0.01;
                if(b.lifeTime>=300){
                    en.bullets.remove(b);
                }
            }
            if (en.health <= 0) {
                explosions.add(new explosion(50, (int) en.xPos, (int) en.yPos));
                addScore(en.reward * combo); //add player score
                comboCounter = 3.0; //reset combo counter
                combo +=1;
                comboText.setText("Combo: "+combo);
                enemies.remove(en);
            }
        }
        //explosion stuff
        for (int i = 0; i < explosions.size(); i++) {
            explosion ex = explosions.get(i);
            if (ex.particles.size() != 0) {
                for (int z = 0; z < ex.particles.size(); z++) {
                    explosionParticle ep = ex.particles.get(z);
                    if (ep.lifeTime >= 0) {
                        ep.posX += ep.speed * Math.sin(Math.toRadians(ep.rotation));
                        ep.posY -= ep.speed * Math.cos(Math.toRadians(ep.rotation));
                        ep.lifeTime -= 0.01;
                        if (ep.trailBool) {
                            if (ep.particleCounter <= 10) {
                                ep.particleCounter += 1;
                                ep.trail.add(new particleTrail(ep));
                            } else {
                                ep.trail.remove(0);
                                ep.particleCounter -= 1;
                            }
                        }
                    } else {
                        ex.particles.remove(ep);
                    }
                }
            } else {
                explosions.remove(ex);
            }

        }


        if (p != null) { //if the player is not dead
            //manage Particles
            if (particleCounter <= 100) {
                particleCounter += 1;
                trail.add(new particleTrail(p));
            } else {
                trail.remove(0);
                particleCounter -= 1;
            }
            //bullet particles/trails
            for (int bu = 0; bu < bullets.size(); bu++) {
                bullet b = bullets.get(bu);
                if (b.particleCounter <= 10) {
                    b.particleCounter += 1;
                    b.trail.add(new particleTrail(b));
                } else {
                    b.trail.remove(0);
                    b.particleCounter -= 1;
                }
                b.bounds = new Rectangle((int) b.xPos, (int) b.yPos, 5, 5);
                b.xPos += b.speed * b.xRot;
                b.yPos += b.speed * b.yRot;
                if(b.xPos < -50 || b.xPos > 1330 || b.yPos < -50 || b.yPos > 770){
                    bullets.remove(bu);
                }
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

            //boost!
            if (boost) {
                maxVelocity = 500;
                velocityX = 500 * Math.sin(Math.toRadians(p.rotation));
                velocityY = -500 * Math.cos(Math.toRadians(p.rotation));
            } else {
                maxVelocity = 100;
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
            } else {
                forceX = 0;
                forceY = 0;
            }
            }
            //System.out.println(p.health);
            if(p.xPos < 0){
                p.xPos = 1280;
            }
            if (p.xPos > 1280){
                p.xPos = 0;
            }
            if (p.yPos < 0){
                p.yPos = 720;
            }
            if (p.yPos > 720){
                p.yPos = 0;
            }
            
            p.regenerateHealth();
            //if player is dead....
            if (p.health <= 0) {
                explosions.add(new explosion(50, (int) p.xPos, (int) p.yPos));
                save.readFile();
                if(Integer.parseInt(save.getHighScoreSurvival()) < score){ //if high score is broken
                    save.setHighScoreSurvival(String.valueOf(score));
                    save.writeToFile(); //write to file
                }
                makeLosePanel();
                p = null;
                for (int i = 0; i < enemies.size(); i++) {
                enemy en = enemies.get(i);
                en.playerTarget = null;
                }
            }

        }
        time++;
        repaint();
        //System.out.println("(X,Y) | Acceleration " + accelerationX + ", " + accelerationY + " 
        //| Velocity " + velocityX + ", " + velocityY + " | Force " + forceX + ", " + forceY);
        //System.out.println(velocityX);
    }

    public void makeLosePanel(){
        this.add(youLose);
        this.add(restart);
    }


    public void addScore(int addition){
    	score += addition;
    	scoreText.setText("Score: "+score);
    }


    public void findVelocityY() {
        if (forward) {
            if (Math.abs(velocityY) < maxVelocity) {
                velocityY += accelerationY * 0.01;
            } else if (velocityY > 0) {
                velocityY = maxVelocity - 1;
            } else if (velocityY < 0) {
                velocityY = (-maxVelocity) + 1;
            }
        } else {
            velocityY += accelerationY * 0.01;
        }
    }
    public void findVelocityX() {
        if (forward) {
            if (Math.abs(velocityX) < maxVelocity) {
                velocityX += accelerationX * 0.01;
            } else if (velocityX > 0) {
                velocityX = maxVelocity - 1;
            } else if (velocityX < 0) {
                velocityX = (-maxVelocity) + 1;
            }
        } else {
            if (velocityX == 0) {

            } else if (velocityX > 0) {
                velocityX -= mass * 0.01;
            } else if (velocityX < 0) {
                velocityX += mass * 0.01;
            }

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
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !boost && p!=null) { // cant shoot while boosting
            //System.out.println("Shoot");
            shoot();
        }
        if (e.getKeyChar() == 's' && p != null) {
            //System.out.println("space pressed");
            boost = true;
            p.boost = true;
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
        if (e.getKeyChar() == 's' && p != null) {
            //System.out.println("space released");
            boost = false;
            p.boost = false;
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
            0.1f);
        if (p != null) {
            for (particleTrail p: trail) {
                c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    (float)(1f / pCounter));
                g2d.setComposite(c);
                pCounter -= 1;
                //g2d.drawImage(p.img, (int) p.xPos, (int) p.yPos, 5, 5, j);
                g2d.setColor(p.color);
                g2d.fillRect((int)p.xPos,(int)p.yPos,p.size, p.size);
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

        for (explosion ex: explosions) {
            for (explosionParticle ep: ex.particles) {
                g2d.drawImage(ep.img, (int) ep.posX, (int) ep.posY, 5, 5, j);
                pCounter = ep.trail.size();
                for (particleTrail p: ep.trail) {
                    c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1f / pCounter));
                    g2d.setComposite(c);
                    pCounter -= 1;
                    g2d.drawImage(p.img, (int) p.xPos, (int) p.yPos, p.size, p.size, j);
                }
            }
        }

    }

}
