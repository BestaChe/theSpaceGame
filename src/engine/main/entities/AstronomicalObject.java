package engine.main.entities;
import org.newdawn.slick.geom.Circle;

public class AstronomicalObject {

	private int x;
	private int y;
	private int scale;
	private Circle shape;
	
	/**
	 * Constructs the class of the Astronomical Object
	 * @param x - x position of the object
	 * @param y - y position of the object
	 * @param scale - the scale of the object
	 */
	public AstronomicalObject( int x, int y, int scale ) {
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.shape = new Circle( x, y, scale*32 );
	}
	
	/**
	 * Returns the x position
	 * @return int x
	 */
	public int x() {
		return this.x;
	}
	
	/**
	 * Returns the y position
	 * @return int y
	 */
	public int y() {
		return this.y;
	}
	
	/**
	 * Returns the scale
	 * @return int scale
	 */
	public int scale() {
		return this.scale;
	}
	
	/**
	 * Returns the shape body
	 * @return circle shape
	 */
	public Circle shape() {
		return this.shape;
	}
	
}
