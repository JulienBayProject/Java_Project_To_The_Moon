package view.duringGame;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.bonus.PriceSpell;
import model.bonus.Spell;
import model.player.Player;

public class ShopSpellBP extends BorderPane {
	
	private VBox purchasable;
	
	private Button btnFiftyFifty;
	private Button btnChooseCat;
	
	private Label lblPlayer;
	
	private double spacing = 20.;
	
	private Player player;
	
	
	/**
	 * creation d'un shop
	 * @param p
	 */
	public ShopSpellBP(Player p) {
		this.player = p;
		this.setCenter(getPurchasable());
		this.setLeft(getLblPlayer());
		this.getStyleClass().add("alert_css");
	}
	
	/**
	 * Methode retournant une VBox si celle-ci n'a pas été crée au préalable
	 * @return
	 */
	public VBox getPurchasable() {
		if(purchasable==null) {
			HBox hb = new HBox();
			hb.getChildren().addAll(getBtnFiftyFifty(),getBtnChooseCat());
			hb.setAlignment(Pos.CENTER);
			hb.setSpacing(spacing);
			purchasable = new VBox(hb);
			purchasable.setAlignment(Pos.CENTER);
		}
		return purchasable;
	}
	
	/**
	 * Méthode réactualisant le label des players
	 */
	public void refresh() {
		lblPlayer.setText(player.getNickName());
	}
	
	/**
	 * getter permettant la création du lblPlayer si ce-dernier n'est pas créer
	 * @return
	 */
	public Label getLblPlayer() {
		if(lblPlayer==null) {
			lblPlayer = new Label(player.getNickName());
		}
		return lblPlayer;
	}

	/**
	 * getter permettant la création du bouton fiftyFifty si ce-dernier n'est pas créer
	 * @return
	 */
	public Button getBtnFiftyFifty() {
		if(btnFiftyFifty==null) {
			btnFiftyFifty = new Button(Spell.FIFTY_FIFTY.getS()+"\nPrice : "+PriceSpell.FIFTY_FIFTY.getPrice()+" gold");
		}
		return btnFiftyFifty;
	}

	/**
	 * getter permettant la création du bouton chooseCat si ce-dernier n'est pas créer
	 * @return
	 */
	public Button getBtnChooseCat() {
		if(btnChooseCat==null) {
			btnChooseCat = new Button(Spell.CHOOSE_CAT.getS()+"\nPrice : "+PriceSpell.CHOOSE_CAT.getPrice()+" gold");
		}
		return btnChooseCat;
	}
	
	/**
	 * setter du player
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * getter du player
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}
}
