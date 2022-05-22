package view.toolsView;

import javafx.stage.Screen;

public interface WindowData {

	/**
	 * mesure général de la hauteur et largeur fenetre
	 */
	public final double HEIGHT_SCREEN = Screen.getPrimary().getVisualBounds().getHeight() - 30.;
	public final double WIDTH_SCREEN = Screen.getPrimary().getVisualBounds().getWidth() ;
	
	/**
	 * mesure de mise page general
	 */
	public final double PADDING = 20.;
	
	/**
	 *  donnee relative au animation du dice
	 */
	public final double HEIGHT_DICE = 200., WIDTH_DICE = 100;
	public final int TURNS_ANIMATE = 15;
	public final int RATIO_FAST_ANIMATION = 15;
	
	/**
	 * donnee relative a la classe QuestionViewGP
	 */
	public final double BUTTON_CHOICE_WIDTH = 1000.;
	public final double SIDE_SQUARE_CATEGORY = 30.;
	
	
	/**
	 * donnee relative a la classe SetupGameView
	 */
	public final double TABLEVIEW_WIDTH = 200.0;
	public final double TABLEVIEW_HEIGHT = 200.;

	/**
	 * donnee relative a la classe ViewEndGame
	 */
	public final double TABLE_END_WIDTH = 625.;
	public final double TABLE_END_HEIGHT = 270.;
	public final double COLUMN_WIDTH_NICKNAME = 200.;
	public final double COLUMN_WIDTH_CATEGORIES = 350.;
	public final double COLUMN_WIDTH_WALLET = 75.;
	
}
