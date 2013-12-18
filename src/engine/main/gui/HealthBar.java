package engine.main.gui;

import org.newdawn.slick.Image;

import engine.main.entities.Player;

public class HealthBar extends GUI {

	private Player player;
	
	public HealthBar(Image img, int x, int y, Player player) {
		super(img, x, y);
		this.player = player;
	}

}
