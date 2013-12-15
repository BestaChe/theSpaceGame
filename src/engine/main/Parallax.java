package engine.main;

import org.newdawn.slick.GameContainer;

public class Parallax extends Camera {
	
	private float parallaxConstant;

	public Parallax(GameContainer gc, int layer) {
		super(gc);
		this.parallaxConstant = 1 /(layer + 1);
	}
	
	public void translateParallax() {
		
		 gc.getGraphics().translate(-(cameraX)*parallaxConstant, -(cameraY)*parallaxConstant);
	}
	
	public void untranslateParallax() {
		
		 gc.getGraphics().translate((cameraX)*parallaxConstant, (cameraY)*parallaxConstant);
	}
	
	

}
