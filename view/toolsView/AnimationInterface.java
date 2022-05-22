package view.toolsView;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import song.Sound;
import view.connection.ConnexionSignVB;
import view.menu.HomepagelSP;

public interface AnimationInterface {

	public final double DURA_ALERT_IN = 0.5;
	public final double DURA_ALERT_OUT = 0.5;

	public final double DURA_DICE = 1.5;
	
	public final double DURA_QUESTION_IN = 2.;
	public final double DURA_QUESTION_OUT = 2.;
	
	public final double DURATION_FADE = 0.2;
	
	public final double DURA_FRAME = 0.5;
	
	
	/**
	 * Animation transistion du bas (hors ecran) de l'ecran vers le milieu
	 * @param n Node
	 * @param dura Duration
	 */
	public default void animateNodeViewIn(Node n, double dura ) {
		n.setVisible(true);
		if ( n instanceof ConnexionSignVB) {
			Sound.setFile(4);
			Sound.play();
		}
		TranslateTransition transition = new TranslateTransition(Duration.seconds(dura), n);
		transition.setFromY(1000);
		transition.setByX(0);
		transition.setToY(0);
		transition.play(); 

	}

	/**
	 * Animation transistion du milieu de l ecran vers le bas (hors ecran)
	 * @param n Node
	 * @param dura Duration
	 */
	public default void animateNodeViewOut(Node n, double dura) {
		if ( n instanceof ConnexionSignVB) {
			Sound.setFile(4);
			Sound.play();
		}
		
		TranslateTransition transition = new TranslateTransition(Duration.seconds(dura), n);
		transition.setByX(0);
		transition.setToY(1000);
		transition.play();
		transition.setOnFinished((e) -> {
			n.setVisible(false);
		});
	}
	
	/**
	 * Animation simulation de click sur un Node
	 * @param btn Node
	 */
	public default void animateClick(Node btn) {
		btn.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
					btn.setTranslateY(btn.getTranslateY() + 4);
					//if ( !(btn instanceof HomepagelSP)) {
						Sound.setFile(3);
						Sound.play();
					//}
			}
		});
	
		btn.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
					btn.setTranslateY(btn.getTranslateY() - 4);
			}
		});
	}
	
	/**
	 * Animation changeant l'opacite de 0 à 1
	 * @param n Node
	 * @param dura Duration
	 */
	public default void animateFadeIn(Node n, double dura) {

		n.setVisible(true);
		
		FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(dura), n);
		fadeInTransition.setFromValue(0.0);
		fadeInTransition.setToValue(1.0);
		fadeInTransition.play();
	}
	
	/**
	 * Animation changement d'opacite de 1 a 0
	 * @param n Node
	 * @param dura Duration
	 */
	public default void animateFadeOut(Node n, double dura) {
		if ( !(n instanceof HomepagelSP)) {
			
			Sound.setFile(2);
			Sound.play();
		}
		
		FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(dura), n);
		fadeOutTransition.setFromValue(1.0);
		fadeOutTransition.setToValue(0.0);
		fadeOutTransition.setOnFinished((e) -> {
			n.setVisible(false);
		});
		fadeOutTransition.play();
	}
	
	
}
