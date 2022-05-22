package view.duringGame;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.bonus.Spell;
import model.player.Player;
import view.toolsView.WindowData;

public class InventoryBP extends BorderPane implements WindowData{

	private Player player;

	private Button btnFiftyFifty;
	private Button btnChooseCat;

	private Label lblFiftyFifty;
	private Label lblChooseCat;

	private int choice;
	private int fifty;

	private Label lblPlayer;

	/**
	 * Creation d'un inventaire
	 * @param p
	 */
	
	public InventoryBP(Player p) {
		this.player = p;
		HBox hb2 = new HBox(PADDING,getBtnFiftyFifty(), getLblFiftyFifty());
		HBox hb3 = new HBox(PADDING,getBtnChooseCat(), getLblChooseCat());

		hb2.setAlignment(Pos.CENTER);
		hb3.setAlignment(Pos.CENTER);

		VBox vb = new VBox( hb2, hb3);
		vb.setSpacing(PADDING);
		this.setCenter(vb);
		vb.setAlignment(Pos.CENTER);
		this.setLeft(getLblPlayer());
		this.getStyleClass().add("alert_css");
	}

	/**
	 * Méthode permettant de reset les labels des players dès qu'elle est appelé 
	 */
	public void refresh() {
		getLblPlayer().setText(player.getNickName() + "      " + player.getTransaction() + " po");
	}

	/**
	 * Méthode permettant de voir si le joueur possède bien le sort choose_cat de l'enumaration Spell
	 * @return
	 */
	public boolean chooseCatVerif() {
		if(player.getInventory().contains(Spell.CHOOSE_CAT.getS())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Méthode permettant de réactualiser les différents labels en regardant si le joueurs possèdent 
	 * les différents bonus dans son inventaire
	 */
	public void actualisationLabels() {
		choice = 0;
		fifty = 0;
		for (String str : player.getInventory()) {
			switch (str) {
			case "Choose category":
				choice++;
				break;
			case "Fifty / Fifty":
				fifty++;
				break;
			}
		}
		getLblChooseCat().setText("x "+choice);
		getLblFiftyFifty().setText("x "+ fifty);
		}

	/**
	 * getter permettant la création si null du label lblPlayer
	 * @return
	 */
	public Label getLblPlayer() {
		if (lblPlayer == null) {
			lblPlayer = new Label(player.getNickName()/* + " +++ " + player.getTransaction()*/);
		}
		return lblPlayer;
	}

	/**
	 * Getter permettant la création du bouton fiftyFifty si ce dernier n'est pas créer
	 * @return
	 */
	public Button getBtnFiftyFifty() {
		if (btnFiftyFifty == null) {
			btnFiftyFifty = new Button(Spell.FIFTY_FIFTY.getS());
		}
		return btnFiftyFifty;
	}
	
	/**
	 * getter permettant la création du bouton chooseCat si ce dernier n'est pas créer
	 * @return
	 */
	public Button getBtnChooseCat() {
		if (btnChooseCat == null) {
			btnChooseCat = new Button(Spell.CHOOSE_CAT.getS());
		}
		return btnChooseCat;
	}

	/**
	 * getter permettant la création du label fiftyFifty si ce dernier n'est pas créer 
	 * @return
	 */
	public Label getLblFiftyFifty() {
		if (lblFiftyFifty == null) {
			lblFiftyFifty = new Label();
		}
		return lblFiftyFifty;
	}

	/**
	 * getter permettant la création du label chooseCat si ce dernier n'est pas créer 
	 * @return
	 */
	public Label getLblChooseCat() {
		if (lblChooseCat == null) {
			lblChooseCat = new Label();
		}
		return lblChooseCat;
	}

	/**
	 * getter revoyant un joueur
	 * @return 
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * setter du joueur
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * setter
	 * @param choice
	 */
	public void setChoice(int choice) {
		this.choice = choice;
	}

	/**
	 * setter
	 * @param fifty
	 */
	public void setFifty(int fifty) {
		this.fifty = fifty;
	}

}
