package view.playerView;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.player.Player;
import view.toolsView.WindowData;

public class SkinViewIV extends ImageView implements WindowData{
	
    private static ListSkinImg SKINS = new ListSkinImg();
    private Player p;
    private SkinImg skin;
    private boolean good;
    
    
    
    /**
     * Constructeur - respresente visuellement un joueur
     * @param p Player
     */
    public SkinViewIV(Player p){
    	
    	this.p = p; 
        this.setFitHeight(HEIGHT_SCREEN/8);
        this.setFitWidth(WIDTH_SCREEN/8);
        this.setPreserveRatio(true);
    }
    
    /**
     * donne la valeur a l'attribut skin en fonction du skin se trouvant à l'index spécifie en argument
     * @param index int
     */
    public void setSkinIndex(int index) {
		this.skin = SKINS.getSkinIndex(index);
		this.setImage(skin.getImg(0));
	}
   
    /**
     * melange la liste de skin
     */
    public static void shuffleSkins() {
    	SKINS.shuffleList();
    }
    
    /**
     * renvoit l'instance Player de la class
     * @return Player
     */
    public Player getPlayer() {
    	return p;
    }
    
    /**
     * gere le déplacement et animation du skin joueur
     */
    public void up() {
    	
    	this.getPlayer().setPosLadder(this.getPlayer().getPosLadder() + 1);  
    	
		if (this.getPlayer().getPosLadder() == Player.END_POSITION ) 
    		setGood(true);
    	
    	if ( isGood()) {	
            down();
            setGood(false);
            this.getPlayer().setPosLadder(0);
    	}
    	else {
    		TranslateTransition tt = new TranslateTransition(Duration.seconds(1), this);
    		

    		tt.setByY(-HEIGHT_SCREEN/27);    		
    		SequentialTransition st = new SequentialTransition();
    		
    		for (int i = 0 ; i <= 3 ; i++ ) {
    			
    			FadeTransition fot = new FadeTransition(Duration.seconds(0.25), this);
    			fot.setFromValue(1.);
    			fot.setToValue(1.);
    			fot.setOnFinished((event) ->{
    				if (this.getImage().getUrl().equals(skin.getImg(0).getUrl())) {
    					this.setImage(skin.getImg(1));
    				}
    				else
    					this.setImage(skin.getImg(0));
    			});
    			
    			st.getChildren().addAll(fot);
    		}
    		ParallelTransition pt = new ParallelTransition(tt,st);
    		pt.play();
    		
    		
    	}
    	
    	//System.out.println(isGood());
        
    }
    
    
    public void down() {
        SequentialTransition st = new SequentialTransition(); 
        
        TranslateTransition tt = new TranslateTransition(Duration.seconds(1), this);
        tt.setByX(-WIDTH_SCREEN/21);
        st.getChildren().add(tt);
        
        ParallelTransition pt = new ParallelTransition();
        TranslateTransition down= new TranslateTransition(Duration.seconds(4), this);
        down.setToY(270);
        pt.getChildren().add(down);
        
        for (int i = 0 ; i <= 16 ; i++ ) {
            
            FadeTransition fot = new FadeTransition(Duration.seconds(0.25), this);
            fot.setFromValue(1.);
            fot.setToValue(1.);
            fot.setOnFinished((event) ->{
                
                if (this.getImage().getUrl().equals(skin.getImg(0).getUrl())) {
                    this.setImage(skin.getImg(1));
                }
                else
                    this.setImage(skin.getImg(0));
            });

            pt.getChildren().add(fot);
        }
        st.getChildren().add(pt);
        
        TranslateTransition ttt = new TranslateTransition(Duration.seconds(1), this);
        ttt.setByX(WIDTH_SCREEN/21);
        
        st.getChildren().add(ttt);
        st.play();
    }
    
    public boolean isGood() {
		return good;
	}
    
    public void setGood(boolean good) {
		this.good = good;
	}
}