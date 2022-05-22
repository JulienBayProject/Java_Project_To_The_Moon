package view.menu;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import song.Sound;

public class HomepagelSP extends StackPane {
	
	double test;
	
	private Button boutonAccueil = new Button();
	Sound sound = new Sound();
	
	double translation = 300;
	
	public void playMusic(int i) {
		Sound.setFile(i);
		Sound.play();
		sound.loop();
	}
	
	public void stopMusic() {
		sound.stop();
	}
	
	public void playSE(int i) {
		Sound.setFile(i);
		Sound.play();
	}

	public HomepagelSP() {
//		Définition du fond d'accueil
		this.setId("stack-pane");
		createButton();
		playMusic(0);
		
		
	}
	
// Effet d'ombre
	private Effect shadow() {
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(15.0);
		dropShadow.setOffsetX(9.0);
		dropShadow.setOffsetY(9.0);
		dropShadow.setColor(Color.color(0.2, 0, 0.5));
		return dropShadow;
	}
	
//	Création du boutton
	private void createButton() {
		Image img = new Image("images/start-buttonV2.png");
		ImageView view = new ImageView(img);
		boutonAccueil.setId("boutton");
		//changer la taille de l'image
		view.setFitHeight(200);
		view.setFitWidth(500);
		//ajout de l'image au bouton
		boutonAccueil.setGraphic(view);
		//modification de la taille de l'image
		boutonAccueil.setPrefSize(300, 200);
		boutonAccueil.setTranslateY(translation);
		
		this.getChildren().addAll(boutonAccueil);
		
//		Ombre lors de l'entrée sur boutton
		boutonAccueil.setOnMouseEntered(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				boutonAccueil.setEffect(shadow());
			}
		});
		
		boutonAccueil.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					boutonAccueil.setTranslateY(translation + 4);
					
					Sound.setFile(1);
					Sound.play();
				}
				
			}
		});
		
		boutonAccueil.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					boutonAccueil.setTranslateY(translation - 4);
				}
				
			}
		});
		
//		Annulation de l'ombre lors de la sortie du boutton
		boutonAccueil.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				boutonAccueil.setEffect(null);
				
			}
		});
	}	
	
	public Button getBoutonAccueil() {
		return boutonAccueil;
	}
}