package engine.main.entities.weapons;

import org.newdawn.slick.SlickException;

import engine.main.entities.NPC;
import engine.main.entities.Player;

public class Damage {
	
	/**
	 * 
	 * @param weapon
	 * @param npc
	 * @param player
	 * @return
	 * @throws SlickException 
	 */
	public static void BulletCollisionWithNPC( Weapon weapon, NPC npc ) throws SlickException {
		
		if ( weapon != null && npc != null ) {
			for( int i = 0; i <= weapon.bullets().size() - 1; i++ ) {
				Bullet b = weapon.bullets().get(i);
				
				if ( b.alive() ) {
					if ( npc.shape().intersects(b.shape()) ) {
						System.out.println("Touching: " + npc.name() );
						
						npc.harm(b.damage());
						npc.setRelationship(npc.relationship()-10);
						
						System.out.println(npc.name() + " hp: " + npc.health());
						weapon.removeBullet(b);
						
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param weapon
	 * @param npc
	 * @param player
	 * @throws SlickException 
	 */
	public static void BulletCollisionWithPlayer( Weapon weapon, NPC npc, Player player ) throws SlickException {
		
		if ( weapon != null && npc != null ) {
			for( int i = 0; i <= weapon.bullets().size() - 1; i++ ) {
				Bullet b = weapon.bullets().get(i);
				
				if ( player.shape().intersects(b.shape())) {
					weapon.removeBullet(b);
				}

					
			}
		}
		
	}
	
	
	
}
