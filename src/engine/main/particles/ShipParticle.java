package engine.main.particles;

import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;

public class ShipParticle {
	
	private ParticleSystem particle;
	private ParticleEmitter particleEmitter;
	private Particle particlesprite;
	
	public ShipParticle( String path ) {
		this.particle = new ParticleSystem(path);
		this.particlesprite = new Particle( particle );
		this.particle.addEmitter( particleEmitter );
		
		this.particlesprite.init(particleEmitter, (float)3.5 );
		
		
	}
	
	public void update( int dt ) {
		
		
		this.particle.update( dt );
		this.particlesprite.update( dt );
		this.particleEmitter.update( this.particle, dt );
		
	}
	
	public void render() {
		
		
	}
	
	public void enableShipParticles( boolean enabled ) {
		
		this.particleEmitter.setEnabled(enabled);
		
	}
	
}
