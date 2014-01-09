package engine.main.entities;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

public class AstronomicalObject {
	
	public static final int ASTRO_IMAGE_SIZE = 128;
	
	private float x;
	private float y;
	private float scale;
	
	private Image image;
	private Circle shape;
	
	/**
	 * Constructs the class of the Astronomical Object
	 * @param x - x position of the object
	 * @param y - y position of the object
	 * @param scale - the scale of the object
	 */
	public AstronomicalObject( float x, float y, float scale, Image image ) {
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.shape = new Circle( this.x, this.y, (int)((this.scale*2*0.95f)*( AstronomicalObject.ASTRO_IMAGE_SIZE /4.0) ) );
		this.image = image;
	}
	
	/**
	 * Returns the x position
	 * @return int x
	 */
	public float x() {
		return this.x;
	}
	
	/**
	 * Returns the y position
	 * @return int y
	 */
	public float y() {
		return this.y;
	}
	
	/**
	 * Returns the scale
	 * @return int scale
	 */
	public float scale() {
		return this.scale;
	}
	
	/**
	 * Returns the shape body
	 * @return circle shape
	 */
	public Circle shape() {
		return this.shape;
	}
	
	/**
	 * Returns the image
	 * @return Image image
	 */
	public Image image() {
		return this.image;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition( float x, float y ) {
		this.x = x;
		this.y = y;
		this.shape().setCenterX(this.x);
		this.shape().setCenterY(this.y);
	}
	
}
