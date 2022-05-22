package view.toolsView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AlertChooseCatVB extends VBox implements AnimationInterface{
	
	private Label lblHead,lblContent;
	private List<Button> buttons;
	
	private Button btnExit;
	
	/**
	 * création d'une alertChooseCat
	 */
	public AlertChooseCatVB(){
		getButtons();
		this.getChildren().addAll(getLblHead(),getLblContent());
		createButtons();
		this.getChildren().add(getBtnExit());
		this.setSpacing(20.);
		this.setVisible(false);
		this.getStyleClass().add("alert_css");
		this.setAlignment(Pos.CENTER);
	}

	/**
	 * getter permettant la création du label head
	 * @return
	 */
	public Label getLblHead() {
		if(lblHead==null) {
			lblHead = new Label("Choose category !");
		}
		return lblHead;
	}

	/**
	 * getter permettant la création du lable content
	 * @return
	 */
	public Label getLblContent() {
		if(lblContent==null) {
			lblContent = new Label("Choose the category you want to have");
		}
		return lblContent;
	}

	/**
	 * getter permettant la création du bouton exit
	 * @return
	 */
	public Button getBtnExit() {
		if(btnExit==null) {
			btnExit = new Button("Cancel");
		}
		return btnExit;
	}
	
	/**
	 * getter permettant d'instancier la list si elle ne l'a pas été auparavant
	 * @return
	 */
	public List<Button> getButtons() {
		if(buttons==null) {
			buttons = new ArrayList<Button>();
		}
		return buttons;
	}
	
	/**
	 * Méthode permettant la création des différents boutons pour chaques catégories spécifiés
	 */
	public void createButtons() {
		List<String> categories = new ArrayList<String>();
		Collections.addAll(categories, "Ideas","Science","Planet","History","Literature","Computer");
		HBox hb1 = new HBox(20.);
		HBox hb2 = new HBox(20.);
		for(int i=0;i<categories.size();i++) {
			Button b = new Button(categories.get(i));
			buttons.add(b);
			if(i<3) {				
				hb1.getChildren().add(b);
			}else {
				hb2.getChildren().add(b);
			}
		}
		hb1.setAlignment(Pos.CENTER);
		hb2.setAlignment(Pos.CENTER);
		this.getChildren().addAll(hb1,hb2);
	}
}
