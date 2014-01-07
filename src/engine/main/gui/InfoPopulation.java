package engine.main.gui;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import engine.main.entities.Player;

public class InfoPopulation extends GUI {
	
	private String text;
	private TrueTypeFont font;
	private Image icon;
	
	public InfoPopulation(Image img, int x, int y) throws SlickException {
		super(img, x, y);
		
		this.text = "";
		this.font = new TrueTypeFont( new Font("Tahoma", Font.ITALIC, 13 ), true );
		this.icon = new Image("gfx/gui/gui_population.png");
		
	}
	
	public void update( GameContainer window, int dt, Player player ) {
		
		this.text = player.getPopulation() + "/" + player.getMaxPopulation();
		
	}
	
	public void render( GameContainer window, Graphics g ) {
		
		//System.out.println(this.x(window) + " " + this.y(window));
		g.drawImage(this.icon, this.x(window)+16, this.y(window)+16 );
		g.setFont(font);
		g.drawString(this.text, this.x(window)+50, this.y(window)+16);
		
	}

}
