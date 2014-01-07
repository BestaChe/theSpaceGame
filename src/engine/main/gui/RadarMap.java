package engine.main.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

import engine.main.Camera;
import engine.main.Util;
import engine.main.entities.Planet;
import engine.main.entities.Player;
import engine.main.entities.Star;
import engine.main.world.World;

public class RadarMap extends GUI {

	private ArrayList<Circle> allAstros;
	private int guiX;
	private int guiY;
	
	private float centerX;
	private float centerY;
	
	private Player player;
	
	public RadarMap(Image img, int x, int y, Player player, GameContainer window) {
		super(img, x, y);
		
		this.guiX = (int)(( x * window.getWidth() ) / 800.0);
		this.guiY = (int)(( y * window.getHeight() ) / 600.0);;
		this.player = player;
		
		this.allAstros = new ArrayList<Circle>();
	}
	
	public void update( GameContainer window, int dt, World world, Camera camera ) {
		
		for( Planet p : world.returnPlanets() ) {
			
			if ( Util.dist(p.x(), p.y(), this.player.x(), this.player.y() ) < 10000 ) {
				
				float cameraX = ( ( camera.camX() * 256 ) / 10000 ) + this.guiX;
				float cameraY = ( ( camera.camY() * 256 ) / 10000 ) + this.guiY+256;;
				float radarX = ( ( (p.x()-cameraX) * 256 ) / 10000 ) + this.guiX;
				float radarY = ( ( (p.y()-cameraY) * 256 ) / 10000 ) + this.guiY+256;;
				float scale = 3; 
				
				Circle planetDot = new Circle( radarX-(scale/2.0f), radarY-(scale/2.0f), scale );
				planetDot.setCenterX(radarX);
				planetDot.setCenterY(radarY);
				
				if ( !this.allAstros.contains(planetDot) )
					this.allAstros.add(planetDot);
			}
			
		}
		
		for( Star s : world.returnStars() ) {
			
			if ( Util.dist(s.x(), s.y(), this.player.x(), this.player.y() ) < 800 ) {
				
				float cameraX = ( ( camera.camX() * 256 ) / 10000 ) + this.guiX;
				float cameraY = ( ( camera.camY() * 256 ) / 10000 ) + this.guiY+(256/2.0f);
				float radarX = ( ( (s.x()-cameraX) * 256 ) / 10000 ) + this.guiX;
				float radarY = ( ( (s.y()-cameraY) * 256 ) / 10000 ) + this.guiY+(256/2.0f);
				float scale = 7; 
				
				Circle starDot = new Circle( (radarX-(scale/2.0f)), (radarY-(scale/2.0f)), scale );
				starDot.setCenterX(radarX);
				starDot.setCenterY(radarY);
				
				if ( !this.allAstros.contains(starDot) )
					this.allAstros.add(starDot);
			}
			
		}
		
	}
	
	public void render( GameContainer window, Graphics g, Camera camera ) {
		
		g.setColor(new Color(255,0,0,255) );
		for( Circle c : this.allAstros ) {
			g.fill(c);
		}
		
		float radarX = ( ( (player.x()-camera.camX()) * 256 ) / 10000 ) + (this.guiX);
		float radarY = ( ( (player.y()-camera.camY()) * 256 ) / 10000 ) + (this.guiY+128+128);
		
		g.setColor(new Color(0,255,0,255) );
		g.draw(new Circle( radarX-2, radarY-2, 4 ));
		g.setColor(new Color(255,255,255,255) );
		
	}

}
