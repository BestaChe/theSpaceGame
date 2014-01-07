package engine.main.gui;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;

import engine.main.entities.Player;

public class Info extends GUI {
	
	private String text;
	private TrueTypeFont font;
	
	public Info(Image img, int x, int y) {
		super(img, x, y);
		
		this.text = "";
		this.font = new TrueTypeFont( new Font("Tahoma", Font.ITALIC, 13 ), true );
		
	}
	
	public void update( GameContainer window, int dt, Player player ) {
		
		this.text = player.currentObject();
		
	}
	
	public void render( GameContainer window, Graphics g ) {
		
		//System.out.println(this.x(window) + " " + this.y(window));
		g.setFont(font);
		g.drawString(this.text, this.x(window)+24, this.y(window)+16);
		
	}

}
