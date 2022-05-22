package view.connection;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.player.GestionMember;
import model.player.Member;
import persistence.Persistence;
import view.toolsView.AlerteVB;
import view.toolsView.AnimationInterface;
import view.toolsView.WindowData;

public class ProfileMemberBP extends BorderPane implements AnimationInterface, WindowData{
	
	private Label lblNickname;
	private TextField txtMemberNickname;
	
	private Label lblPass;
	private TextField txtPass;
	private CheckBox showPassCk;
	
	private Label lblWallet;
	private Label lblWallerPlayerPo;
	
	private Member registeredMember;

	private Background bGround;
	private Button returnBtn;
	private Button changeNameBtn;
	private Button changePassBtn;
	
	private AlerteVB alert;
	
	private final double ADJUSTMENT_POS_Y = -200.;
	private final double ADJUSTMENT_POS_X = 25.;
	
	/**
	 * frame reprenant les informations d'un membre
	 */
	public ProfileMemberBP() {
		
		HBox hbName = new HBox(PADDING , getLblNickname() , getTxtMemberNickname());
		hbName.setAlignment(Pos.CENTER);
		
		HBox hbPass = new HBox(PADDING, getLblPass(), getTxtPass(), getShowPassCk());
		hbPass.setAlignment(Pos.CENTER);
		
		HBox hbWallet = new HBox (PADDING, getLblWallet(), getLblWallerPlayerPo());
		hbWallet.setAlignment(Pos.CENTER);
		
		HBox hbBtn = new HBox(PADDING * 2 , getChangeNameBtn(), getChangePassBtn() ,getReturnBtn());
		hbBtn.setAlignment(Pos.CENTER);
		
		VBox v = new VBox(PADDING, hbName, hbPass, hbWallet, hbBtn);
		v.setTranslateY(ADJUSTMENT_POS_Y);
		v.setTranslateX(ADJUSTMENT_POS_X);
		
		v.setId("vbox_profileMember");
		v.setAlignment(Pos.CENTER);

		this.setBackground(getBack());
		this.setBottom(getAlert());
		this.setCenter(v);
		this.setVisible(false);
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Background avec l'arriere plan image correspondant a la classe
	 * @return Background
	 */
	public Background getBack() {
		if ( bGround == null ) {
			
	    Image img = new Image("./images/profileMember/profileMember-view.jpg");
	    BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
	            new BackgroundSize(WIDTH_SCREEN, HEIGHT_SCREEN, isDisabled(), isDisable(), isCache(), isHover()));
	    bGround = new Background(bImg);
		}
		return bGround;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe AlerteVB
	 * @return AlerteVB
	 */
	public AlerteVB getAlert() {
		if (alert == null) {
			alert = new AlerteVB();
		}
		return alert;
	}
	
	public void setRegisteredMember(Member registeredMember) {
		this.registeredMember = registeredMember;
	}
	
	/**
	 * Met a jour les composants avec les donnees du membre qui se connecte
	 * Par defaut le mot de passe est masque
	 * @param m Membre
	 */
	public void refresh(Member m) {
		setRegisteredMember(m);
		setLblMemberNickname(getRegisteredMember().getNickName() );
		setLblWallerPlayerPo( getRegisteredMember().getWalletPlayerPO() +"");
		maskPassword();
	}

	/**
	 * renvoit et instancie si besoin un element de classe TextField prevu pour recevoir et afficher le nickname du joueur
	 * Par defaut : desactiver 
	 * @return Textfield
	 */
	public TextField getTxtMemberNickname() {
		if(txtMemberNickname==null) {
			txtMemberNickname = new TextField();
			txtMemberNickname.setFocusTraversable(false);
			txtMemberNickname.setDisable(true);
		}
		return txtMemberNickname;
	}

	/**
	 * renvoit et instancie si besoin un element de classe Label prevu pour afficher le nombre po present dans son porte-feuille
	 * @return Label
	 */
	public Label getLblWallerPlayerPo() {
		if(lblWallerPlayerPo==null) {
			lblWallerPlayerPo = new Label();
		}
		return lblWallerPlayerPo;
	}

	/**
	 * renvoit le membre connecte actuellement
	 * @return Member
	 */
	public Member getRegisteredMember() {
		return registeredMember;
	}

	/**
	 * change la valeur texte du Label affichant le nickname du joueur
	 * @param lblMemberNickname nom joueur remplacant
	 */
	public void setLblMemberNickname(String lblMemberNickname) {
		this.txtMemberNickname.setText(lblMemberNickname);
	}

	/**
	 * change la valeur texte du Label affichant le montant porte-feuille du joueur
	 * @param lblWallerPlayerPo montant portefeuille textuelle du joueur remplacant
	 */
	public void setLblWallerPlayerPo(String lblWallerPlayerPo) {
		this.lblWallerPlayerPo.setText(lblWallerPlayerPo);
	}

	/**
	 * renvoit et instancie si besoin un element de classe Label contenant "Nickname :"
	 * @return Label
	 */
	public Label getLblNickname() {
		if ( lblNickname == null ) {
			lblNickname = new Label("Nickname :");
		}
		return lblNickname;
	}

	/**
	 * renvoit et instancie si besoin un element de classe Label contenant "Wallet : "
	 * @return Label
	 */
	public Label getLblWallet() {
		if ( lblWallet == null) {
			lblWallet = new Label("Wallet :");
		}
		return lblWallet;
	}

	/**
	 * renvoit et instancie si besoin un element de classe Button prevu pour le retour au main menu
	 * @return Button
	 */
	public Button getReturnBtn() {
		if ( returnBtn ==  null ) {
			returnBtn = new Button("Main Menu");
			animateClick(returnBtn);
		}
		return returnBtn;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Button prevu lancer la procedure du changement de nom
	 * @return Button
	 */
	public Button getChangeNameBtn() {
		if ( changeNameBtn == null ) {
			changeNameBtn = new Button("Change nickname");
			animateClick(changeNameBtn);
			changeNameBtn.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					
					// preparation de la procedure on active les nodes correspondant et desactive les autres
					if ( changeNameBtn.getText().equals("Change nickname")) {
						
						getTxtMemberNickname().setDisable(false);
						changeNameBtn.setText("Save");
						getReturnBtn().setVisible(false);
						getChangePassBtn().setVisible(false);
					
					// lancement de la procedure de changement de nom	
					} else {
						// changement de la liste de membre
						GestionMember gestion = Persistence.readingJson("databaseUser.json", GestionMember.class);
						
						// si le nom est valable (non present dans la liste) ou inchange
						if (gestion.availableName(getTxtMemberNickname().getText()) || getTxtMemberNickname().getText().equals(getRegisteredMember().getNickName()) ) {
							
							// on clone le membre connecte, on change son nom et on remplace le membre avec l'ainsi nom par le clone avec le nouveau nom on verifie si admin et on modifie aussi
							Member newM = getRegisteredMember().clone();
							newM.setNickName(getTxtMemberNickname().getText());
							if ( getRegisteredMember().isAdmin() )
								newM.changeAdmin();
							
							gestion.replaceMember(getRegisteredMember(), newM);
							
							// avertissement de la reussite de l'operation
							getAlert().writeAlert("Rename Member","The rename was succesful","Continue");
							
							// une fois confirme, on rafraichi les donnees affichees et enregistre la liste de membre
							getAlert().getOkBtn().setOnAction((e) -> {
								animateNodeViewOut(getAlert(), DURA_ALERT_OUT);
								refresh(newM);
								Persistence.writeJson("databaseUser.json", gestion);
							});
						// si le nom n'est pas valable, on averti et on remet les nodes en etat par defaut	
						} else {
							getAlert().writeAlert("Rename Member","The name was unavailable, please retry","Retry");
							
							getAlert().getOkBtn().setOnAction((e) -> {
								animateNodeViewOut(getAlert(), DURA_ALERT_OUT);
								refresh(getRegisteredMember());
							});
						}
						
						// re activation des autres boutons des nodes
						changeNameBtn.setText("Change nickname");
						getReturnBtn().setVisible(true);
						getChangePassBtn().setVisible(true);
						getTxtMemberNickname().setDisable(true);
					}
				}
			});
		}
		return changeNameBtn;
	}

	/**
	 * renvoit et instancie si besoin un element de classe Label ayant pour valeur "Password"
	 * @return Label
	 */
	public Label getLblPass() {
		if ( lblPass == null ) {
			lblPass = new Label("Password");
		}
		return lblPass;
	}

	/**
	 * renvoit et instancie si besoin un element de classe Textfield prevu pour recevoir et afficher le mot de passe du joueur
	 * @return Textfield
	 */
	public TextField getTxtPass() {
		if ( txtPass == null ) {
			txtPass = new TextField();
			txtPass.setDisable(true);
		}
		return txtPass;
	}
	
	/**
	 * change la valeur contenue dans le Textfield contenant le mot de passe
	 * @param txtPass nouveau mot de passe a afficher
	 */
	public void setTxtPass(String txtPass) {
		this.txtPass.setText(txtPass);
	}
	
	/**
	 * remplace chaque carractere du mot de passe par '*'
	 */
	public void maskPassword() {
		String mask = "";
		int count = this.txtPass.getText().length();
		for (int i = 0 ; i < count ; i++) {
			mask += "*";
		}
		setTxtPass(mask);
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe CheckBox permettant si selectionner d'afficher le mot de passe
	 * du membre actuellement connecte
	 * @return CheckBox
	 */
	public CheckBox getShowPassCk() { 
		if (showPassCk == null) {
			showPassCk = new CheckBox("Show");
			
			showPassCk.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if ( showPassCk.isSelected() )
						setTxtPass(getRegisteredMember().getPassword());
					else 
						maskPassword();
					
				}
			
			});
		}
		return showPassCk;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Button prevu pour lancer la procedure du changement de mot de passe
	 * @return Button
	 */
	public Button getChangePassBtn() {
		if ( changePassBtn == null ) {
			changePassBtn = new Button("Change password");
			animateClick(changePassBtn);
			changePassBtn.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// preparation de la procedure pour le changement de mot de passe en bloquant les elements non concerne
					if ( changePassBtn.getText().equals("Change password")) {
						
						setTxtPass(getRegisteredMember().getPassword());
						getTxtPass().setDisable(false);
						getShowPassCk().setDisable(true);
						
						changePassBtn.setText("Save");
						getReturnBtn().setVisible(false);
						getChangeNameBtn().setVisible(false);
					
					// lancement de la procedure de changement de mot de passe
					} else {
						// chargement de la liste des membres
						GestionMember gestion = Persistence.readingJson("databaseUser.json", GestionMember.class);
						
						// si le nouveau mot de passe n'est pas nulle ou identique
						if (!getTxtPass().getText().isEmpty() || getTxtPass().getText().equals(getRegisteredMember().getPassword()) ) {
							
							// on clone le membre connecte, on change le password du clone et on remplace le membre avec 
							// l'ancien mot de passe par le clone disposant du nouveau mot de passe
							Member newM = getRegisteredMember().clone();
							newM.setPassword(getTxtPass().getText());
							
							if ( getRegisteredMember().isAdmin() )
								newM.changeAdmin();
							
							gestion.replaceMember(getRegisteredMember(), newM);
							
							// on avertit de la reussite
							getAlert().writeAlert("Rename password","The rename password was succesful","Continue");
							
							// on rafraichit les donnees affichees et sauvegarde la liste de membre
							getAlert().getOkBtn().setOnAction((e) -> {
								animateNodeViewOut(getAlert(), DURA_ALERT_OUT);
								refresh(newM);
								Persistence.writeJson("databaseUser.json", gestion);
							});
							
						// si le nouveau mot de passe ne convient pas on avertit
						} else {
							getAlert().writeAlert("Rename password","The password was unavailable, please retry","Retry");
							
							// on redispose les donnees
							getAlert().getOkBtn().setOnAction((e) -> {
								animateNodeViewOut(getAlert(), DURA_ALERT_OUT);
								refresh(getRegisteredMember());
							});
						}
						
						// remise en place des nodes en fin de procedure
						getTxtPass().setDisable(true);
						getShowPassCk().setDisable(true);
						
						changePassBtn.setText("Change password");
						getReturnBtn().setVisible(true);
						getChangeNameBtn().setVisible(true);
					}
				}
			});
		}
		return changePassBtn;
	}
}
