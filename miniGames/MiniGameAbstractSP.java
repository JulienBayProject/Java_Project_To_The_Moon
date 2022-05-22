package miniGames;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import view.toolsView.WindowData;

public abstract class MiniGameAbstractSP extends StackPane implements WindowData{
	
	private Button finishBtn;
	private int po;
	private int chrono = 30;
	
	/**
	 * renvoit la valeur de l'attribut chrono
	 * @return int
	 */
	public int getChrono() {
		return chrono;
	}
	
	/**
	 * donne la valeur recu en argument a l'attribut chrono
	 * @param chrono int
	 */
	public void setChrono(int chrono) {
		this.chrono = chrono;
	}
	
	/**
	 * renvoit la valeur de l'attribut po
	 * @return
	 */
	public int getPo() {
		return po;
	}

	/**
	 * donne la valeur recu en argument a l'attribut po 
	 * @param po int
	 */
	public void setPo(int po) {
		this.po = po;
	}

	/**
	 * renvoit et si besoin cree un objet de classe Button destiner a passer du mini a l'ecran de jeu
	 * @return
	 */
	public Button getFinishBtn() {
		if ( finishBtn == null) {
			finishBtn = new Button("End");
		}
		return finishBtn;
	}
	
	/**
	 * method a redefinir commune a tout les minis jeux permettant je lancement du jeu
	 */
	public abstract void launchGame();
}
