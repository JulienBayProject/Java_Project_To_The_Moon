package view.duringGame;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import miniGames.ListMiniGame;
import miniGames.MiniGameAbstractSP;
import model.bonus.PriceSpell;
import model.bonus.Spell;
import model.deck.Category;
import model.game.DuringGame;
import model.player.Player;
import view.toolsView.AlertChooseCatVB;
import view.toolsView.AlertFiftyFiftyVB;
import view.toolsView.AlerteVB;
import view.toolsView.AnimationInterface;
import view.toolsView.WindowData;

public class DuringGameSP extends StackPane implements AnimationInterface, WindowData {

	private DuringGame duringGame;
	private DiceSP dice;
	private QuestionViewGP questionView;
	private TimerGameHB timer;
	private GamePanelSP gamePanel;
	private ViewEndGameSP endGame;
	private MiniGameAbstractSP miniGame;
	private AlerteVB alert;
	private ShopSpellBP shop;
	private InventoryBP inventory;
	private AlertFiftyFiftyVB fiftyFifty;
	private AlertChooseCatVB chooseCat;

	private StackPane screenPause;
	private ImageView playIV;
	private ImageView mainMenuIV;

	private int i;
	private List<Category> cat;
	
	private Button btnShop;
	private Button btnInventaire;
	
	private Button cheatEndGame;

	/**
	 * Cree la classe principal gerant les affichages et interraction durant la
	 * partie
	 * 
	 * @param playerss Liste des joueurs
	 */
	public DuringGameSP(List<Player> playerss) {

		/**
		 * Instanciation de la partie
		 */
		duringGame = new DuringGame(playerss);
		duringGame.changeTransactionWallet();

		for (Player player : playerss) {
			player.addMoney(500);
		}
		/**
		 * création d'un anchorPane conteneur du dé, accordeon player, de l'affichage
		 * question, et du timer (en construction) suivi du positionnement de chaque
		 * noeud dans celui-ci
		 */
		AnchorPane panels = new AnchorPane();
		
		HBox hb = new HBox(PADDING, getBtnInventaire(), getBtnShop());
		
		panels.getChildren().addAll( getQuestionView(getDice(), duringGame), getTimer(), getDice(), getCheatEndGame(), hb );
		
		
		AnchorPane.setLeftAnchor(hb, PADDING );
		AnchorPane.setTopAnchor(hb, PADDING );
		
		AnchorPane.setLeftAnchor(getDice(), PADDING );
		AnchorPane.setBottomAnchor(getDice(), PADDING );
		
		AnchorPane.setRightAnchor(getQuestionView(), PADDING );
		AnchorPane.setBottomAnchor(getQuestionView(), PADDING );
		
		AnchorPane.setRightAnchor(getAlert(), (double) Pos.CENTER.ordinal() );
		AnchorPane.setLeftAnchor(getAlert(), (double) Pos.CENTER.ordinal() );
		
		AnchorPane.setTopAnchor(getTimer(), PADDING );
		AnchorPane.setRightAnchor(getTimer(), PADDING);
		
		AnchorPane.setLeftAnchor(getCheatEndGame(), PADDING );
		AnchorPane.setBottomAnchor(getCheatEndGame(), PADDING + 200 );

		this.getChildren().addAll(getGamePanel(), panels, alert, getShop(), getInventory(), getScreenPause(),getFiftyFifty(), getChooseCat());
	} 
	
	public Button getBtnInventaire() {
		if ( btnInventaire == null ) {
			btnInventaire = new Button("Inventory");

			btnInventaire.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (btnInventaire.getText().equals("Inventory") && !getInventory().isVisible()) {
						if ( getShop().isVisible()) {
							getShop().setVisible(false);
							getBtnShop().setText("Shop");
						}
						getInventory().setPlayer(getDuringGame().getPlayerActive());
						getInventory().refresh();
						getInventory().actualisationLabels();
						animateFadeIn(getInventory(), DURA_FRAME); 
						btnInventaire.setText("Close");
						
					} else {
						animateFadeOut(getInventory(), DURA_FRAME);
						btnInventaire.setText("Inventory");
					}
				}
			});
		}
		return btnInventaire;
	}
	
	public Button getBtnShop() {
		if ( btnShop == null ) {
			btnShop = new Button("Shop");
			btnShop.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (btnShop.getText().equals("Shop") && !getShop().isVisible()) {
						if ( getInventory().isVisible()) {
							getInventory().setVisible(false);
							getBtnInventaire().setText("Inventory");
						}
						getShop().setPlayer(getDuringGame().getPlayerActive());
						getShop().refresh();
						animateFadeIn(getShop(), DURA_FRAME);
						btnShop.setText("Close");
					} else {
						animateFadeOut(getShop(), DURA_FRAME);
						btnShop.setText("Shop");
					}
				}
			});
		}
		return btnShop;
	}
	
	public Button getCheatEndGame() {
		if ( cheatEndGame == null ) {
			cheatEndGame = new Button("GOD MOD");
			cheatEndGame.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					duringGame.getPlayerActive().callMeGod();
					displayEndGame();
				}
			});
		}
		return cheatEndGame;
	}
	

	/**
	 * renvoit et instancie si besoin un element de classe StackPane prevu a etre
	 * affiche quand le joueur fait pause, stop le timer
	 * 
	 * @return StackPane
	 */
	public StackPane getScreenPause() {
		if (screenPause == null) {
			// creation des composants
			Label lbl = new Label("Pause");
			lbl.getStyleClass().add("lbl_titre");

			playIV = new ImageView(new Image("./images/timer/play.png"));
			animateClick(playIV);
			playIV.setFitHeight(200);
			playIV.setPreserveRatio(true);

			// quand on clique sur pause, affichage de l'ecran de pause + timer mis en pause
			playIV.setOnMouseClicked((e) -> {
				getTimer().runTimer();
				getTimer().setPause(false);
				getScreenPause().setVisible(false);
			});

			// dispositon et mise en place
			HBox btnHB = new HBox(PADDING, playIV, getMainMenuIV());
			btnHB.setAlignment(Pos.CENTER);

			VBox vb = new VBox(150., lbl, btnHB);
			vb.setAlignment(Pos.CENTER);

			Rectangle back = new Rectangle(WIDTH_SCREEN, HEIGHT_SCREEN);
			back.setId("screen_pause");

			screenPause = new StackPane(back, vb);
			screenPause.setVisible(false);
		}
		return screenPause;
	}

	/**
	 * renvoit et instancie si besoin un element de classe ImageView ayant l'image
	 * du "bouton" pause
	 * 
	 * @return ImageView
	 */
	public ImageView getMainMenuIV() {
		if (mainMenuIV == null) {
			mainMenuIV = new ImageView(new Image("./images/timer/stop.png"));
			animateClick(mainMenuIV);
			mainMenuIV.setFitHeight(200);
			mainMenuIV.setPreserveRatio(true);
		}
		return mainMenuIV;
	}

	/**
	 * renvoit l'element de classe DuringGame
	 * 
	 * @return
	 */
	public DuringGame getDuringGame() {
		return duringGame;
	}

	/**
	 * Lance le mini jeu en ajoutant celui ci a la fenetre
	 */
	public void launchMiniGame() {
		this.getChildren().add(getMiniGame());
	}

	/**
	 * termine le mini jeu en le retirant de la fenetre
	 */
	public void endMiniGame() {
		this.getChildren().remove(getMiniGame());
	}

	/**
	 * renvoit l element de classe MiniGameAbstract
	 * 
	 * @return MiniGameAbstract
	 */
	public MiniGameAbstractSP getMiniGame() {
		return miniGame;
	}

	/**
	 * renvoit et instancie si besoin un element de classe ShopSpell -> interface
	 * d'achat de sort
	 * 
	 * @return ShopSpell
	 */
	public ShopSpellBP getShop() {
		if (shop == null) {
			shop = new ShopSpellBP(duringGame.getPlayerActive());
			shop.setVisible(false);
			shop.getBtnFiftyFifty().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (shop.getPlayer().purchasePossible(PriceSpell.FIFTY_FIFTY.getPrice())) {
						shop.getPlayer().removeMoney(PriceSpell.FIFTY_FIFTY.getPrice());
						shop.getPlayer().addInventory(Spell.FIFTY_FIFTY.getS());
						if (inventory.getBtnFiftyFifty().isDisable())
							inventory.getBtnFiftyFifty().setDisable(false); 
					}
				}
			});
			
			shop.getBtnChooseCat().setOnAction(new EventHandler<ActionEvent>() { 

				@Override
				public void handle(ActionEvent event) {
					if (shop.getPlayer().purchasePossible(PriceSpell.CHOOSE_CAT.getPrice())) {
						shop.getPlayer().removeMoney(PriceSpell.CHOOSE_CAT.getPrice());
						shop.getPlayer().addInventory(Spell.CHOOSE_CAT.getS());
						if (inventory.getBtnChooseCat().isDisable())
							inventory.getBtnChooseCat().setDisable(false);
							
					}

				}
			});
		}
		return shop;
	}

	public InventoryBP getInventory() {
		if (inventory == null) {
			inventory = new InventoryBP(duringGame.getPlayerActive());
			inventory.setVisible(false);

			inventory.getBtnFiftyFifty().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (duringGame.getPlayerActive().getInventory().contains(Spell.FIFTY_FIFTY.getS())) {
						getFiftyFifty().writeAlert("Fifty fifty","Be sure to have a question active before using the spell");
						if (getQuestionView().isVisible()) {
							getFiftyFifty().getYes().setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
									animateNodeViewOut(getFiftyFifty(), DURA_ALERT_OUT);
									int i = 0;
									for (Map.Entry<String, Boolean> entry : getQuestionView().getQ().getChoices()
											.entrySet()) {
										if (!entry.getValue()) {
											getQuestionView().getChoicesBtn().get(i).setVisible(false);
											break;
										}
										i++;
									}
								getDuringGame().getPlayerActive().removeString(Spell.FIFTY_FIFTY.getS());
								inventory.actualisationLabels();
								inventory.setVisible(false);
								// btn inventaire .setText
								getBtnInventaire().setText("Inventory");
							}

						});
						}
						getFiftyFifty().getNo().setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								animateNodeViewOut(getFiftyFifty(), DURA_ALERT_OUT);
							}
						});
					}
				}
			});
			inventory.getBtnChooseCat().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (duringGame.getPlayerActive().getInventory().contains(Spell.CHOOSE_CAT.getS())) {
						animateNodeViewIn(getChooseCat(), DURA_ALERT_IN);
						getBtnInventaire().setText("Inventory");
					}
				}
			});
		}
		return inventory;
	}

	/**
	 * choisi un mini jeu au hazard dans la liste
	 */
	public void setRandomMiniGame() {
		this.miniGame = new ListMiniGame().getRandomGame();
	}

	/**
	 * renvoit et instancie si besoin un element de classe ViewEndGame -> interface
	 * de fin de partie
	 * 
	 * @return ViewEndGameGP
	 */
	public ViewEndGameSP getEndGame() {
		if (endGame == null) {
			endGame = new ViewEndGameSP();
			
		}
		return endGame;
	}

	/**
	 * renvoit et instancie si besoin un element de classe GamePanel -> ensemble
	 * d'image du plateau de jeu
	 * 
	 * @return GamePanel
	 */
	public GamePanelSP getGamePanel() {
		if (gamePanel == null) {
			gamePanel = new GamePanelSP();
			gamePanel.setPlayers(getDuringGame().getPlayers()); 
			gamePanel.getPlayerActiverSP();		 
			gamePanel.setPlayerActive(getDuringGame().getPlayerActive().getNickName(), getDuringGame().getPlayerActive(), getDuringGame().beforePlayer());
			gamePanel.decorate();
		}
		return gamePanel;
	}

	/**
	 * renvoit et instancie si besoin un element de classe TimerGameHB -> chrono de
	 * partie
	 * 
	 * @return TimerGameHB 
	 */
	public TimerGameHB getTimer() {
		if (timer == null) {
			timer = new TimerGameHB();

			timer.getPauseIV().setOnMouseClicked((e) -> {
				if (!timer.isPause()) {
					timer.pauseTimer();
					timer.setPause(true);
					getScreenPause().setVisible(true);
				}
			});
		}
		return timer;
	}

	/**
	 * renvoit et instancie si besoin un element de classe Dice -> selectionne les
	 * questions au hasard definit que faire en fonction du resultat aleatoire
	 * obtenu
	 * 
	 * @return Dice
	 */
	public DiceSP getDice() {
		if (dice == null) {
			dice = new DiceSP();
			dice.setFocusTraversable(true);
			dice.getTitle().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// recuperation de la categorie aleatoire
					String cat = duringGame.getRandomCategory();

					// on lance l'animation
					dice.animateDice(duringGame.getCategoriesString(), cat);
					dice.setDisable(true);

					switch (cat) {
					// evenement lie aux categories special - passage au joueur suivant
					case "NEXT":
						// on avertit de l'evenement 
						getAlert().writeAlert("Next player", "You lose your turn", "Next");
						getAlert().getOkBtn().setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								// on passe au joueur suivant
								getGamePanel().getSkinViewByPlayer(getDuringGame().getPlayerActive()).up();
								getQuestionView().nextTurn();
								dice.getTitle().setText("Launch");
								dice.setDisable(false);
							}
						});
						break;

					// evenement lie aux categories special - defi mini jeu
					case "DEFI":

						// on avertit de l'evenement
						getAlert().writeAlert("It's time for a defi !","A challenging moment! \nA mini-game will start allowing you to earn money!", "Start");
						getAlert().getOkBtn().setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								// on choisi le mini jeu aleatoirement et on le lance
								setRandomMiniGame();
								launchMiniGame();

								// on parametre le bouton se trouvant a la fin du jeu pour recuperer les gains
								// et passage au tour suivant
								getMiniGame().getFinishBtn().setOnAction(new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent event) {
										duringGame.getPlayerActive().addMoney(miniGame.getPo());
										endMiniGame();
										getGamePanel().getSkinViewByPlayer(getDuringGame().getPlayerActive()).up();
										getQuestionView().nextTurn();
										dice.setDisable(false);
										animateNodeViewOut(getAlert(), DURA_ALERT_OUT);
									}
								});
							}
						});
						break;
					default:
						// ligne pour le choix de catégories
						getQuestionView().remplissage(duringGame.getQuestion(model.deck.Category.valueOf(cat)));
						animateNodeViewIn(getQuestionView(), DURA_QUESTION_IN);
						break;
					}
				};
			});
		}
		return dice;
	}

	/**
	 * renvoit et instancie si besoin un element de classe QuestionViewGP prevu pour
	 * l'affichage des questions
	 * 
	 * @param d  Dice
	 * @param dg DuringGame
	 * @return QuestionViewGP
	 */
	public QuestionViewGP getQuestionView(DiceSP d, DuringGame dg) {
		if (questionView == null) {
			questionView = new QuestionViewGP();
			questionView.setDuringGameSP(this);
		}
		return questionView;
	}


	/**
	 * renvoit et instancie si besoin un element de classe
	 * 
	 * @return QuestionViewGP
	 */
	public QuestionViewGP getQuestionView() {
		return questionView;
	}

	/**
	 * Ajouter l'ecran de fin de partie et affiche les statistiques
	 */
	public void displayEndGame() {
		this.getChildren().add(getEndGame());
		animateFadeIn(getEndGame(), DURATION_FADE);
	}

	/**
	 * renvoit et instancie si besoin un element de classe AlerteVB
	 * 
	 * @return
	 */
	public AlerteVB getAlert() {
		if (alert == null) {
			alert = new AlerteVB();
		}
		return alert;
	}

	public AlertFiftyFiftyVB getFiftyFifty() {
		if (fiftyFifty == null) {
			fiftyFifty = new AlertFiftyFiftyVB();
		}
		return fiftyFifty;
	}

	public AlertChooseCatVB getChooseCat() {
		if (chooseCat == null) {
			chooseCat = new AlertChooseCatVB();
			cat = Arrays.asList(Category.values());
			for (i = 0; i < cat.size(); i++) {
				chooseCat.getButtons().get(i).setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						for (int y = 0; y < chooseCat.getButtons().size(); y++) {
							Button button = (Button) event.getSource();
							if (button.getText().equalsIgnoreCase(cat.get(y).toString())) {
								getDuringGame().getPlayerActive().removeString(Spell.CHOOSE_CAT.getS());
								inventory.actualisationLabels();
								if (!inventory.chooseCatVerif()) {
									button.setDisable(true);
								}
								chooseCat.setVisible(false);
								getInventory().setVisible(false);
								getQuestionView().remplissage(duringGame.getQuestion(cat.get(y)));
								animateNodeViewIn(getQuestionView(), DURA_QUESTION_IN);
							}
						} 
					}
				});
			}
			chooseCat.getBtnExit().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					animateNodeViewOut(chooseCat, DURA_ALERT_OUT);
				}
			});
		}
		return chooseCat;
	}

}