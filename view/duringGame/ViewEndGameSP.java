package view.duringGame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import model.deck.Category;
import model.player.Member;
import model.player.Player;
import view.toolsView.AnimationInterface;
import view.toolsView.WindowData;

public class ViewEndGameSP extends StackPane implements WindowData, AnimationInterface {

	private Label lblEndGame;
	private ImageView bGround;
	private Button returnMain;
	private List<HBox> categoriesPlayers;
	private List<Label> playersLbl;
	private TableView<Player> tab;
	private List<Player> players;

	
	
	public ViewEndGameSP() {
	}
	
	public void remplissageEnd(List<Player> p) {
		setPlayers(p);
		if ( categoriesPlayers != null) {
			
			categoriesPlayers=null;
			playersLbl=null;
			this.getChildren().clear();
		}
		sortPlayers();	
		
		VBox container = new VBox(PADDING, getTab());
		
		AnchorPane ap = new AnchorPane(getReturnMain(), getLblEndGame(), container);
		
		AnchorPane.setRightAnchor(container, WIDTH_SCREEN / 6.7); // 300
		AnchorPane.setBottomAnchor(container, HEIGHT_SCREEN / 3.0857142857142857142857142857143); // 350
		
		AnchorPane.setRightAnchor(getReturnMain(), PADDING);
		AnchorPane.setBottomAnchor(getReturnMain(), PADDING);
		
		AnchorPane.setRightAnchor(getLblEndGame(), WIDTH_SCREEN / 2.4 );
		AnchorPane.setTopAnchor(getLblEndGame(), PADDING);
		
		this.getChildren().addAll(getBack(), ap);
		if ( !this.isVisible() ) {
			animateFadeIn(this, DURATION_FADE);
		}
	}
	
	public TableView<Player> getTab() {
		tab = new TableView<Player>();
		
		// dimension du tableau
		tab.setMaxWidth(TABLE_END_WIDTH);
		tab.setMinWidth(TABLE_END_WIDTH);
		tab.setMaxHeight(TABLE_END_HEIGHT);
		
		// on utilise toutes la place disponible du tableau
		tab.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		// on recupere les donnees qu'on dispose dans le tableau, ceci permet de tenir le tableView a jour
		ObservableList<Player>data=FXCollections.observableArrayList(players);
		tab.setItems(data);
		
		// creation de la colonne reprenant le surnom jhoueur
		TableColumn<Player, String> nickNameTC =  new TableColumn<Player, String>("Nickname");
		nickNameTC.setCellValueFactory(new PropertyValueFactory<>("nickName"));
		nickNameTC.setMaxWidth(COLUMN_WIDTH_NICKNAME);
		
		// // creation de la colonne reprenant la hbox categories réussie des joueurs
		TableColumn<Player, HBox> catTC =  new TableColumn<Player, HBox>("Categories successfull");
		catTC.setMaxWidth(COLUMN_WIDTH_CATEGORIES);
		catTC.setMinWidth(COLUMN_WIDTH_CATEGORIES);
		catTC.setCellValueFactory(new Callback<CellDataFeatures<Player, HBox>, ObservableValue<HBox>>() {	
	        @Override
	        public ObservableValue<HBox> call(CellDataFeatures<Player, HBox> param) {
	        	return new SimpleObjectProperty<HBox>(  getCategoriesPlayers().get(players.indexOf(param.getValue())));
	        }
	      });
		
		// creation de la colonne reprenant le portefeuille du joueur
		TableColumn<Player, String> poTC =  new TableColumn<Player, String>("Wallet");
		poTC.setMaxWidth(COLUMN_WIDTH_WALLET);
		poTC.setMinWidth(COLUMN_WIDTH_WALLET);
		poTC.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player,String>, ObservableValue<String>>() {
			// seul les membres ont un portefeuille hors jeu, donc on fait la distinction
			@Override
			public ObservableValue<String> call(CellDataFeatures<Player,String> param) {
				if ( param.getValue() instanceof Member )
					return new SimpleStringProperty(param.getValue().getTransaction() +"");
				return new SimpleStringProperty("/");
			}
		});
		
		// on ajoute tout au tableau
		tab.getColumns().addAll(nickNameTC, catTC, poTC );
		return tab;
	}
	
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	public Button getReturnMain() {
		if ( returnMain == null ) {
			returnMain = new Button("Go to main");
		}
		return returnMain;
	}
	
	public void sortPlayers() {
		players.sort(new Comparator<Player>() {

            @Override
            public int compare(Player o1, Player o2) {
                if ( o1.getSuccessfulCategories().size() == o2.getSuccessfulCategories().size())
                    return 0;
                if ( o1.getSuccessfulCategories().size() >  o2.getSuccessfulCategories().size() )
                    return -1;
                else 
                    return 1;
            }
        });
	}
	
	public List<HBox> getCategoriesPlayers() {
		if ( categoriesPlayers == null) {
			categoriesPlayers = new ArrayList<HBox>();
			for ( Player p : players ) {
				HBox hb = new HBox(PADDING);
				for ( Category c : Category.values() ) {
					Rectangle rg = new Rectangle(SIDE_SQUARE_CATEGORY , SIDE_SQUARE_CATEGORY);
					rg.setFill(c.getColor());
					rg.setStyle("-fx-stroke: black; -fx-stroke-width: 3;");
					
					if ( !p.getSuccessfulCategories().contains(c))
						rg.setOpacity(0.3);
					hb.getChildren().add(rg);
				}
				categoriesPlayers.add(hb);
			}
		}
		return categoriesPlayers;
	}
	
	public List<Label> getPlayersLbl() {
		if ( playersLbl == null) {
			playersLbl = new ArrayList<Label>();
			for ( Player p : players ) {
				Label lbl = new Label(p.getNickName());
				lbl.getStyleClass().add("timer_duringGame");
				playersLbl.add(lbl);
			}
		}
		return playersLbl;
	}
	
	 /**
     *  renvoit et instancie si besoin un element de classe Background ayant pour image le fond prevu pour le jeu
     * @return Background
     */
    public ImageView getBack() {
    	if ( bGround == null ) {
    		
		    bGround = new ImageView( new Image("./images/endGame/e1.jpg"));
			bGround.setFitWidth(WIDTH_SCREEN);
			bGround.setFitHeight(HEIGHT_SCREEN);
    	}
    	return bGround;
    }

    public Label getLblEndGame() {
    	if (lblEndGame == null) {
    		lblEndGame = new Label("End Game");
    		lblEndGame.getStyleClass().add("lbl_titre");
    	}
    	return lblEndGame;
    }
}