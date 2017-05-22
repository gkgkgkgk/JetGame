import javax.swing.*;
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


public class LeaderBoard {

	ArrayList<multiplayer> players = new ArrayList<multiplayer>();
	Font font, fontBig, fontBiggest;

	int amount;

	int[] points;

	public LeaderBoard(ArrayList<multiplayer> mp, int[] p){
		points = new int[p.length];
		for(int i = 0; i < p.length; i++){
			points[i] = p[i];	
		}
		getFonts();
		setArrays(mp);
		amount = players.size();
		System.out.println(amount);
	}	
	
	public void setPoints(int[] p){
		for(int i = 0; i < p.length; i++){
			points[i] = p[i];	
		}
	}
	
	
	public void setArrays(ArrayList<multiplayer> x){
		for(multiplayer a : x){
			players.add(new multiplayer(a));	
		}
		
	}
	
	public void Draw(Graphics g){
		g.fillRect(0, 0, 200,720);
		for(int i = 0; i < amount; i++){
			g.setColor(players.get(i).c);
			g.fillOval(25, (i * (700/(amount+1))), 60,60);	
			System.out.println("wat");	
		}
		for(int i = 0; i < amount; i++){
			g.setColor(players.get(i).c);
			drawCenteredString(g, ""+points[i], new Rectangle(70, (i * (700/(amount+1))), 100, 60), font);
			System.out.println("wat");	
		}
	}
	//this thing is a lifesaver
	public void drawCenteredString(Graphics g, String text, Rectangle rect, Font f) {
    // Get the FontMetrics
    FontMetrics metrics = g.getFontMetrics(f);
    // Determine the X coordinate for the text
    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
    // Set the font
    g.setFont(font);
    // Draw the String
    g.drawString(text, x, y);
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

	
}