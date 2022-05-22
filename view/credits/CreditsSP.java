package view.credits;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.game.Credits;
import model.game.Credits.Credit;
import model.player.Member;
import persistence.Persistence;
import view.toolsView.AnimationInterface;
import view.toolsView.WindowData;

public class CreditsSP extends StackPane implements WindowData, AnimationInterface{

	private Credits credits;
	private TableView<Credit> tabCredits;
	private Label titleLbl;
	private Button previousBtn;
	private ImageView backIV;
	
	/**
	 * Classe presentant les credits du jeu
	 */
	public CreditsSP() {
		// chargement des credits depuis la db
		credits = Persistence.readingJson("credits.json", Credits.class);
		
		// mise en place
		AnchorPane ap = new AnchorPane(getPreviousBtn(), getTitleLbl(), getTabCredits());
		
		AnchorPane.setBottomAnchor(getPreviousBtn(),HEIGHT_SCREEN / 10);
		AnchorPane.setLeftAnchor(getPreviousBtn(),PADDING );
		
		AnchorPane.setTopAnchor(getTitleLbl(), HEIGHT_SCREEN / 8.5); // 350
		AnchorPane.setLeftAnchor(getTitleLbl(), WIDTH_SCREEN / 2.4 ); // 700
		
		AnchorPane.setBottomAnchor(getTabCredits(), (HEIGHT_SCREEN / 2.0) - ( getTabCredits().getMinHeight() / 2) ); // 500
		AnchorPane.setLeftAnchor(getTabCredits(), (WIDTH_SCREEN / 2) - (getTabCredits().getMinWidth() / 2 ) ); // 700
		
		this.getChildren().addAll(getBack(),ap);
		
	}
	
	/**
	 * renvoit un element de classe Credit
	 * @return Credits
	 */
	public Credits getCredits() {
		return credits;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Label avec pour valeur textuelle "Credits"
	 * @return Label
	 */
	public Label getTitleLbl() {
		if ( titleLbl == null ) {
			titleLbl = new Label("Credits");
			titleLbl.getStyleClass().add("lbl_titre");
		}
		return titleLbl;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe ImageView prevu pour l'image de fond
	 * @return ImageView
	 */
	public ImageView getBack() {
		if (backIV == null) {
			backIV = new ImageView(new Image("./images/credits/creditBack.jpg"));
			backIV.setFitWidth(WIDTH_SCREEN);
			backIV.setFitHeight(HEIGHT_SCREEN);
		}
		return backIV;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Button permettant de revenir au menu precedent
	 * @return Button
	 */
	public Button getPreviousBtn() {
		if (previousBtn == null) {
			previousBtn = new Button("Previous menu");
			animateClick(previousBtn);
		}
		return previousBtn;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe TableView<Credit> affichant les donnees relative aux credits
	 * @return TableView<Credit> 
	 */
	public TableView<Credit> getTabCredits() {
		if ( tabCredits == null ) {
			tabCredits = new TableView<Credit>();
			
			tabCredits.setMaxHeight(400.);
			tabCredits.setMinHeight(400.);
			tabCredits.setMinWidth(3 * (WIDTH_SCREEN / 4));
			
			tabCredits.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			tabCredits.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
			ObservableList<Credit>data=FXCollections.observableArrayList(getCredits().getCredits());
			tabCredits.setItems(data);
			
			TableColumn<Credit, String> authorTC =  new TableColumn<Credit, String>("Author");
			authorTC.setCellValueFactory(new PropertyValueFactory<>("author"));
			tabCredits.getColumns().add(authorTC);
			
			TableColumn<Credit, String> webTC =  new TableColumn<Credit, String>("URL Website");
			webTC.setCellValueFactory(new PropertyValueFactory<>("website"));
			tabCredits.getColumns().add(webTC);
			
			TableColumn<Credit, String> descriTC =  new TableColumn<Credit, String>("Description");
			descriTC.setCellValueFactory(new PropertyValueFactory<>("description"));
			tabCredits.getColumns().add(descriTC);
			
		}
		return tabCredits;
	}
}
