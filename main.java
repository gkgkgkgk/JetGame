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
import java.awt.FontMetrics;
import java.awt.Font;
import java.io.*;
import java.io.InputStream;

public class main extends JPanel implements ActionListener {

    JFrame w = new JFrame();
    JButton start, startMulti;
    Font font;
    Font fontBig;
    int time = 0;
    Timer t;
    JLabel title;
    enemy en = new enemy(900, 400);
    public main() {
        t = new Timer(20, this);
        t.start();
        setLayout(null);
        getFonts();
        w.setContentPane(this);
        w.setSize(800, 600);
        w.setVisible(true);
        setBackground(new Color(35, 106, 135)); //todo: add moving clouds
        start = new JButton("Start");
        start.setBounds(300, 275, 200, 50); // x y size
        start.setFont(font);
        start.setBackground(Color.BLACK);
        start.setOpaque(false);
        start.setForeground(Color.RED);
        start.setFocusPainted(false);
        start.setBorderPainted(false);
        start.addActionListener(this);
        startMulti = new JButton("Start MultiPlayer");
        startMulti.setBounds(300, 375, 200, 50); // x y size
        startMulti.setFont(font);
        startMulti.setBackground(Color.BLACK);
        startMulti.setOpaque(false);
        startMulti.setForeground(Color.RED);
        startMulti.setFocusPainted(false);
        startMulti.setBorderPainted(false);
        startMulti.addActionListener(this);
        title = new JLabel();
        title.setBounds(130, 0, 1000, 100);
        title.setText("Hijack High Jinks");
        title.setFont(fontBig);
        title.setForeground(Color.BLACK);
        en.targetPosX = 400;
        en.targetPosY = 300;
        en.target = false;
        add(start);
        add(startMulti);
        add(title);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void getFonts() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/mainFont.ttf"))).deriveFont(Font.PLAIN, 24);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
         try {
            fontBig = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/mainFont.ttf"))).deriveFont(Font.PLAIN, 72);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void restart(Survival s){
        s = null;
        new Survival(this);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            new Survival(this);
            System.out.println("New Survival");
            w.setVisible(false); //what...
            t.stop();
        }
        if (e.getSource() == startMulti) {
            new VSMode(this);
            System.out.println("New Multi");
            w.setVisible(false); //what...
            t.stop();
        }

        
        en.move();
        if (en.particleCounter <= 100) {
            en.particleCounter += 1;
            en.trail.add(new particleTrail(en));
        } else {
            en.trail.remove(0);
            en.particleCounter -= 1;
        }
        time++;
        repaint();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; // Create a Java2D version of g.
        //store old rotation
        AffineTransform old = g2d.getTransform();
        Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
            .1f);
        float pCounter = en.trail.size();

        g2d.translate(en.xPos, en.yPos); // Translate the center of our coordinates.
        g2d.rotate(Math.toRadians(en.rotation), 15, 15);
        g2d.drawImage(en.img, 0, 0, en.width, en.height, this);
        pCounter = en.trail.size();
        g2d.setTransform(old);
        for (particleTrail part: en.trail) {
            c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1f / pCounter));
            g2d.setComposite(c);
            pCounter -= 1;
            g2d.drawImage(part.img, (int) part.xPos, (int) part.yPos, part.size, part.size, this);
        }

    }



    public static void main(String[] args) {
        new main();
    }

}