package engine.main.gui;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import engine.main.entities.Planet;
import engine.main.entities.Player;
import engine.main.entities.Star;
import engine.main.world.World;

public class InfoWindow extends GUI {
	
	private Planet planet;
	private Star star;
	
	private TrueTypeFont labelFont;
	private TrueTypeFont font;

	private Image image;
	private Image image2;
	private Image image3;
	
	private Color color;
	
	public InfoWindow(Image img, int x, int y ) throws SlickException {
		super(img, x, y);
		
		this.labelFont = new TrueTypeFont( new Font("Tahoma", Font.BOLD, 16 ), true );
		this.font = new TrueTypeFont( new Font("Tahoma", Font.ITALIC, 14 ), true );
		
	}
	
	public void setPlanet( Planet planet ) throws SlickException {
			this.planet = planet;
			this.star = null;

			this.image = this.planet.image().getScaledCopy((float) 0.5);
			this.color = this.planet.color();

			if ( this.planet.hasDetail() && this.planet.isTerrestrian() )
				this.image2 = new Image( this.planet.detail() ).getScaledCopy((float)0.5);
			else
				this.image2 = null;

			if ( this.planet.hasAtmosphere() )
				this.image3 = new Image("gfx/astros/terr_planet_atmosphere_1.png").getScaledCopy((float)0.5);
			else
				this.image3 = null;
	}
	
	public void setStar( Star star ) {
		
		this.star = star;
		this.planet = null;
		
		this.image = this.star.image();
		this.color = this.star.color();
		
		this.image2 = null;
		this.image3 = null;
		
	}
	
	public void render( GameContainer window, Graphics g ) {
		
		g.drawImage(this.image, this.x(window) + 160, this.y(window) + 20, this.color);

		if ( this.image2 != null )
			g.drawImage(this.image2, this.x(window) + 160, this.y(window) + 20 );

		if ( this.image3 != null )
			g.drawImage(this.image3, this.x(window) + 160, this.y(window) + 20 );

		if ( this.planet != null ) {
			
			g.setFont(labelFont);
			g.drawString("    - Planet -", this.x(window) + 10	, this.y(window) + 20  );
			g.drawString("Type:"		 , this.x(window) + 10	, this.y(window) + 40  );
			g.drawString("Mass:"		 , this.x(window) + 10	, this.y(window) + 60  );
			g.drawString("Temperature:"  , this.x(window) + 10	, this.y(window) + 80 );
			g.drawString("Atmosphere:"	 , this.x(window) + 10	, this.y(window) + 100 );
			g.drawString("Water:"		 , this.x(window) + 10	, this.y(window) + 120 );
			g.drawString("Habitable:"	 , this.x(window) + 10	, this.y(window) + 140 );
			g.drawString("Resources:"	 , this.x(window) + 10	, this.y(window) + 160 );
			g.drawString("Name:"		 , this.x(window) + 10	, this.y(window) + 180  );
			
			g.setFont(font);
			
			String name = this.planet.name();
			String type = ( this.planet.isTerrestrian() ? "Rock" : "Gas");
			String mass = (this.planet.mass()/2.0) + " Earth's";
			String temperature = this.planet.temperature() + "K";
			String atmos = ( this.planet.hasAtmosphere() ? "Dense" : "Non existant" );
			String water = ( this.planet.rawDetail() == 1 ? "Oceans" : "Non existant" );
			String habitable = ( this.planet.supportsLife() ? "Yes" : "No" );
			String resources = "test";
			
			switch( this.planet.materials() ) {
				case 1:
					resources = "Iron";
					break;
				case 2:
					resources = "Ditrium";
					break;
				case 3:
					resources = "Tropium";
					break;
				case 4:
					resources = "Uranium";
					break;
				case 0:
					resources = "None";
					break;
			}
				
			g.drawString( type , this.x(window) + 65			, this.y(window) + 42  );
			g.drawString( mass , this.x(window) + 65			, this.y(window) + 62  );
			g.drawString( temperature , this.x(window) + 130	, this.y(window) + 82 );
			g.drawString( atmos , this.x(window) + 125			, this.y(window) + 102 );
			g.drawString( water , this.x(window) + 70			, this.y(window) + 122 );
			g.drawString( habitable , this.x(window) + 100		, this.y(window) + 142 );
			g.drawString( resources , this.x(window) + 100		, this.y(window) + 162 );
			g.drawString( name , this.x(window) + 65			, this.y(window) + 182  );
			
		}
		else {
			g.setFont(labelFont);
			g.drawString("    - Star -"		, this.x(window) + 10	, this.y(window) + 20  );
			g.drawString("Mass:"			, this.x(window) + 10	, this.y(window) + 40  );
			g.drawString("Child Planets:" 	, this.x(window) + 10	, this.y(window) + 60  );
			g.drawString("Temperature:"	    , this.x(window) + 10	, this.y(window) + 80 );
			g.drawString("Name:"			, this.x(window) + 10	, this.y(window) + 100  );
			
			g.setFont(font);
			
			String name = this.star.name();
			String mass = this.star.mass() + " Sun's";
			String temperature = this.star.temperature() + "K";
			String childs = this.star.getChildPlanets().size() + "";
			
			g.drawString( mass , this.x(window) + 65			, this.y(window) + 42  );
			g.drawString( childs , this.x(window) + 130			, this.y(window) + 62  );
			g.drawString( temperature , this.x(window) + 130	, this.y(window) + 82 );
			g.drawString( name , this.x(window) + 65			, this.y(window) + 102 );
		}
	}
	
}
