package view.duringGame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
import model.deck.Category;
import model.player.Player;
import view.playerView.SkinViewIV;
import view.toolsView.WindowData;

public class GamePanelSP extends StackPane implements WindowData{

    private Background bGround;
    
    List<ImageView> ladders;
    List<SkinViewIV> playersViews;
    
    List<FlowPane> lightCategories;
    List<StackPane> consoles;
    
    List<Player> players;
    
   // ImageView lum;
    private Label playerActive;
	private StackPane playerActiverSP;
	
    /**
     * Constructeur vide, la classe sera decore quand on connaitra le nbre de joueur
     */
    public GamePanelSP() {
    }
    
    /**
     * Methode qui decore la classe, avec le fond, les echelles et skin joueur
     * Utilise un algorithm qui placera les échelles, qu'importe le nombre, de maniere optimum sur toutes la largeur de la fenetre 
     */
    public void decorate() {
    	
    	ladders = new ArrayList<ImageView>();
        int nbPlayers = players.size();
        
        // on place le background
        this.setBackground(getBack());
        
        // on définit la largeur entre deux échelles 
        double widthPerPlayer =  (WIDTH_SCREEN/nbPlayers);
        
        // pour chaque joueur, 
        for(int i=1; i<= nbPlayers; i++) {
        	// on crée l'echelle
            ImageView img = createLadder();
            ladders.add(img);
            
            // on pose l'echelle en fonction du nombre de joueur et de la largeur de l'ecran
            if(nbPlayers%2 != 0) {
                if(i%2==0) {
                    img.setTranslateX((widthPerPlayer*(i))/2);
                    
                }else {
                    img.setTranslateX((widthPerPlayer*(-i+1))/2);
                }
            }else {
                if(i%2==0) {
                    img.setTranslateX((widthPerPlayer*(-i+1))/2);
                }else {
                    img.setTranslateX((widthPerPlayer*(i))/2);
                }
            }
            
            // on définit l'axe Y des echelles
            img.setTranslateY(HEIGHT_SCREEN/8);
            this.getChildren().addAll(img);
        }
        
        // tri de la list echelles pour la plus gauche en 1ere et ainsi de suite 
        // pour que cela corresponde a la liste des joueurs
        ladders.sort(new Comparator<ImageView>() {

            @Override
            public int compare(ImageView o1, ImageView o2) {
                if ( o1.getTranslateX() == o2.getTranslateX())
                    return 0;
                if ( o1.getTranslateX() < o2.getTranslateX())
                    return -1;
                else 
                    return 1;
            }
        });
        
        
        // on recupère la liste des skins qu'on melange
        playersViews = new ArrayList<SkinViewIV>();
        SkinViewIV perso;
        SkinViewIV.shuffleSkins();
        
        // pour chaque joueur
        for (int j = 0 ; j < players.size() ; j++) {
        	
        	// on lui attribue un skin
        	perso = new SkinViewIV(players.get(j));
        	perso.setSkinIndex(j);
        	playersViews.add(perso);
        	
        	// on lui attribue une console (categorie reussie) qu'on positionne
        	StackPane cons = getConsole().get(j);
            cons.setTranslateX(ladders.get(j).getTranslateX() + 95);
            cons.setTranslateY(-88);
            cons.setViewOrder(ladders.get(j).getViewOrder() + 1);
            
            perso.setTranslateX(ladders.get(j).getTranslateX());
            perso.setTranslateY(270);
            
            this.getChildren().addAll(cons,perso);
            
            if (j == 0) {
            	 this.getChildren().addAll(getPlayerActiverSP());
            	 getPlayerActiverSP().setTranslateX(ladders.get(j).getTranslateX());
            	 getPlayerActiverSP().setTranslateY(ladders.get(j).getTranslateY() - (HEIGHT_SCREEN / 3.08) ); // 350
            }
        }
    }
    
    
    /**
     * donne la valeur recue en argument a l'attribut players
     * @param players List<Player>
     */
    public void setPlayers(List<Player> players) {
		this.players = players;
	}
    
    /**
     * cree une List<StackPane> contenant l'image de la console et la creation de cercle correspondant au nombre de categrie
     * @return List<StackPane>
     */
    public List<StackPane> getConsole() {
    	if (lightCategories == null ) {
	    	consoles = new ArrayList<StackPane>();
	    	lightCategories = new ArrayList<FlowPane>();
	    	
	    	// pour chaque joueur
	    	for (@SuppressWarnings("unused") Player player : players) {
				
	    		// on cree un flowpane qui contiendra les cercles des categories
		    	FlowPane btnConsole = new FlowPane();
				btnConsole.setAlignment(Pos.CENTER);
				btnConsole.setMaxWidth(60.);
				btnConsole.setPadding(new javafx.geometry.Insets(0, 0, 29, 0));
				lightCategories.add(btnConsole);
				
//				pour chaque categorie, on cree un cercle avec la couleur de la cat
				for (Category c : Category.values()) {
					Circle ci = new Circle(4., c.getColor());
					
					ci.setFocusTraversable(true);
					ci.setStroke(Color.WHITE);
					ci.setStrokeWidth(1.);
					ci.setStrokeType(StrokeType.INSIDE);
					ci.setOpacity(0.0);
					
					btnConsole.getChildren().add(ci);
					FlowPane.setMargin(ci, new javafx.geometry.Insets(4.));
					
				} 
	    	}
	    	
	    	// on ajoute la la console 
	    	for ( int i = 0 ; i < lightCategories.size() ; i++ ) {
	    		
				Image img = new Image("./images/gamePanel/console.png");
		    	ImageView imageView = new ImageView(img);
		    	StackPane sp = new StackPane( imageView , lightCategories.get(i));
		    	consoles.add(sp);
	    	}
    	}
    	return consoles;
    }
    
    /**
     * Change l'opacite a 1 du cercle correspondant a l'index
     * @param index int correspondant a l'emplacement du cercle
     * @param c Category 
     */
    public void changeOpacityCategory(int index, Category c) {
    	// recherche depuis le parent
    	FlowPane fp = lightCategories.get(index);
    	for (Node n : ((Pane)fp).getChildren()) {
    		if ( n instanceof Circle && ((Circle)n).getFill().equals(c.getColor()) ) {
    			((Circle)n).setOpacity(1.);   
    		}
    	}
	}
  
    /**
     * Renvoit le SkinView du joueur passer en argument
     * @param p Player
     * @return SkinView
     */
    public SkinViewIV getSkinViewByPlayer(Player p) {
    	for (SkinViewIV iv : playersViews) {
			if ( iv.getPlayer().equals(p)  ) 
				return iv;
    	}
    	return null;
    }
    
    
    /**
     *  renvoit et instancie si besoin un element de classe Background ayant pour image le fond prevu pour le jeu
     * @return Background
     */
    public Background getBack() {
    	if ( bGround == null ) {
    		
		    Image img = new Image("./images/gamePanel/fond11.jpg");
		    BackgroundImage bImg = new BackgroundImage(img,
		            BackgroundRepeat.NO_REPEAT,
		            BackgroundRepeat.NO_REPEAT,
		            BackgroundPosition.DEFAULT,
		            new BackgroundSize(WIDTH_SCREEN, HEIGHT_SCREEN, isDisabled(), isDisable(), isCache(), isHover()));
		    bGround = new Background(bImg);
    	}
    	return bGround;
    }
    
    /**
     * cree une img d'echelle pour le plateau
     * @return ImageView
     */
    public ImageView createLadder() {
        Image img = new Image("./images/gamePanel/EchelleLiane.png");
        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(HEIGHT_SCREEN/3);
        imageView.setFitWidth(WIDTH_SCREEN/3);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    
    public Label getPlayerActive() {
		if ( playerActive == null ) {
			playerActive = new Label();
		}
		return playerActive;
	}
	
	/**
	 * donne la valeur textuelle recue en argument au label prevu pour contenir le surnom du joueur actif + animation de changement de joueur
	 * @param nickName
	 * @param p1 Player vers qui on va
	 * @param p2 Player de qui on demare
	 */
	public void setPlayerActive(String nickName, Player p1, Player p2) {
		playerActive.setText(nickName);
		if ( playersViews != null ) {
			
		double from = 0, to = 0;
		for (SkinViewIV sv : playersViews ) {
			if ( sv.getPlayer().equals(p2) )
				from = sv.getTranslateX();
			if ( sv.getPlayer().equals(p1) )
				to = sv.getTranslateX();
		}
		TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), getPlayerActiverSP());
		transition.setFromX(from);
		transition.setToX(to);
		transition.play();
		}
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Stackpane regroupant le label et arriere plan du joueur actif
	 * @return StackPane
	 */
	public StackPane getPlayerActiverSP() {
		if ( playerActiverSP == null) {
			ImageView iv = new ImageView(new Image("./images/timer/backTimer.png"));
			iv.setFitHeight(75);
	        iv.setPreserveRatio(true);
	        
	        playerActive = new Label();
	        playerActive.getStyleClass().add("timer_duringGame_player");
	        
	        playerActiverSP = new StackPane(iv, playerActive);
	        
		}
		return playerActiverSP;
	}
}