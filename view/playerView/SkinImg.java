package view.playerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.scene.image.Image;

public class SkinImg {
	
	private List<Image> moves;
	
	/**
	 * Classe reprenant les images d'un skins
	 */
	public SkinImg() {
		
		moves = new ArrayList<Image>();
	}
	
	public SkinImg(Image i1, Image i2) {
		
		moves = new ArrayList<Image>();
		moves.add(i1);
		moves.add(i2);
	}
	
	/**
	 * renvoit l'element de Classe Image present a l'index
	 * @param index int
	 * @return Image
	 */
	public Image getImg(int index) {
		return moves.get(index);
	}
	
	/**
	 * Renvoit le nombre d'occurence dans la liste
	 * @return int
	 */
	public int getSize() {
		return moves.size();
	}
	
	/**
	 * ajoute l'image recue en arguement dans la liste
	 * @param i
	 */
	public void addSkinImg(Image i) {
		moves.add(i);
	}
}
