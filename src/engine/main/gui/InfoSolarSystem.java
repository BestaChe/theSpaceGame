package engine.main.gui;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;

import engine.main.entities.Player;

public class InfoSolarSystem extends GUI {
	
	private String text;
	private TrueTypeFont font;
	
	public InfoSolarSystem(Image img, int x, int y) {
		super(img, x, y);
		
		this.text = "";
		this.font = new TrueTypeFont( new Font("Tahoma", Font.BOLD, 16 ), true );
		
	}
	
	public void update( GameContainer window, int dt, Player player ) {
		
		this.text = player.currentSystem();
		
	}
	
	public void render( GameContainer window, Graphics g ) {
		
		//System.out.println(this.x(window) + " " + this.y(window));
		g.setFont(font);
		g.drawString(this.text, this.x(window)+24, this.y(window)+16);
		
	}

}
