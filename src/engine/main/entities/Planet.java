package engine.main.entities;

public class Planet extends AstronomicalObject {

	private String name;
	private int temperature;
	private boolean terrestrian;
	
	/**
	 * Constructs the planet
	 * @param name - string name of the planet
	 * @param x - int x position of the planet
	 * @param y - int y position of the planet
	 * @param scale - int scale
	 * @param terrestrian - boolean is the planet not gas typed
	 * @param temperature - int temperature of the planet
	 */
	public Planet(String name, int x, int y, int scale, boolean terrestrian, int temperature) {
		super(x, y, scale);
		
		this.name = name;
		this.terrestrian = terrestrian;
		this.temperature = temperature;
		
	}
	
	/**
	 * Returns the name of the planet
	 * @return string name
	 */
	public String name() {
		return this.name;
	}
	
	/**
	 * Returns the temperature of the planet in kelvin
	 * @return int temperature
	 */
	public int temperature() {
		return this.temperature;
	}
	
	/**
	 * Returns whether the planet is terrestrian type or not (aka gas typed)
	 * @return boolean
	 */
	public boolean isTerrestrian() {
		return this.terrestrian;
	}
	
	/**
	 * Returns the mass of the planet ( 1 scale = 1 earth mass )
	 * @return int mass
	 */
	public int mass() {
		return this.scale();
	}
	

}
