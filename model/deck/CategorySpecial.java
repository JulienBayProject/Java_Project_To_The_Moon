
package model.deck;

import javafx.scene.paint.Color;

public enum CategorySpecial {

	DEFI(Color.BLACK),
	NEXT(Color.RED);
	
	private Color c;
	
	CategorySpecial(Color c) {
		this.c = c;
	}
	
	public Color getColor() {
		return this.c;
	}
}
