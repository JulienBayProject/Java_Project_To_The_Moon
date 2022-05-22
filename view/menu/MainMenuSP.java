package view.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import view.toolsView.AnimationInterface;
import view.toolsView.WindowData;

public class MainMenuSP extends StackPane implements WindowData, AnimationInterface{
	
	private VBox planetProfile;
	private VBox planetPlay;
	private VBox planetCredit;
	private VBox planetAdmin;
	
	private ImageView exit;
	private ImageView back;	
	
	
	/**
	 * Classe reprenant les menus possible en entree
	 */
	public MainMenuSP(){

		AnchorPane locationPlanet = new AnchorPane(getPlanetAdmin(), getPlanetPlay(), getPlanetProfile(), getPlanetCredit(), getExit());
		
		AnchorPane.setLeftAnchor(getPlanetAdmin(), (WIDTH_SCREEN / 60.) );
		AnchorPane.setTopAnchor(getPlanetAdmin(), HEIGHT_SCREEN / 4.2);

		AnchorPane.setLeftAnchor(getPlanetPlay(), (WIDTH_SCREEN / 3.2) ); // 600.
		AnchorPane.setTopAnchor(getPlanetPlay(), HEIGHT_SCREEN / 3.2); // 300.
		
		AnchorPane.setLeftAnchor(getPlanetProfile(), (WIDTH_SCREEN / 1.745) ); // 600.
		AnchorPane.setTopAnchor(getPlanetProfile(), HEIGHT_SCREEN / 3.5); 
		
		AnchorPane.setLeftAnchor(getPlanetCredit(), WIDTH_SCREEN / 1.163 ); // 600.
		AnchorPane.setTopAnchor(getPlanetCredit(), HEIGHT_SCREEN / 3.6); 
		
		AnchorPane.setLeftAnchor(getExit(), WIDTH_SCREEN / 1.9 ); // 600.
		AnchorPane.setBottomAnchor(getExit(), HEIGHT_SCREEN / 9.); 
		
		this.getChildren().addAll(getBack() ,locationPlanet);
	}

	/**
	 * renvoit et instancie si besoin un element de classe VBox reprenant le design et dimension lie au profil
	 * @return VBox
	 */
	public VBox getPlanetProfile() {
		if (planetProfile == null  ) {
			ImageView iv = new ImageView(new Image("./images/mainMenu/planet3.png"));
			iv.setFitWidth(250.);
			iv.setPreserveRatio(true);
			iv.getStyleClass().add("planet");
			
			Label lbl = new Label("Profile");
			lbl.getStyleClass().add("planetLbl");
			
			planetProfile = new VBox( lbl,iv);
			planetProfile.setAlignment(Pos.CENTER);
			planetProfile.getStyleClass().add("VBox_planet");
		}
		return planetProfile;
	}

	/**
	 * renvoit et instancie si besoin un element de classe VBox reprenant le design et dimension lie au lancement de partie
	 * @return VBox
	 */
	public VBox getPlanetPlay() {
		if ( planetPlay == null ) {
			ImageView iv = new ImageView(new Image("./images/mainMenu/planet1.png"));
			iv.setFitWidth(200.);
			iv.setPreserveRatio(true);
			iv.getStyleClass().add("planet");
			
			Label lbl = new Label("Play");
			lbl.getStyleClass().add("planetLbl");
			
			planetPlay = new VBox( iv, lbl);
			planetPlay.setAlignment(Pos.CENTER);
			planetPlay.getStyleClass().add("VBox_planet");
		}
		return planetPlay;
	}

	/**
	 * renvoit et instancie si besoin un element de classe VBox reprenant le design et dimension lie a la page credit
	 * @return VBox
	 */
	public VBox getPlanetCredit() {
		if ( planetCredit == null) {
			ImageView iv = new ImageView(new Image("./images/mainMenu/planet2.png"));
			iv.setFitWidth(200.);
			iv.setPreserveRatio(true);
			iv.getStyleClass().add("planet");
			
			Label lbl = new Label("Credit");
			lbl.getStyleClass().add("planetLbl");
			
			planetCredit = new VBox( iv, lbl);
			planetCredit.setAlignment(Pos.CENTER);
			planetCredit.getStyleClass().add("VBox_planet");
		}
		return planetCredit;
	}

	/**
	 * renvoit et instancie si besoin un element de classe VBox reprenant le design et dimension lie a la page admin
	 * @return VBox
	 */
	public VBox getPlanetAdmin() {
		if ( planetAdmin == null) {
			ImageView iv = new ImageView(new Image("./images/mainMenu/planet4.png"));
			iv.setFitWidth(280.);
			iv.setPreserveRatio(true);
			iv.getStyleClass().add("planet");
			
			Label lbl = new Label("Administration");
			lbl.getStyleClass().add("planetLbl");
			
			planetAdmin = new VBox(lbl,iv);
			planetAdmin.setAlignment(Pos.CENTER);
			planetAdmin.getStyleClass().add("VBox_planet");
		}
		return planetAdmin;
	}
	
	
	/**
	 * renvoit et instancie si besoin un element de classe ImageView reprenant l'image de fond
	 * @return ImageView
	 */
	public ImageView getBack() {
		if (back == null) {
			back = new ImageView(new Image("images/mainMenu/mainMenuBack - Copie.png"));
			back.setFitHeight(HEIGHT_SCREEN);
			back.setFitWidth(WIDTH_SCREEN);
		//	back.setPreserveRatio(true);
		}
		return back;
	}
	
	/**
	 * envoit et instancie si besoin un element d classe ImageView reprenant l'image bouton exit 
	 * @return ImageView
	 */
	public ImageView getExit() {
		if ( exit == null ) {
			exit = new ImageView(new Image("images/mainMenu/btnExit.png"));
			animateClick(exit);
			exit.getStyleClass().add("VBox_planet");
		}
		return exit;
	}
}