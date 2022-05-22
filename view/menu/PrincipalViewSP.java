package view.menu;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.player.GestionMember;
import model.player.Member;
import model.player.Player;
import model.player.TransactionWalletPo;
import persistence.Persistence;
import view.admin.AdminSP;
import view.connection.ConnexionSignVB;
import view.connection.ProfileMemberBP;
import view.credits.CreditsSP;
import view.duringGame.DuringGameSP;
import view.duringGame.SetupGameViewAP;
import view.duringGame.ViewEndGameSP;
import view.toolsView.AnimationInterface;

public class PrincipalViewSP extends StackPane implements AnimationInterface{

	private HomepagelSP accueil;
	private MainMenuSP menu;
	private DuringGameSP duringGame;
	private SetupGameViewAP setupgame;
	private ViewEndGameSP eg;
	private ProfileMemberBP profile;
	private AdminSP admin;
	private CreditsSP credits;
	
	private ConnexionSignVB connexionSign;
	private boolean isBtnForAdminSP;
	
	/**
	 * Classe de fenetre principal qui regroupe et permet le passage d'une fenetre a l'autre
	 */
	public PrincipalViewSP() {
		isBtnForAdminSP = false;
		this.getChildren().addAll( getAccueil(), getMenu(), getSetupGameView(), getConnexionSignVB(), getProfile(), getAdmin(), getCreditsSP());
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe AdminSP - fenetre administrateur
	 * @return AdminSP
	 */
	public AdminSP getAdmin() {
		if ( admin == null) {
			admin = new AdminSP();
			admin.setVisible(false);
			admin.getPreviousBtn().setOnAction((e) -> {
				animateFadeIn(menu, DURA_FRAME);
				animateFadeOut(admin, DURA_FRAME);
				isBtnForAdminSP = false;
				getConnexionSignVB().getTxtUser().setText("");
				getConnexionSignVB().getPswUser().setText("");
				getConnexionSignVB().setVisible(false);
			});
		}
		return admin;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe CreditSP - fenetre reprenant les credits
	 * @return CreditSP
	 */
	public CreditsSP getCreditsSP() {
		if ( credits == null) {
			credits = new CreditsSP();
			credits.setVisible(false);
			credits.getPreviousBtn().setOnAction((e) -> {
				animateFadeIn(menu, DURA_FRAME);
				animateFadeOut(credits, DURA_FRAME);
			});
		}
		return credits;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe ConnexionSignVB - fenetre permettant la connexion / inscription 
	 * @return ConnexionSignVB
	 */
	public ConnexionSignVB getConnexionSignVB() {
		if (connexionSign == null) {
			connexionSign = new ConnexionSignVB();
			connexionSign.setVisible(false);
			connexionSign.getStyleClass().add("alert_css");
		}
		return connexionSign;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe AcceuilPagelSP  - fenetre de demarrage
	 * @return AcceuilPagelSP
	 */
	public HomepagelSP getAccueil() {
		if(accueil == null) {
			accueil = new HomepagelSP();
			accueil.getBoutonAccueil().setOnAction((event) -> {
				animateFadeIn(menu, 1.);
				animateFadeOut(accueil, 1.);
			});
		}
		return accueil;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe ProfileMember - fenetre des membres
	 * @return ProfileMember
	 */
	public ProfileMemberBP getProfile() {
		if (profile == null ) {
			profile = new ProfileMemberBP();
			profile.getReturnBtn().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					animateFadeOut(profile, DURA_ALERT_IN);
					getConnexionSignVB().getTxtUser().setText("");
					getConnexionSignVB().getPswUser().setText("");
					getConnexionSignVB().setVisible(false);
				}
			
			});
		}
		return profile;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe MainMenuSP - fenetre des menus
	 * @return MainMenu
	 */
	public MainMenuSP getMenu() {
		if(menu == null) {
			menu = new MainMenuSP();
			menu.setVisible(false);
			
			// instruction retour menu principal 
			menu.getPlanetAdmin().setOnMouseClicked((e) -> {
						getAdmin().refresh();
						isBtnForAdminSP = true;
						getConnexionSignVB().toFront();
						animateNodeViewIn(getConnexionSignVB(), DURA_ALERT_IN);
						
						getConnexionSignVB().getPreviousMenu().setOnAction(new EventHandler<ActionEvent>() {	
							@Override
							public void handle(ActionEvent event) {
								animateNodeViewOut(getConnexionSignVB(), DURA_ALERT_OUT);
								getConnexionSignVB().getTxtUser().setText("");
								getConnexionSignVB().getPswUser().setText("");
							}
						});
				});
			
			// instruction retour menu principal 
			menu.getPlanetPlay().setOnMouseClicked((e) -> {	
					animateFadeIn(getSetupGameView(), DURA_FRAME);
					animateFadeOut(menu, DURA_FRAME);
			});
			
			// instruction retour menu principal  
			menu.getPlanetProfile().setOnMouseClicked((e) -> {
					getConnexionSignVB().toFront();
					System.out.println("coucu");
					animateNodeViewIn(getConnexionSignVB(), DURA_ALERT_IN);
					getConnexionSignVB().getPreviousMenu().setOnAction(new EventHandler<ActionEvent>() {	
						@Override
						public void handle(ActionEvent event) {
							animateNodeViewOut(getConnexionSignVB(), DURA_ALERT_OUT);
							getConnexionSignVB().getTxtUser().setText("");
							getConnexionSignVB().getPswUser().setText("");
						}
					});
			});
			
			// instruction retour menu principal 
			menu.getPlanetCredit().setOnMouseClicked((e) -> {
				
					animateFadeIn(getCreditsSP(), DURA_FRAME);
					animateFadeOut(menu, DURA_FRAME);
			});
			
			// instruction pour quitter le jeu avec confirmation
			menu.getExit().setOnMouseClicked((e) -> {
				Alert al = new Alert(AlertType.CONFIRMATION);
				al.setTitle("Exit");
				al.setHeaderText("Exit button");
				al.setContentText("Would you like to leave us ?");
				Optional<ButtonType> result = al.showAndWait();
				if( result.get() == ButtonType.OK) {
					
					Stage stage = (Stage) this.getScene().getWindow();
					stage.close();
				} 
		});
			
			// verification connexion pour acceder a la page membre 
			getConnexionSignVB().getBtnConnexion().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					boolean find = false;
					for (Member m : getConnexionSignVB().getCloneList()) {
						// connexion reussie avertissement + ouverture de la page 
						if(getConnexionSignVB().getTxtUser().getText().equals(m.getNickName()) && getConnexionSignVB().getPswUser().getText().equals(m.getPassword())) {
							// si l'utilisateur tente de se connecter pour l'administrationc
							if ( isBtnForAdminSP ) {
									// si il est admin on passe a la page d'admin
								if (m.isAdmin()) {
									animateFadeIn(getAdmin(), DURA_FRAME);
									animateFadeOut(menu, DURA_FRAME);
								} 
								
								// pour la page profil
							}else {
								getConnexionSignVB().getAlert().writeAlert("Welcome !","The authentification was succesful","View profil");
								getConnexionSignVB().getAlert().getOkBtn().setOnAction((e) -> {
									animateNodeViewOut(getConnexionSignVB().getAlert(), DURA_ALERT_OUT);
									getProfile().refresh(m);
									animateFadeIn(getProfile(), DURA_FRAME);
								});
							}
							// le membre ou admin a ete trouve
							find = true;
							break;
						}
					}
					isBtnForAdminSP = false;
					
					// connexion raté avertissement 
					// sinon on a verti qu'il n'a pas l'acces
					if (!find) {
						
						getConnexionSignVB().getAlert().setHeadLbl("Error !");
						getConnexionSignVB().getAlert().setContentLbl("access denied");
						getConnexionSignVB().getAlert().setOkBtn("Back");
						getConnexionSignVB().getAlert().animateNodeViewIn(getConnexionSignVB().getAlert(), DURA_ALERT_IN);
						
						getConnexionSignVB().getAlert().getOkBtn().setOnAction((ee) -> {
							animateNodeViewOut(getConnexionSignVB().getAlert(), DURA_ALERT_OUT);
							getConnexionSignVB().getTxtUser().setText("");
							getConnexionSignVB().getPswUser().setText("");
						});
					}
				}
			});
		}
		return menu;
	}
	
	
	/**
	 * renvoit et instancie si besoin un element de classe ViewEndGameGP - fenetre de fin de jeu
	 * @return ViewEndGame
	 */
	public ViewEndGameSP getEg() {
		if ( eg == null ) {
			eg = new ViewEndGameSP();
			eg.setPlayers(getDuringGame().getDuringGame().getPlayers());
		//	eg.setVisible(false);
			eg.getReturnMain().setOnAction((e) -> {
//				animateFadeIn(getMenu(), DURA_FRAME);
//				getSetupGameView().getSetupGame().removeAllPlayer(getSetupGameView().getSetupGame().getClonePlayers()) ;
//				getSetupGameView().clearTableView();
//				duringGame = null;
//				eg=null;
				
				animateFadeOut(eg, DURA_FRAME);
				getSetupGameView().getSetupGame().removeAllPlayer(getSetupGameView().getSetupGame().getClonePlayers()) ;
				getSetupGameView().clearTableView();
				duringGame.setVisible(false);
				eg.getChildren().clear();
				this.getChildren().removeAll(duringGame = null, eg);
				
				animateFadeIn(getMenu(), DURA_FRAME);
			});
		}
		return eg;
	}
	
////////////////////////	
	
	/**
	 * renvoit et instancie si besoin un element de classe SetupGameView - fenetre d'invitation des joueurs
	 * @return SetupGameView
	 */
	public SetupGameViewAP getSetupGameView(){
		if ( setupgame == null) {
			setupgame = new SetupGameViewAP();
			setupgame.setVisible(false);
			setupgame.getStartBtn().setOnAction(new EventHandler<ActionEvent>() {	
				@Override
				public void handle(ActionEvent event) {
					// lancement de la partie si assez de joueur
					if ( setupgame.getSetupGame().getNumberPlayers() > 1) {
						animateFadeIn(getDuringGame(), DURA_FRAME);
						animateFadeOut(setupgame, DURA_FRAME);
					// si pas assez on averti
					}else {
						Alert al = new Alert(AlertType.ERROR);
						al.setContentText("not enough players, minimal 2 players");
						al.showAndWait();
					}
				}
			});
			// retour menu principal
			setupgame.getReturnPrevious().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					animateFadeIn(menu, DURA_FRAME);
					animateFadeOut(setupgame, DURA_FRAME);
				}
			});
		}
		return setupgame;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe DuringGameSP - fenetre de jeu
	 * @return DuringGameSP
	 */
	public DuringGameSP getDuringGame() {
		if(duringGame==null) {
			duringGame = new DuringGameSP(getSetupGameView().getSetupGame().getClonePlayers());
			this.getChildren().add(duringGame);
			
			// quand on quitte la partie, on vide la liste des joueurs et null duringGame
			getDuringGame().getMainMenuIV().setOnMouseClicked((e)-> {
				
				getSetupGameView().getSetupGame().removeAllPlayer(getSetupGameView().getSetupGame().getClonePlayers()) ;
				getSetupGameView().clearTableView();
				animateFadeOut(duringGame, DURA_FRAME);
				this.getChildren().remove(duringGame = null);
				
				animateFadeIn(getMenu(), DURA_FRAME);
				getMenu().toFront();
			});
			
			// btn cheat
			getDuringGame().getCheatEndGame().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
					getDuringGame().getDuringGame().getPlayerActive().callMeGod();
					addEndGame();
				}
			});
			
			
		}
		return duringGame;
	}
	
	public void addEndGame() {
		if ( this.getChildren().contains(getEg()))
			this.getChildren().remove(getEg());
		this.getChildren().add(getEg());
		//getEg().setPlayers(getDuringGame().getDuringGame().getPlayers());
		GestionMember m = Persistence.readingJson("databaseUser.json", GestionMember.class);
		for ( Player p : getDuringGame().getDuringGame().getPlayers() ) {
			if ( p instanceof Member) {
				p.setTransaction(new TransactionWalletPo((Member) p));
				p.addMoney(50);
				m.replaceMember((Member) p, (Member) p);
			}
		}
		Persistence.writeJson("databaseUser.json", m);
		getEg().remplissageEnd(getDuringGame().getDuringGame().getPlayers());
	}
	
}
