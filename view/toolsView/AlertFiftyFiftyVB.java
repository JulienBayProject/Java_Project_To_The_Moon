package view.toolsView;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AlertFiftyFiftyVB extends VBox implements AnimationInterface{
	
	private Label head,content;
	private Button yes,no;
	
	/**
	 * Création d'une alertFiftyFifty
	 */
	public AlertFiftyFiftyVB() {
		HBox hb = new HBox(20.,getYes(),getNo());
		hb.setAlignment(Pos.CENTER);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20.);
		this.getChildren().addAll(getHead(),getContent(),hb);
		this.setVisible(false);
		this.getStyleClass().add("alert_css");
	}
	
	/**
	 * Méthode permettant de changer les labels à l'appel de la méthode
	 * @param head
	 * @param content
	 */
	public void writeAlert(String head, String content) {
		getHead().setText(head);
		getContent().setText(content);
		animateNodeViewIn(this, DURA_ALERT_IN);
	}
	
	/**
	 * getter permettant la création du label head si ce-dernier n'a pas été crée
	 * @return
	 */
	public Label getHead() {
		if(head==null) {
			head = new Label();
		}
		return head;
	}
	
	/**
	 * getter permettant la création du label content si ce-dernier n'a pas été crée
	 * @return
	 */
	public Label getContent() {
		if(content==null) {
			content = new Label();
		}
		return content;
	}
	
	/**
	 * getter permettant la création du bouton yes si ce-dernier n'a pas été crée
	 * @return
	 */
	public Button getYes() {
		if(yes==null) {
			yes = new Button("Yes");
		}
		return yes;
	}
	/**
	 * getter permettant la création du bouton no si ce-dernier n'a pas été crée
	 * @return
	 */
	public Button getNo() {
		if(no==null) {
			no = new Button("No");
		}
		return no;
	}
	

	
	

}
