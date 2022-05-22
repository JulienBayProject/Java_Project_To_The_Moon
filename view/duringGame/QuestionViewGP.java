package view.duringGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import model.deck.Question;
import model.player.Player;
import view.toolsView.AnimationInterface;
import view.toolsView.WindowData;

public class QuestionViewGP extends GridPane implements AnimationInterface, WindowData {

	private Question q;
	private DuringGameSP duringGameSP;

	private Label questionLbl;
	private HBox categoryHB;
	private List<Button> choicesBtn;

	/**
	 * Cree un element de classe QuestionViewGP
	 */
	public QuestionViewGP() {
		this.getStyleClass().add("alert_css");
		this.setVisible(false);
		this.setVgap(PADDING);
	}

	/**
	 * Place la valeur recue en arguement dans l'attribut duringGameSP
	 * 
	 * @param duringGameSP
	 */
	public void setDuringGameSP(DuringGameSP duringGameSP) {
		this.duringGameSP = duringGameSP;
	}

	/**
	 * renvoit element de classe DuringGameSP
	 * 
	 * @return DuringGameSP
	 */
	public DuringGameSP getDuringGameSP() {
		return duringGameSP;
	}

	public Question getQ() {
		return q;
	}

	/**
	 * donne la valeur null aux composantx suivants : questionLbl, categoryHB,
	 * choicesBtn
	 */
	public void refresh() {
		questionLbl = null;
		categoryHB = null;
		choicesBtn = null;
		this.getChildren().clear();
	}

	/**
	 * Actualise les composants pour afficher la question et choix de la Question
	 * passé en argument
	 * 
	 * @param q Question a afficher
	 */
	public void remplissage(Question q) {
		refresh();
		setQ(q);
		this.add(getCategoryHB(), 0, 0);
		this.add(getQuestionLbl(), 0, 1);

		this.setAlignment(Pos.CENTER);

		for (int i = 0; i < getChoicesBtn().size(); i++) {
			this.add(getChoicesBtn().get(i), 0, i + 2);
		}
	}

	/**
	 * Execute toutes les procedures necessaire pour Passer au tour suivant
	 */
	public void nextTurn() {
		// passage au joueur actif suivant
		getDuringGameSP().getDuringGame().setPlayerActive();
		getDuringGameSP().getGamePanel().setPlayerActive(getDuringGameSP().getDuringGame().getPlayerActive().getNickName(), getDuringGameSP().getDuringGame().getPlayerActive(), getDuringGameSP().getDuringGame().beforePlayer());

		if (this.isVisible()) {
			animateNodeViewOut(this, DURA_QUESTION_OUT);
			this.getChildren().clear();
		}

		animateNodeViewOut(duringGameSP.getAlert(), DURA_ALERT_OUT);
		getDuringGameSP().getDice().setDisable(false);
		getDuringGameSP().getDice().setTitle("Launch");
	}

	/**
	 * Place la valeur recue en arguement dans l'attribut question
	 * 
	 * @param q Question
	 */
	public void setQ(Question q) {
		this.q = q;
	}

	/**
	 * renvoit et instancie si besoin un element de classe Label avec pour valeur
	 * textuelle l'interrogation de l'attribut Question
	 * 
	 * @return Label
	 */
	public Label getQuestionLbl() {
		if (questionLbl == null) {
			questionLbl = new Label(q.getInterrogation());
			questionLbl.setWrapText(true);
			questionLbl.setMinWidth(BUTTON_CHOICE_WIDTH / 2);
			questionLbl.setMaxWidth(BUTTON_CHOICE_WIDTH / 2);
		}
		return questionLbl;
	}

	/**
	 * renvoit et instancie si besoin un element de classe HBox contenant un
	 * Rectangle de couleur et d'un Label contenant le nom de la categorie en cours
	 * 
	 * @return HBox
	 */
	public HBox getCategoryHB() {
		if (categoryHB == null) {
			Rectangle r = new Rectangle(SIDE_SQUARE_CATEGORY, SIDE_SQUARE_CATEGORY);
			r.setFill(q.getCategory().getColor());

			Label c = new Label(q.getCategory().toString());

			categoryHB = new HBox(r, c);
			HBox.setMargin(c, new Insets(0, 0, 0, 25));
		}
		return categoryHB;
	}

	/**
	 * renvoit et instancie si besoin un element de classe List<Button> contenant la
	 * liste de bouton en rapport avec les reponses possible a la question ainsi de
	 * le traitement en fonction de la reponse
	 * 
	 * @return List<Button>
	 */
	public List<Button> getChoicesBtn() {
		if (choicesBtn == null) {
			choicesBtn = new ArrayList<Button>();
			Player p = getDuringGameSP().getDuringGame().getPlayerActive();
			for (Map.Entry<String, Boolean> entry : q.getChoices().entrySet()) {
				Button btn = new Button(entry.getKey());
				animateClick(btn);
				btn.setMaxWidth(BUTTON_CHOICE_WIDTH);

				if (entry.getValue()) {
					System.out.println("bonne reponse -->" + entry.getKey());
				}
				btn.setOnAction((event) -> {
					// animation visuel du personnage
					duringGameSP.getGamePanel().getSkinViewByPlayer(getDuringGameSP().getDuringGame().getPlayerActive()).up();

					// Si c'est la bonne reponse
					if (entry.getValue()) {

						// on avertit de la bonne reponse
						getDuringGameSP().getAlert().writeAlert("The answer is good ! ", p.getNickName() + " can continue to play ! ", "Continue");

						// quand le joueur a confirme
						getDuringGameSP().getAlert().getOkBtn().setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								// on verifie sa position sur l echelle et les categories reussies
								// si ok on lance la fin de partie
								if (p.verifyCategories()&& getDuringGameSP().getGamePanel().getSkinViewByPlayer(p).isGood())
									getDuringGameSP().displayEndGame();

								// Si le joueur n'a pas la categorie, l'ajoute a la liste de categorie reussie
								if (!p.containsCategory(q.getCategory())) {
									p.addCategory(q.getCategory());

									// on change l'opacite de la categorie dans la console (visuelle)
									getDuringGameSP().getGamePanel().changeOpacityCategory(getDuringGameSP().getDuringGame().getPlayers().indexOf(p), q.getCategory());
								}
								animateNodeViewOut(getDuringGameSP().getAlert(), DURA_ALERT_OUT);
							}
						});

						// Si c'est la mauvaise reponse
					} else {

						// on averti de la mauvaise reponse et passage au tour suivant
						getDuringGameSP().getAlert().writeAlert("The answer is not good ! ",
								getDuringGameSP().getDuringGame().nextPlayer().getNickName()
										+ " your turn to play ! Are you ready ? ",
								"Yes !");
						getDuringGameSP().getAlert().getOkBtn().setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								nextTurn();
	//							DisplayPlayerVB.disableButtonTitlePane(duringGameSP.getDuringGame().getPlayerActive());
							}
						});

					}

//					animateNodeViewOut(this, DURA_QUESTION_OUT);
					this.setVisible(false);
					this.getChildren().clear();
					getDuringGameSP().getDice().setDisable(false);
					getDuringGameSP().getDice().setTitle("Launch");
				});
				choicesBtn.add(btn);
			}
		}
		return choicesBtn;
	}
}