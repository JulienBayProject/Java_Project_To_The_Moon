package view.toolsView;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AlerteVB extends VBox implements AnimationInterface
{
	
	private Label headLbl, contentLbl;
	private Button okBtn;
	
	/**
	 * Creation du classe servan
	 */
	public AlerteVB() {
		
		this.getChildren().addAll( getHeadLbl(), getContentLbl(), getOkBtn());
		
		this.setAlignment(Pos.CENTER);

		this.getStyleClass().add("alert_css");
		this.setVisible(false);
		
	}
	

	/**
	 * change le contenu de l'alerte et l'affiche 
	 * @param head en-tete 
	 * @param content contenu
	 * @param btnString valeur texte du bouton
	 */
	public void writeAlert(String head, String content, String btnString) {
		setHeadLbl(head);
		setContentLbl(content);
		setOkBtn(btnString);
		animateNodeViewIn(this, DURATION_FADE);
	}
	
	public Label getHeadLbl() {
		if ( headLbl == null) {
			headLbl = new Label();
		}
		return headLbl;
	}

	public void setHeadLbl(String headLbl) {
		getHeadLbl().setText(headLbl);
	}

	public Label getContentLbl() {
		if ( contentLbl == null ) {
			contentLbl = new Label();
		}
		return contentLbl;
	}

	public void setContentLbl(String contentLbl) {
		getContentLbl().setText(contentLbl);;
	}

	public Button getOkBtn() {
		if ( okBtn == null ) {
			okBtn = new Button();
			animateClick(okBtn);
		}
		return okBtn;
	}

	public void setOkBtn(String okBtn) {
		getOkBtn().setText(okBtn);
	}

}
