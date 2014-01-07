package engine.main.entities.buildings;

import engine.main.entities.NPC;
import engine.main.entities.Player;

public class Building {

	private Player owner;
	private NPC NPCowner;
	private float x;
	private float y;
	private int health;
	private int level;
	
	/**
	 * Player building
	 * @param x - float x
	 * @param y - float y
	 * @param health - int health
	 * @param level - int level
	 * @param owner - Player owner
	 */
	public Building( Player owner, float x, float y, int health, int level ) {
		
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.health = health;
		this.level = level;
		
		this.NPCowner = null;
		
	}
	
	/**
	 * NPC building
	 * @param x - float x
	 * @param y - float y
	 * @param health - int health
	 * @param level - int level
	 * @param owner - NPC owner
	 */
	public Building( NPC owner, float x, float y, int health, int level ) {
		
		this.NPCowner = owner;
		this.x = x;
		this.y = y;
		this.health = health;
		this.level = level;
		
		this.owner = null;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public float x() {
		return this.x;
	}
	
	/**
	 * 
	 * @return
	 */
	public float y() {
		return this.y;
	}
	
	/**
	 * 
	 * @return
	 */
	public int health() {
		return this.health;
	}
	
	/**
	 * 
	 * @return
	 */
	public int level() {
		return this.level;
	}
	
	/**
	 * 
	 * @return
	 */
	public NPC ownerNPC() {
		return this.NPCowner;
	}
	
	/**
	 * 
	 * @return
	 */
	public Player owner() {
		return this.owner;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition( float x, float y ) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void harm( int amount ) {
		this.health -= amount;
	}
	
	/**
	 * 
	 * @param level
	 */
	public void setLevel( int level ) {
		this.level = level;
	}
	
}
