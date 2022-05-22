package view.duringGame;

import java.text.DecimalFormat;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import view.toolsView.AnimationInterface;
import view.toolsView.WindowData;

public class TimerGameHB extends VBox implements WindowData, AnimationInterface{

	private int sec = 0,min = 0,hour = 0;
	private Label timer;
	private KeyFrame kfFrame;
	private Timeline timeline;
	private boolean isPause;
	
	private StackPane timerSP;
	private ImageView pauseIV;

	
	/**
	 * Classe reprenant le timer, label du joueur actif et la fonction pause
	 */
	public TimerGameHB() {
		
		this.getStyleClass().add("timer_duringGame");
		
		this.isPause = false;
		
		HBox hb = new HBox(PADDING, getTimerViewSP(), getPauseIV());
		
		this.setSpacing(PADDING);
		this.getChildren().addAll(hb/*, getPlayerActiverSP()*/);
	}
	
	/**
	 * donne la valeur recue en arguement (True | false) à l'attribut "isPause"
	 * @param isPause
	 */
	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}
	
	/**
	 * renvoit la valeur booleene de l'attribut "isPause"
	 * @return boolean
	 */
	public boolean isPause() {
		return isPause;
	}
	
	
	/**
	 * renvoit et instancie si besoin un element de classe ImageView contenant l'image "pause"
	 * @return ImageView
	 */
	public ImageView getPauseIV() {
		if ( pauseIV == null ) {
			pauseIV = new ImageView(new Image("./images/timer/pause.png"));
			pauseIV.getStyleClass().add("timer_duringGame");
			pauseIV.setFitHeight(50);
	        pauseIV.setFitWidth(50);
	        animateClick(pauseIV);
	        pauseIV.setPreserveRatio(true);
		}
		return pauseIV;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe StackPane regroupant l'arriere plan du timer et le timer
	 * @return StackPane
	 */
	public StackPane getTimerViewSP() {
		if ( timerSP == null ) {
			ImageView iv = new ImageView(new Image("./images/timer/backTimer.png"));
			iv.setFitHeight(50);
	        iv.setFitWidth(150);
	        iv.setPreserveRatio(true);
	        
			timerSP = new StackPane();
			timerSP.getChildren().addAll(iv,getTimer());
		}
		return timerSP;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Label contenant le chrono mis a jour chaque seconde sous format 00:00:00
	 * @return Label
	 */
	public Label getTimer() {
		if ( timer == null) {
			timer = new Label();
			timer.getStyleClass().add("timer_duringGame");
			// evenement a chaque seconde pour mettre a jour le label
			kfFrame = new KeyFrame(Duration.seconds(1.), new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					setTimer();
					timer.setText(new DecimalFormat("00").format(hour)+":"+new DecimalFormat("00").format(min)+":"+new DecimalFormat("00").format(sec));
				}
			});
			timeline = new Timeline(kfFrame);
			timeline.setCycleCount(Animation.INDEFINITE);
			timeline.play();
		}
		return timer;
	}
	
		
	/**
	 * methode du chrono permettant avec l'incrementation des secondes, passe des minutes / heures
	 */
	public void setTimer() {
		sec++;
		if ( sec == 60 ) {
			min++;
			sec = 0 ;
		}
		if ( min == 60 ) {
			hour++;
			min = 0;
		}
	}
	
	
	/**
	 * methode qui mets le timer en pause
	 */
	public void pauseTimer() {
		timeline.pause();
	}
	/**
	 * methode qui redemarre le timer la il s'etais arreter
	 */
	public void runTimer() {
		timeline.play();
	}
}


