package miniGames.rushSpatial;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import miniGames.MiniGameAbstractSP;

public class RushSpatial extends MiniGameAbstractSP{

	private ImageView back, moon, planet1, planet2, rocket, satellite, star;
	private List<ImageView>ennemies;
	private AnimationTimer animation;
	private Label timeLbl, textEnd;
	private VBox entry, end;
	
	private int chronoTime;
	private double speed = 25.;
	private double largeur = 40.;
	
	private String entryText = 
			  "Move \r\n"
			+ "  z to move up\r\n"
			+ "  s to go down\r\n"
			+ "  q to move left\r\n"
			+ "  d to move right\r\n"
			+ "\r\n"
			+ "#faulty reactor noise#\r\n"
			+ "MAYDAY MAYDAY MAYDAY, \r\n"
			+ "this is Appolo_A02, Appolo_A02, Appolo_A02\r\n"
			+ "Position: Andromeda Galaxy\r\n"
			+ "Reactors are defective\r\n"
			+ "Request immediate landing\r\n"
			+ "#Faulty engine noise#\r\n"
			+ "\r\n"
			+ "MAYDAY Appolo_A02\r\n"
			+ "This is Ishanahar\r\n"
			+ "Copy Appolo_A02\r\n"
			+ "Hold for 30 seconds \r\n"
			+ "Time to prepare for landing \r\n"
			+ "Over";
	
	
	
	public RushSpatial() {
		chronoTime = getChrono();
		this.setMinWidth(WIDTH_SCREEN);
		this.setMaxWidth(WIDTH_SCREEN);
		this.setMinHeight(HEIGHT_SCREEN);
		this.setMaxHeight(HEIGHT_SCREEN);

		VBox timerVB = new VBox(getTimeLbl());
		timerVB.setAlignment(Pos.TOP_RIGHT);
		StackPane.setAlignment(timerVB, Pos.CENTER);
		this.getChildren().addAll(getBack() ,timerVB,getEntry());
		
		ennemies = new ArrayList<ImageView>();
		ennemies.add(getPlanet1());
		ennemies.add(getPlanet2());		
		ennemies.add(getMoon());
		ennemies.add(getSatellite());
		ennemies.add(getStar());
		
	}
	
	public void launchGame() {
		this.getChildren().addAll(getRocket(), getPlanet1(), getMoon(), getPlanet2(),getStar(), getSatellite(), getEnd() );

		
		getAnimation().start();	
	}
	
	public VBox getEntry() {
		if ( entry == null) {
			
			Label textEntry = new Label(entryText);
			textEntry.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(1.), null)));
			textEntry.setPadding(new Insets(15.));
			
			Button startBtn = new Button("Start");
			startBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					getEntry().setVisible(false);
					launchGame();
				}
			});
			
			entry = new VBox(PADDING,textEntry, startBtn);
			entry.setAlignment(Pos.CENTER);
			entry.getStyleClass().add("mini_css");
		}
		return entry;
	}
	
	public VBox getEnd() {
		if (end == null) {
			textEnd = new Label("test");
			textEnd.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(1.), null)));
			textEnd.setPadding(new Insets(15.));
			
			//finishBtn = new Button("End");
			
			end = new VBox(PADDING, textEnd, super.getFinishBtn());
			end.getStyleClass().add("alert_css");
			end.setAlignment(Pos.CENTER);
			end.setVisible(false);
		}
		return end;
	}
	
	public Label getTimeLbl() {
		if ( timeLbl == null ) {
			timeLbl = new Label();
//			timeLbl.setBackground(new Background(new BackgroundFill(Color.BLUE, new CornerRadii(1.), null)));
//			timeLbl.setPadding(new Insets(15.));
			timeLbl.setFocusTraversable(true);
			timeLbl.getStyleClass().add("lbl_titre");
			
		}
		return timeLbl;
	}
	
	
	public AnimationTimer getAnimation() {
		if ( animation == null) {
			animation = new AnimationTimer() {
				Long lastValue = 0L;
				boolean finish = false;
				@Override
				public void handle(long now) {
					if ( now - lastValue >= 1000000000) {
						lastValue = now;
						getTimeLbl().setText(--chronoTime + "");
					}
					for (ImageView iv : ennemies) {
						if (getRocket().getTranslateX() - largeur <= iv.getTranslateX()  && getRocket().getTranslateX() + largeur >= iv.getTranslateX() ){
					    	
							if (getRocket().getTranslateY() - largeur <= iv.getTranslateY()  && getRocket().getTranslateY() + largeur >= iv.getTranslateY() ) {
								finish = true;
							}
						}
					}
					if ( chronoTime == 0 || finish == true ) {
						getAnimation().stop();
						for (ImageView iv2 : ennemies) {
							iv2.setVisible(false);
						}
						
						if (chronoTime == 0) 
							textEnd.setText("Successful mission ! You win " + (30  * 10) );
						else 
							textEnd.setText("Failed mission ! You win " + ((30- chronoTime) * 10) );
						
						setPo((30-chronoTime)*10);
						getEnd().setVisible(true);
					}
				}
			};
		}
		return animation;
	}
	
	public void setPo(int po) {
		super.setPo(po);
	}
	
	
	public ImageView getBack() {
		if ( back == null ) {
			Image img = new Image("./images/miniGames/rushSpatial/backgroundStar.jpg");
//			Image img = new Image("./images/miniGames/rushSpatial/moon.png");
		    back = new ImageView(img);
		    TranslateTransition tt = new TranslateTransition(Duration.seconds(300), back);
		    tt.setFromY(0);
		    tt.setToY(-2000);
		    tt.setInterpolator(Interpolator.LINEAR);
		    tt.setCycleCount(Animation.INDEFINITE);
		    tt.play();
		}
		return back;
	}
	
	public ImageView getMoon() {
		if ( moon == null ) {
			Image img = new Image("./images/miniGames/rushSpatial/moon.png");
			moon = new ImageView(img);
			moon.setFocusTraversable(true);
			TranslateTransition tt = moveEnnemies(moon, 500 , 500);
			tt.play();
			
		}
		return moon;
	}
	
	public TranslateTransition moveEnnemies(ImageView iv, double x, double y) {
		TranslateTransition tt = new TranslateTransition(Duration.seconds(1. + new Random().nextDouble()), iv);
		
		tt.setFromY(y);
//		tt.setToY(-500. + new Random().nextInt(1000));
		tt.setToY((-HEIGHT_SCREEN /2) + new Random().nextInt((int)HEIGHT_SCREEN));
		
		tt.setFromX(x);
		tt.setToX((-WIDTH_SCREEN /2) + new Random().nextInt((int)WIDTH_SCREEN));
	    
	    tt.setInterpolator(Interpolator.LINEAR);
	    tt.play();
	    tt.setOnFinished(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				moveEnnemies(iv, iv.getTranslateX(), iv.getTranslateY());
			}
		});
	    
	    return tt;
	}
	
	public ImageView getPlanet1() {
		if ( planet1 == null ) {
			Image img = new Image("./images/miniGames/rushSpatial/planet1.png");
			planet1 = new ImageView(img);
			planet1.setFocusTraversable(true);
			TranslateTransition tt = moveEnnemies(planet1, WIDTH_SCREEN , 250);
			tt.play();
		}
		return planet1;
	}
	public ImageView getPlanet2() {
		if ( planet2 == null ) {
			Image img = new Image("./images/miniGames/rushSpatial/planet2.png");
			planet2 = new ImageView(img);
			planet2.setFocusTraversable(true);
			TranslateTransition tt = moveEnnemies(planet2, -WIDTH_SCREEN , 100);
			tt.play();
		}
		return planet2;
	}
	public ImageView getRocket() {
		if ( rocket == null ) {
			Image img = new Image("./images/miniGames/rushSpatial/rocket.png");
			rocket = new ImageView(img);
			//rocket.setTranslateX(50.);
		//	rocket.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			rocket.setFocusTraversable(true);
			TranslateTransition tt = new TranslateTransition(Duration.seconds(0.05), rocket);
			 
			rocket.setOnKeyTyped( new EventHandler<KeyEvent>() {
			    @Override 
			    public void handle(KeyEvent event) { 
			        String c =event.getCharacter(); 
			        switch (c) { 
			        	case " " :
			        		break;
			         	case "z" : 
			         		if ( rocket.getTranslateY() > (- HEIGHT_SCREEN / 2 ) + largeur ) { // -500
				        		rocket.setRotate(0); 
			         			tt.setFromY(rocket.getTranslateY());
			         			tt.setToY(rocket.getTranslateY() - speed);
			         			
			         			tt.setFromX(rocket.getTranslateX());
			         			tt.setToX(rocket.getTranslateX());
			         			
			         			tt.play();
			         		} else 
			         			rocket.setRotate(180);
			         		
			        		break; 
			        	case "s" : 
			        		if ( rocket.getTranslateY() < ( HEIGHT_SCREEN / 2 ) - largeur) { // 500
				        		rocket.setRotate(180);
				        		tt.setFromY(rocket.getTranslateY());
			         			tt.setToY(rocket.getTranslateY() + speed);
			         			
			         			tt.setFromX(rocket.getTranslateX());
			         			tt.setToX(rocket.getTranslateX());
			         			
			         			tt.play();
			        		} else 
			        			rocket.setRotate(0);
			        		break;
			        	case "q" :
			        		if ( rocket.getTranslateX() >  ( - WIDTH_SCREEN / 2 ) + largeur ) { // -965
			        			rocket.setRotate(270);
			        			tt.setFromX(rocket.getTranslateX());
			         			tt.setToX(rocket.getTranslateX() - speed);
			         			
			         			tt.setFromY(rocket.getTranslateY());
			         			tt.setToY(rocket.getTranslateY());
			         			
			         			tt.play();
			        		}
			        		else 
			        			rocket.setRotate(90);
			        		break;
			        	case "d" :
			        		if ( rocket.getTranslateX() <  (WIDTH_SCREEN / 2 ) - largeur ) {
				        		rocket.setRotate(90);
				        		tt.setFromX(rocket.getTranslateX());
			         			tt.setToX(rocket.getTranslateX() + speed);
			         			
			         			tt.setFromY(rocket.getTranslateY());
			         			tt.setToY(rocket.getTranslateY());
			         			
			         			tt.play();
			        		}else 
			        			rocket.setRotate(270);
			        		break;
			        	default :
			        		break;
			        }
			    }
			    
			});
		}
		return rocket;
	}
	public ImageView getSatellite() {
		if ( satellite == null ) {
			Image img = new Image("./images/miniGames/rushSpatial/satellite.png");
			satellite = new ImageView(img);
			satellite.setFocusTraversable(true);
			TranslateTransition tt = moveEnnemies(satellite, WIDTH_SCREEN , 0);
			tt.play();
		}
		return satellite;
	}
	public ImageView getStar() {
		if ( star == null ) {
			Image img = new Image("./images/miniGames/rushSpatial/star.png");
			star = new ImageView(img);
			star.setFocusTraversable(true);
			TranslateTransition tt = moveEnnemies(star, -WIDTH_SCREEN , 0);
			tt.play();
		}
		return star;
	}
	
	
}
