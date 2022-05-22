package view.playerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javafx.scene.image.Image;

public class ListSkinImg {

	private List<SkinImg> skins;
	
	/**
	 * Creation d'une classe qui charge la liste de skins
	 */
	public ListSkinImg() {
		
		skins = new ArrayList<SkinImg>();
		
		skins.add(new SkinImg(new Image("./images/PNJ/BlueL.png"), new Image("./images/PNJ/BlueR.png")));
		skins.add(new SkinImg(new Image("./images/PNJ/GreenL.png"), new Image("./images/PNJ/GreenR.png")));
		skins.add(new SkinImg(new Image("./images/PNJ/PinkL.png"), new Image("./images/PNJ/PinkR.png")));
		skins.add(new SkinImg(new Image("./images/PNJ/RedL.png"), new Image("./images/PNJ/RedR.png")));
		skins.add(new SkinImg(new Image("./images/PNJ/YellowL.png"), new Image("./images/PNJ/YellowR.png")));
		skins.add(new SkinImg(new Image("./images/PNJ/OrangeL.png"), new Image("./images/PNJ/OrangeR.png")));
	}
	
	/**
	 * renvoit un element aleatoire de la classe SkinImg present dans la liste de skins
	 * @return SkinImg
	 */
	public SkinImg getSkinRandom() {
		return skins.get(new Random().nextInt(skins.size()));
	}
	
	/**
	 * renvoit l element de classe SkinImg present a l index, recu en argument, dans la liste de skins
	 * @param index
	 * @return SkinImg
	 */
	public SkinImg getSkinIndex(int index) {
		return skins.get(index);
	}
	
	/**
	 * Melange la liste de skins
	 */
	public void shuffleList() {
		Collections.shuffle(skins);
	}
}
