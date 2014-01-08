package engine.main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;

public class Camera {
   
   /** the GameContainer, used for getting the size of the GameCanvas */
   protected GameContainer gc;
   protected int gcWidth;
   protected int gcHeight;

   /** the x-position of our "camera" in pixel */
   protected float cameraX;
   
   /** the y-position of our "camera" in pixel */
   protected float cameraY;
   
   /** the parallax constant of the camera */
   protected double parallaxConstant;
   
   /** ZOOM */
   protected float zoom;
   
   /** object pos */
   protected float objectX;
   protected float objectY;
   
   /**
    * Create a new camera
    * 
    * @param gc the GameContainer, used for getting the size of the GameCanvas
    * @param map the TiledMap used for the current scene
    */
   public Camera(GameContainer gc, int layer, float zoom ) {
      
      this.gc = gc;
      this.zoom = zoom;
      this.gcWidth = (int)(gc.getWidth());
      this.gcHeight = (int)(gc.getHeight());
      this.parallaxConstant = (1 /((layer + 1)*1.0)) * this.zoom;
   }
   
   /**
    * "locks" the camera on the given coordinates. The camera tries to keep the location in it's center.
    * 
    * @param x the real x-coordinate (in pixel) which should be centered on the screen
    * @param y the real y-coordinate (in pixel) which should be centered on the screen
    */
   public void centerOn(float x, float y) {
      //try to set the given position as center of the camera by default
	   
	   float distX = (float)((((this.gcWidth) * 0.5f ) - x)*parallaxConstant);
	   float distY = (float)((((this.gcHeight) * 0.5f ) - y)*parallaxConstant);
	   
	   this.objectX = x;
	   this.objectY = y;
	  
	   cameraX = distX;
	   cameraY = distY;
   }
   
   /**
    * "locks" the camera on the center of the given Rectangle. The camera tries to keep the location in it's center.
    * 
    * @param x the x-coordinate (in pixel) of the top-left corner of the rectangle
    * @param y the y-coordinate (in pixel) of the top-left corner of the rectangle
    * @param height the height (in pixel) of the rectangle
    * @param width the width (in pixel) of the rectangle
    */
   public void centerOn(float x, float y, float height, float width) {
      this.centerOn(x + width / 2, y + height / 2);
   }

   /**
    * "locks the camera on the center of the given Shape. The camera tries to keep the location in it's center.
    * @param shape the Shape which should be centered on the screen
    */
   public void centerOn(Shape shape) {
      this.centerOn(shape.getCenterX(), shape.getCenterY());
   }
   
   /**
    * Translates the Graphics-context to the coordinates of the map - now everything
    * can be drawn with it's NATURAL coordinates.
    */
   public void translateGraphics() {
	   
	   scaleGraphics( this.zoom );
	   gc.getGraphics().translate(cameraX, cameraY);
   }
   /**
    * Reverses the Graphics-translation of Camera.translatesGraphics().
    * Call this before drawing HUD-elements or the like
    */
   public void untranslateGraphics() {
	   
	   scaleGraphics( 1.0f );
	   gc.getGraphics().translate(-cameraX, -cameraY);
	
   }
   
   /**
    * 
    * @param zoom
    */
   public void setZoom( float zoom ) {
	   this.zoom = zoom;
	   System.out.println(zoom);
   }
   
   /**
    * Returns the camera position X
    * @return float cameraX
    */
   public float camX() {
	   return -cameraX;
   }
   
   /**
    * Returns the camera position Y
    * @return float cameraY
    */
   public float camY() {
	   return -cameraY;
   }
   
   /**
    * 
    * @param zoom
    */
   public void scaleGraphics( float zoom ) {
	   
	   gc.getGraphics().translate(((( this.gcWidth ) / 2.0f) ) , ((( this.gcHeight ) / 2.0f) ));
	   gc.getGraphics().scale((float) zoom, (float) zoom);
	   gc.getGraphics().translate(-(((( this.gcWidth ) / 2.0f) )), -(((( this.gcHeight ) / 2.0f) )));
   }
   
}