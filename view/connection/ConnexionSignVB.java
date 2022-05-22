package view.connection;

import java.util.List;

import exception.NickNameLongerException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.player.GestionMember;
import model.player.Member;
import persistence.Persistence;
import view.toolsView.AlerteVB;
import view.toolsView.AnimationInterface;
import view.toolsView.WindowData;

public class ConnexionSignVB extends VBox implements  WindowData, AnimationInterface{

	
	private GestionMember gestions;
	
	private Label lblUser;
	private TextField txtUser;

	private Label lblPassword;
	private PasswordField pswUser;
	
	private Button btnConnexion;
	private Button btnSubscribe;
	
	private Button previousMenu;
	
	private AlerteVB alert;
	
	
	/**
	 * Assure la connection d'un membre et assure l'ouverture de la page profil du membre
	 */
	public ConnexionSignVB() {
		
		// on récupere la liste des membres
		getGestions();
		
		// on dispose les composants
		HBox user = new HBox(PADDING, getLblUser(), getTxtUser());
		user.setAlignment(Pos.CENTER);
		HBox psd = new HBox(PADDING, getLblPassword(), getPswUser());
		psd.setAlignment(Pos.CENTER);
		HBox btn = new HBox(PADDING ,getBtnConnexion(), getBtnSubscribe());
		btn.setAlignment(Pos.CENTER);
		
		
		// on integre les composants a la VBox
		this.getChildren().addAll( user,psd,btn, getPreviousMenu(), getAlert());
		this.setSpacing(PADDING);
		this.setAlignment(Pos.CENTER);
	}
	
	/**
	 * obtient la liste de membres depuis le fichier json
	 * @return GestionMember
	 */
	public GestionMember getGestions() {
		gestions = Persistence.readingJson("databaseUser.json", GestionMember.class);
		return gestions;
	}
	
	/**
	 * obtient le clone de la liste de membre
	 * @return List<Member>
	 */
	public List<Member> getCloneList() {
		return gestions.getMembers();
	}
	
	/**
	 * renvoit et instancie si besoin l'élément de classe AlerteVB
	 * @return AlertVB
	 */
	public AlerteVB getAlert() {
		if (alert == null) {
			alert = new AlerteVB();
		}
		return alert;
	}
	
	/**
	 * renvoit et instancie si besoin un élément de classe Label ayant pour valeur "Pseudos : "
	 * @return Label 
	 */
	public Label getLblUser() {
		if(lblUser == null) {
			lblUser = new Label("Pseudo : ");
		}
		return lblUser;
	}
	
	/**
	 * renvoit et instancie si besoin un élément de classe Textfield prevu pour recevoir le nom de l'utilisateur 
	 * @return TextField
	 */
	public TextField getTxtUser() {
		if(txtUser == null) {
			txtUser = new TextField();
		}
		return txtUser;
	}
	
	/**
	 * renvoit et instancie si besoin un élément de classe Label ayant pour valeur "Passord : "
	 * @return Label
	 */
	public Label getLblPassword() {
		if(lblPassword == null) {
			lblPassword = new Label("Password : ");
		}
		return lblPassword;
	}
	
	/**
	 * renvoit et instancie si besoin un élément de classe PasswordField prevu pour recevoir le mot de passe de l'utilisateur
	 * @return PasswordField
	 */
	public PasswordField getPswUser() {
		if(pswUser == null) {
			pswUser = new PasswordField();
		
		}
		return pswUser;
	}
	
	
	/**
	 * renvoit et instancie si besoin un élément de classe Button ayant pour texte "Previous menu"
	 * @return Button
	 */
	public Button getPreviousMenu() {
		if(previousMenu==null) {
			previousMenu = new Button("Previous menu");
			animateClick(previousMenu);
		}
		return previousMenu;
	}
	
	/**
	 * renvoit et instancie si besoin un élément de classe Button ayant pour texte "Sign in"
	 * permettant la creation d'un compte membre si le pseudo n'est pas deja present dans le JSON 
	 * @return Button
	 */
	public Button getBtnSubscribe() {
		if(btnSubscribe == null) {
			btnSubscribe = new Button("Sign in");
			animateClick(btnSubscribe);
			// action qui cree un compte membre 
			btnSubscribe.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
					// on recupere la liste de membre
					gestions = Persistence.readingJson("databaseUser.json", GestionMember.class);
					
					// condition pour ne pas avoir de mdp et pseudo null
					if ( !getTxtUser().getText().isEmpty() && !getPswUser().getText().isEmpty()) {

						// on tente de creer un membre avec les donnees, profitant ainsi des verifications faite par la classe
						Member m = null;
						try {
							m = new Member(getTxtUser().getText(),getPswUser().getText());
						} catch (NickNameLongerException e) {
							e.printStackTrace();
						}
						
						// traitement si le membre n'est pas present dans la liste
						if(!gestions.contains(m)) {
							
							// on ajoute le membre + enregistrement dans le json + avertissement de la reussite de l'ajout
							gestions.add(m);
							Persistence.writeJson("databaseUser.json", gestions);
							alert.writeAlert("Welcome !","The registration was succesful", "Continue");
							
							alert.getOkBtn().setOnAction((e) -> {
								animateFadeOut(alert, DURATION_FADE);
							});
							gestions = Persistence.readingJson("databaseUser.json", GestionMember.class);
							
						// sinon on previent que le nom est deja present dans la liste	
						}else {
							
							alert.writeAlert("Welcome !", "This nickname is already used","Retry");
							alert.getOkBtn().setOnAction((e) -> {
								animateFadeOut(alert, DURATION_FADE);
							});
						}
						
						// on vide les inputs password et nickname
						getTxtUser().setText("");
						getPswUser().setText("");
					}
				}
			});
		}
		return btnSubscribe;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Button ayant pour texte "Connect"
	 * @return
	 */
	public Button getBtnConnexion() {
		if (btnConnexion == null) {
			btnConnexion = new Button("Connect");
			animateClick(btnConnexion);
		}
		return btnConnexion;
	}
}
