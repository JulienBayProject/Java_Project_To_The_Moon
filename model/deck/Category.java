package model.deck;

import javafx.scene.paint.Color;

public enum Category {
	

	IDEAS(Color.DARKMAGENTA),
	SCIENCE(Color.ORANGE),
	PLANET(Color.FORESTGREEN),
	HISTORY(Color.PINK),
	LITERATURE(Color.BLUE),
	COMPUTER(Color.MAROON);
	
	private Color c;
	
	Category(Color c) {
		this.c = c;
	}
	
	public Color getColor() {
		return this.c;
	}
	
	
	
}
