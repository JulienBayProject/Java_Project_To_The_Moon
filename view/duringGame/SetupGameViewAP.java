package view.duringGame;

import java.util.List;

import exception.NickNameLongerException;
import exception.PlayerAlreadyPresentException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.game.SetupGame;
import model.player.GestionMember;
import model.player.Guest;
import model.player.Member;
import model.player.Player;
import model.player.TransactionWalletPo;
import persistence.Persistence;
import view.connection.ConnexionSignVB;
import view.toolsView.AnimationInterface;
import view.toolsView.WindowData;

public class SetupGameViewAP extends AnchorPane implements WindowData, AnimationInterface{
	
	private SetupGame setupGame;
	private ConnexionSignVB connect;
	
	private VBox containerVB;
	
	private Button addGuessBtn;
	private Button addMemberBtn;
	private Button delMemberBtn;
	private Button returnPrevious;
	
	private Button startBtn;
	
	private TableView<Player> playerTab;

	private Background bGround;
	private Label titleLbl;
	
	
	/**
	 * Constructeur cree la fenetre on ajoute, retire des joueurs a la liste joueur
	 */
	public SetupGameViewAP() {
		setupGame = new SetupGame();
		this.setBackground(getBack());
		
		this.getChildren().addAll(getTitleLbl(), getPlayerTab(), getConnect(), getContainerVB(), getStartBtn(), getReturnPrevious());
		
		AnchorPane.setLeftAnchor(getTitleLbl(), getTitleLbl().getWidth() / 2  );
		AnchorPane.setTopAnchor(getTitleLbl(), PADDING);
		
		AnchorPane.setLeftAnchor(getPlayerTab(), (WIDTH_SCREEN / 2) - ( TABLEVIEW_WIDTH / 2 ) );
		AnchorPane.setTopAnchor(getPlayerTab(), HEIGHT_SCREEN / 4);
		
		AnchorPane.setBottomAnchor(getConnect(), PADDING);
		AnchorPane.setLeftAnchor(getConnect(), getConnect().getWidth() / 2 );
		
		AnchorPane.setBottomAnchor(getContainerVB(), PADDING);
		AnchorPane.setLeftAnchor(getContainerVB(), getContainerVB().getWidth() / 2 );
		
		AnchorPane.setLeftAnchor(getReturnPrevious(), PADDING);
		AnchorPane.setBottomAnchor(getReturnPrevious(), PADDING);

		AnchorPane.setRightAnchor(getStartBtn(), PADDING);
		AnchorPane.setBottomAnchor(getStartBtn(), PADDING);
		
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe ConnexionSign
	 * @return ConnexionSign
	 */
	public ConnexionSignVB getConnect() {
		if ( connect == null ) {
			connect = new ConnexionSignVB();
			connect.setFocusTraversable(true);
			connect.setMinWidth(WIDTH_SCREEN);
			connect.setVisible(false);
			
			// on retire les elements qui nous interesse pas ici
			connect.getAlert().setVisible(false);
			connect.getPreviousMenu().setVisible(false);
			connect.getBtnSubscribe().setVisible(false);
			
			
			connect.setId("connection_setup_css");
			
			connect.getBtnConnexion().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// chargement de la liste des membres
					GestionMember members = Persistence.readingJson("databaseUser.json", GestionMember.class);
					for (Member m : members.getMembers()) {

						// on verifie si le membre est present dans la liste
						if(connect.getTxtUser().getText().equals(m.getNickName()) && connect.getPswUser().getText().equals(m.getPassword())) {
							
							// on avertit de la reussite
							connect.getAlert().writeAlert("Welcome !","The authentification was succesful","Continue");
							
							connect.getAlert().getOkBtn().setOnAction((e) -> {
								animateNodeViewOut(connect.getAlert(), DURA_ALERT_OUT);
								try {
									// une fois que le membre a confirmer, on l'ajoute dans la liste joueur et tableView
									// et on vide les textfield
									m.setTransaction(new TransactionWalletPo(m));
									if ( setupGame.addPlayer(m)) {
										getPlayerTab().getItems().add(m);
										animateNodeViewOut(getConnect(), DURA_ALERT_OUT);
										getConnect().getTxtUser().clear();
										getConnect().getPswUser().clear();
										getAddMemberBtn().setText("Member");
									}
								} catch (PlayerAlreadyPresentException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							});
							break;
						// si pas present on previent et on referme la fenetre de connexion
						} else {
							connect.getAlert().writeAlert("Error !","The authentification wasn't succesful","Retry");
							
							connect.getAlert().getOkBtn().setOnAction((e) -> {
								animateNodeViewOut(getConnect(), DURA_ALERT_OUT);
								getConnect().getTxtUser().clear();
								getConnect().getPswUser().clear();
								getAddMemberBtn().setText("Member");
							});
						}
					}
				}
			});
		}
		return connect;
	}
	
	/**
	 * renvoie l'instance de SetupGame
	 * @return SetupGame
	 */
	public SetupGame getSetupGame() {
		return setupGame;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Label ayant pour valeur Textuelle "Create Game"
	 * @return Label
	 */
	public Label getTitleLbl() {
		if ( titleLbl == null) {
			titleLbl = new Label("Create Game");
			titleLbl.setFocusTraversable(true);
			titleLbl.setMinWidth(WIDTH_SCREEN);
			titleLbl.setAlignment(Pos.CENTER);
			titleLbl.getStyleClass().add("lbl_titre");
		}
		return titleLbl;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Button prevu pour revenir au main menu
	 * @return
	 */
	public Button getReturnPrevious() {
		if(returnPrevious==null) {
			returnPrevious = new Button("Previous menu");
			animateClick(returnPrevious);
			returnPrevious.setFocusTraversable(true);
		}
		return returnPrevious;
	}

	/**
	 * renvoit et instancie si besoin un element de classe VBox contenant le tableView ainsi que
	 * les boutons qui ajoute un invite, ajoute un membre ou supprime un joueur de la liste present dans la tableview
	 * @return VBox
	 */
	public VBox getContainerVB() {
		if (containerVB == null ) {
			containerVB = new VBox(0., getPlayerTab());
			containerVB.setFocusTraversable(true);
			containerVB.setMinWidth(WIDTH_SCREEN);
			
			HBox hb = new HBox(getAddGuessBtn(), getAddMemberBtn(), getDelMemberBtn());
			hb.setAlignment(Pos.CENTER);
			hb.setSpacing(PADDING);
			
			VBox vb = new VBox(hb /*getConnexionVB(), getConnect()*/);
			vb.setAlignment(Pos.CENTER);
			
			containerVB.getChildren().addAll(vb);
			containerVB.setAlignment(Pos.CENTER);
		}
		return containerVB;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe TableView<Player> reprenant le surnom des joueurs ajoute
	 * et doonc present dans la liste
	 * @return TableView<Player>
	 */
	public TableView<Player> getPlayerTab() {
		if ( playerTab == null) {
			playerTab = new TableView<Player>();
			playerTab.setFocusTraversable(true);
			
			playerTab.setMaxHeight(TABLEVIEW_HEIGHT);
			playerTab.setMaxWidth(TABLEVIEW_WIDTH);
			
			playerTab.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			playerTab.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
			ObservableList<Player>data=FXCollections.observableArrayList(setupGame.getClonePlayers());
			playerTab.setItems(data);
			
			TableColumn<Player, String> nickNameTC =  new TableColumn<Player, String>("Nickname");
			nickNameTC.setCellValueFactory(new PropertyValueFactory<>("nickName"));
			
			TableColumn<Player, String> poTC =  new TableColumn<Player, String>("Wallet");
			poTC.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player,String>, ObservableValue<String>>() {
				
				@Override
				public ObservableValue<String> call(CellDataFeatures<Player,String> param) {
					if ( param.getValue() instanceof Member )
						return new SimpleStringProperty(param.getValue().getTransaction() +"");
					return new SimpleStringProperty("/");
				}
			});
			
			playerTab.getColumns().addAll(nickNameTC, poTC);
		}
		return playerTab;
	}
	
	/**
	 * methode vidant le contenu du tableView
	 */
	public void clearTableView() {
		getPlayerTab().getItems().clear();
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Button permettant la suppression d'un joueur de la liste prealablement
	 * selectionne dans le tableau
	 * @return Button
	 */
	public Button getDelMemberBtn() {
		if ( delMemberBtn == null ) {
			delMemberBtn = new Button("Del player");
			animateClick(delMemberBtn);
			delMemberBtn.setFocusTraversable(false);
			
			delMemberBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if ( getPlayerTab().getSelectionModel() != null ) {
							List<Player> playersDell = getPlayerTab().getSelectionModel().getSelectedItems();
							setupGame.removeAllPlayer(playersDell);
							getPlayerTab().getItems().removeAll(playersDell);
					}
				}
			});
		}
		return delMemberBtn;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Button qui permet l'ajout d'un invite dans la liste de joueur
	 * @return Button
	 */
	public Button getAddGuessBtn() {
		if ( addGuessBtn == null ) {
			addGuessBtn = new Button("Guest");
			animateClick(addGuessBtn);
			addGuessBtn.setFocusTraversable(false);
			addGuessBtn.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					try {
						if ( setupGame.getNumberPlayers() < setupGame.NB_PLAYER_MAX) {
							
							Guest g = new Guest();
							if (setupGame.addPlayer(g));
								getPlayerTab().getItems().add(g);
						}
					} catch (NickNameLongerException | PlayerAlreadyPresentException e) {
						e.printStackTrace();
					}
				}
			});
		}
		return addGuessBtn;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Button permet de lancer la procedure de connection
	 * @return Button
	 */
	public Button getAddMemberBtn() {
		if ( addMemberBtn == null ) {
			addMemberBtn = new Button("Member");
			animateClick(addMemberBtn);
			addMemberBtn.setFocusTraversable(false);
			addMemberBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					//getConnexionVB().setVisible(true);
					if (addMemberBtn.getText().equals("Member")) {
						addMemberBtn.setText("Hide");
						animateNodeViewIn(getConnect(), DURA_ALERT_IN);
					}else {
						addMemberBtn.setText("Member");
						animateNodeViewOut(getConnect(), DURA_ALERT_OUT);
					}
					
				}
			});
		}
		return addMemberBtn;
	}

	/**
	 * renvoit et instancie si besoin un element de classe Button prevu pour lancer la partie 
	 * @return Button
	 */
	public Button getStartBtn() {
		if ( startBtn == null ) {
			startBtn = new Button("Start Game");
			animateClick(startBtn);
			startBtn.setFocusTraversable(true);
		}
		return startBtn;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Background generant le fond d'ecran
	 * @return Background
	 */
	public Background getBack() {
		if ( bGround == null ) {
			
		    Image img = new Image("./images/setupGame/back_setupGame.jpg");
		    BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		            new BackgroundSize(WIDTH_SCREEN, HEIGHT_SCREEN, isDisabled(), isDisable(), isCache(), isHover()));
		    bGround = new Background(bImg);
		}
		return bGround;
	}
	
	
	
}
