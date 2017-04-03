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
import java.lang.Math;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.awt.FontMetrics;
import java.awt.Font;
import java.io.*;
import java.io.InputStream;

public class Survival extends JPanel implements KeyListener, ActionListener {

    main main;

    saveToXML save = new saveToXML();
    //you lose! stuff
    JLabel youLose = new JLabel("YOU  DIED");
    JButton restart = new JButton("Restart!");
    JLabel highScore = new JLabel("High Score:");

    JButton startNextRound = new JButton("Continue");

    int refreshRate = 16;
    int time = 0;
    JFrame w;
    double gravity = 9.87;
    double maxVelocity = 160.0;
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
    double rotationSpeed = 5;
    double lastX;
    double lastY;
    ArrayList < bullet > bullets = new ArrayList < bullet > ();
    ArrayList < particleTrail > trail = new ArrayList < particleTrail > ();
    ArrayList < enemy > enemies = new ArrayList < enemy > ();
    ArrayList < explosion > explosions = new ArrayList < explosion > ();
    ArrayList < boss > bosses = new ArrayList < boss > ();
    ArrayList < cloud > clouds = new ArrayList < cloud > ();
    int particleCounter = 0;
    boolean boost = false;
    long startTime = System.currentTimeMillis();

    JLabel wave;
    int waveNumber = 0;
    boolean waveOver = false;
    JLabel scoreText;
    int score;
    JLabel comboText;
    int combo = 1;
    double comboCounter = 3.0;
    Timer t = new Timer();

    boss1 boss1;
    boolean inBossBattle = false;

    Rectangle healthBarBack = new Rectangle(240,600,800,30); //x y size
    Rectangle healthBarRed = new Rectangle(250,605,780,20); //x y size
    Rectangle healthBarGreen = new Rectangle(250,605,780,20); //x y size
    JLabel bossName = new JLabel("");

    enum state {
        UP,
        DOWN,
        RIGHT,
        LEFT
    };
    state s;

    Color bgColor = new Color(108,164,200);
    double elapsedTime = 0.016;
    Font font;
    Font fontBig;
    Font fontBiggest;

    double cloudAmount;

    SoundEffect pew = SoundEffect.HIT;
    SoundEffect explosion = SoundEffect.EXPLODE;

    public Survival(main m) {
        getFonts();
 	
        main = m;

        restart.setFont(fontBig);
        restart.setBounds(490, 10, 300, 100);
        restart.setFocusable(false);
        restart.addActionListener(this);


        cloudAmount = Math.random()*125; //random amount of clouds
        highScore.setBounds(450, 450, 1000,100);
        highScore.setFont(fontBig);

        bossName.setFont(font);
        bossName.setBounds(250, 525, 1000,100);
        bossName.setForeground(Color.BLACK);

        startNextRound.setBounds(490, 300, 300, 100);
        startNextRound.addActionListener(this);
        startNextRound.setFocusable(false);
        youLose.setBounds(450,300,1000,100);
        youLose.setFont(fontBiggest);
        youLose.setForeground(Color.RED);
        wave = new JLabel("Wave " + waveNumber);
        wave.setBounds(550,50,1000,100);
        wave.setFont(fontBig);
        wave.setForeground(Color.BLACK);
        scoreText = new JLabel("Score: 0");
        scoreText.setBounds(10,-20,1000,100);
        scoreText.setFont(fontBig);
        scoreText.setForeground(Color.BLACK);
        comboText = new JLabel("Combo: " + combo);
        comboText.setBounds(10,30,1000,100);
        comboText.setFont(fontBig);
        comboText.setForeground(Color.BLACK);
        p.xPos = 640;
        p.yPos = 360;
        setBackground(bgColor);
        w = new JFrame();
        w.setSize(1280, 720);
        w.setContentPane(this);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.addKeyListener(this);
        setLayout(null);
        add(wave);
        add(scoreText);
        add(comboText);
        add(bossName);
        w.setResizable(false);
        w.setVisible(true);
        loop();
        System.setProperty("sun.java2d.opengl", "true");

    }


public void getFonts() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/mainFont.ttf"))).deriveFont(Font.PLAIN, 24);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
         try {
            fontBig = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/mainFont.ttf"))).deriveFont(Font.PLAIN, 48);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            fontBiggest = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/mainFont.ttf"))).deriveFont(Font.PLAIN, 72);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


public void actionPerformed(ActionEvent e){
	if(e.getSource() == startNextRound){
		resume();
	}
}

public void loop(){
t.scheduleAtFixedRate(new TimerTask() {
      public void run() {
    

    if(clouds.size() < cloudAmount){
    	clouds.add(new cloud(10+Math.random()*90, (int)(4*Math.random())));
    }
    else{
    	for(int i = 0; i < clouds.size(); i++){
    		clouds.get(i).move();
    		if(clouds.get(i).xPos > 1300){
    			clouds.remove(clouds.get(i));
    		}
    	}
    }

    if(enemies.size() == 0 && bosses.size() == 0){
        wave.setText("Wave " + waveNumber);
        //if the wave is over
        waveNumber += 1;
        if(waveNumber % 5 != 0){
            int x = ((1280/waveNumber +1));
            for(int i = 0; i < waveNumber-1; i++){
                enemies.add(new enemy(x, 0, p, 100));
                 x += (1280/waveNumber +1);
            }
        }
        else{
            bosses.add(new boss1(p));
            bossName.setText(bosses.get(0).name);
        }
    }

        if(comboCounter <= 1){
            combo = 1;
            comboText.setText("Combo: "+combo);
        }
        else{
        comboCounter -= elapsedTime;
        }

        if(bosses.size() > 0){
            bosses.get(0).move();
            bosses.get(0).checkCollision(bullets);
            if(p != null){
            bosses.get(0).checkCollision(p);
            p.checkCollision(bosses.get(0).bullets);
            }
            bosses.get(0).bounds = new Rectangle((int) bosses.get(0).xPos, (int) bosses.get(0).yPos, bosses.get(0).size, bosses.get(0).size);
            for (int f = 0; f < bosses.get(0).bullets.size(); f++) {
                bullet b = bosses.get(0).bullets.get(f);
                if (b.particleCounter <= 5) {
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
                if(b.xPos < -50 || b.xPos > 1330 || b.yPos < -50 || b.yPos > 770){
                    bosses.get(0).bullets.remove(b);
                }
            }
            healthBarGreen.width = (int)(((780* bosses.get(0).health)/500) );     //  780/x = 500/health
            
            if(bosses.get(0).health <= 0){
                score += bosses.get(0).reward;
                explosions.add(new explosion(100, (int) bosses.get(0).xPos, (int) bosses.get(0).yPos));
                afterBoss();
                bossName.setText("");
                bosses.remove(0);
            }

        }

        //particle trails and bullets for enemies
        for (int i = 0; i < enemies.size(); i++) {
            // needed to switch to a regular for loop because the foreach loop 
            // has issues with iterators and removing objects. Instead of using the iterator i just switched directly
            // to a regular for loop.    
            enemy en = enemies.get(i);
            if (en.playerTarget != null) {
                //System.out.println(en.playerTarget == null);
                en.targetPosX = en.playerTarget.xPos;
                en.targetPosY = en.playerTarget.yPos;
                en.checkCollision(bullets); //every enemy should check for a collision with bullets
                en.checkCollision(p);
                p.checkCollision(en.bullets); //check player collision with enemy bullets
                //WHY DOES IT CRASH WHEN THE PLAYER DIES?!?!?!?!?!?!?!?!?!? NVM I FIXED IT. Its been fixed.
            } else {
                en.targetPosX = 640;
                en.targetPosY = 360;
                en.target = false;
            }

            en.move();
            
            if (en.particleCounter <= 50) {
                en.particleCounter += 1;
                en.trail.add(new particleTrail(en));
            } else {
                en.trail.remove(0);
                en.particleCounter -= 1;
            }
            //bullet particles/trails
            for (int f = 0; f < en.bullets.size(); f++) {
                bullet b = en.bullets.get(f);
                if (b.particleCounter <= 5) {
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
                if(b.xPos < -50 || b.xPos > 1330 || b.yPos < -50 || b.yPos > 770){
                    en.bullets.remove(b);
                }
            }
            if(en.xPos < 0){
                en.xPos = 1280;
            }
            if (en.xPos > 1280){
                en.xPos = 0;
            }
            if (en.yPos < 0){
                en.yPos = 720;
            }
            if (en.yPos > 720){
                en.yPos = 0;
            }
            if (en.health <= 0) {
                explosions.add(new explosion(50, (int) en.xPos, (int) en.yPos));
                addScore(en.reward * combo); //add player score
                comboCounter = 3.0; //reset combo counter
                combo +=1;
                comboText.setText("Combo: "+combo);
                enemies.remove(en);
                explosion.play(false);
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
                        ep.lifeTime -= elapsedTime;
                        if (ep.trailBool) {
                            if (ep.particleCounter <= 5) {
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
            if (particleCounter <= 50) {
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
                p.img = new ImageIcon(this.getClass().getResource("images/plane-1.png")).getImage(); //this is bad practice
                s = state.UP;
            } else if ((p.rotation <= 135 && p.rotation >= 45) || (p.rotation >= -315 && p.rotation <= -225)) {
                p.img = new ImageIcon(this.getClass().getResource("images/planeRight.png")).getImage(); //this is bad practice
                s = state.RIGHT;
            } else {
                p.img = new ImageIcon(this.getClass().getResource("images/planeLeft.png")).getImage(); //this is bad practice
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
            
            p.yPos += velocityY * elapsedTime*2; //realistacally should be 0.01 not 0.02, 
            p.xPos += velocityX * elapsedTime*2; //but 0.02 makes the game quicker and the plane more manueverable
            p.bounds = new Rectangle((int)p.xPos, (int)p.yPos, 30,30);
            //boost!
            if(boost) {
                maxVelocity = 300;
                velocityX = 300 * Math.sin(Math.toRadians(p.rotation));
                velocityY = -300 * Math.cos(Math.toRadians(p.rotation));
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
    //}
}
    }, 0, refreshRate);}


	void afterBoss(){
		p.health = p.maxHealth;
		t.cancel();
		this.add(startNextRound);

	}
	void resume(){
		t = new Timer();
		this.remove(startNextRound);
		loop();
	}
    public void makeLosePanel(){
        this.add(youLose);
        this.add(restart);
        highScore.setText("High Score: " + save.getHighScoreSurvival());
        this.add(highScore);
        this.add(restart);
    }


    public void addScore(int addition){
        score += addition;
        scoreText.setText("Score: "+score);
    }


    public void findVelocityY() {
        if (forward) {
            if (Math.abs(velocityY) < maxVelocity) {
                velocityY += accelerationY * elapsedTime;
            } else if (velocityY > 0) {
                velocityY = maxVelocity - 1;
            } else if (velocityY < 0) {
                velocityY = (-maxVelocity) + 1;
            }
        } else {
            if(Math.abs(velocityY) < maxVelocity){
            velocityY += accelerationY * elapsedTime;
            }
            else if(velocityY < 0){
                velocityY = -maxVelocity;
            }
            else if(velocityY >= 0){
                velocityY = maxVelocity;
            }
        }
    }
    public void findVelocityX() {
        if (forward) {
            if (Math.abs(velocityX) < maxVelocity) {
                velocityX += accelerationX * elapsedTime;
            } else if (velocityX > 0) {
                velocityX = maxVelocity - 1;
            } else if (velocityX < 0) {
                velocityX = (-maxVelocity) + 1;
            }
        } else {
        	if(maxVelocity < Math.abs(velocityX)){
        		if(velocityX < 0){
                velocityX = -maxVelocity;
            }
            else if(velocityX >= 0){
                velocityX = maxVelocity;
            }
        	}
			if (velocityX >= 0) {
                velocityX -= mass * elapsedTime;
            } else if (velocityX < 0) {
                velocityX += mass * elapsedTime;
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
        bullet b = new bullet(p, 50);
        bullets.add(b);
        pew.play(false);
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
        float pCounter = (float)trail.size();
        //change transparency
        //draw trail
        Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
            0.1f);

        ArrayList<bullet> combinedBullets = new ArrayList<bullet>();
        ArrayList<particleTrail> combinedParticles = new ArrayList<particleTrail>();
        ArrayList<explosionParticle> combinedEParticles = new ArrayList<explosionParticle>();

        for(int i = 0; i < clouds.size(); i ++){
        	c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1f / (100/clouds.get(i).size)));
             g2d.setComposite(c);
        	g2d.drawImage(clouds.get(i).image, (int)clouds.get(i).xPos, (int)clouds.get(i).yPos, (int)clouds.get(i).size, (int)clouds.get(i).size,j);
        }

        //reset the opacity of everything
        c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g2d.setComposite(c);

        if (p != null) {
            for (int i = 0; i < trail.size(); i++) {
                particleTrail p = trail.get(i);
                //c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1f / pCounter));
                //g2d.setComposite(c);
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
            for (int i = 0; i < bullets.size(); i++) {
                bullet b = bullets.get(i);
                g2d.setTransform(old);
                pCounter = (float)b.trail.size();
                combinedParticles.addAll(b.trail);
                g2d.drawImage(b.img, (int) b.xPos, (int) b.yPos, 5, 5, j);
            }
        }
        //draw enemies
        for (int i = 0; i < enemies.size(); i++) {
            enemy e = enemies.get(i);
            //System.out.println("enemies are being drawn");
            g2d.translate(e.xPos, e.yPos); // Translate the center of our coordinates.
            g2d.rotate(Math.toRadians(e.rotation), 15, 15);
            g2d.drawImage(e.img, 0, 0, e.width, e.height, j);
            pCounter = (float)e.trail.size();
            g2d.setTransform(old);
            combinedParticles.addAll(e.trail);
            combinedBullets.addAll(e.bullets);
        }
        for (int o = 0; o < explosions.size(); o++) {
            explosion ex = explosions.get(o);
            combinedEParticles.addAll(ex.particles);         
        }
        for (int z = 0; z < combinedEParticles.size(); z++) {
                explosionParticle ep = combinedEParticles.get(z);
                g2d.drawImage(ep.img, (int) ep.posX, (int) ep.posY, 5, 5, j);
                combinedParticles.addAll(ep.trail);
        }
        for (int n = 0; n < combinedBullets.size(); n++) {
                bullet b = combinedBullets.get(n);
                pCounter = (float)b.trail.size();
                combinedParticles.addAll(b.trail);
                g2d.drawImage(b.img, (int) b.xPos, (int) b.yPos, 5, 5, j);
            }
        
        g2d.setTransform(old);
        if(bosses.size() > 0){
                g2d.drawImage(bosses.get(0).img, (int)bosses.get(0).xPos, (int) bosses.get(0).yPos, bosses.get(0).size, bosses.get(0).size, j);
                for(int i = 0; i < bosses.get(0).bullets.size(); i++){
                    bullet b = bosses.get(0).bullets.get(i);
                    g2d.drawImage(b.img, (int) b.xPos, (int) b.yPos, 5, 5, j);
                    combinedParticles.addAll(b.trail);
                }
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect((int)healthBarBack.getX(), (int)healthBarBack.getY(), (int)healthBarBack.getWidth(), (int)healthBarBack.getHeight());
                    g2d.setColor(Color.RED);
                    g2d.fillRect((int)healthBarRed.getX(), (int)healthBarRed.getY(), (int)healthBarRed.getWidth(), (int)healthBarRed.getHeight());
                    g2d.setColor(Color.GREEN);
                    g2d.fillRect((int)healthBarGreen.getX(), (int)healthBarGreen.getY(), (int)healthBarGreen.getWidth(), (int)healthBarGreen.getHeight());

            }
        for (int n = 0; n < combinedParticles.size(); n++) {
                particleTrail part = combinedParticles.get(n);
                //c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1f / pCounter));
                //g2d.setComposite(c);
                pCounter -= 1;
                g2d.drawImage(part.img, (int) part.xPos, (int) part.yPos, part.size, part.size, j);
            }
        
    }

    void calculateFPS(long x){
        System.out.println(1000/(System.currentTimeMillis()-x)+"FPS");
    }

}
