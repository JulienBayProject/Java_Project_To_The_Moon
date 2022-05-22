package view.admin;

import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import model.player.GestionMember;
import model.player.Member;
import persistence.Persistence;
import view.toolsView.AnimationInterface;
import view.toolsView.WindowData;

public class AdminSP extends StackPane implements WindowData, AnimationInterface {

	private ImageView backIV;
	private Label titleLbl;
	
	private Button previousBtn;
	
	private TableView<Member> memberTab;
	private Label delMemberLbl;
	private Button delMember;
	
	private GestionMember gm;
	private Button promoDemoAdmin;
	
	/**
	 * Classe reprenant l'interface administrateur
	 */
	public AdminSP() {
		
		//ajout et positionnement des composants
		HBox hbBtn = new HBox(PADDING, getDelMember(), getPromoDemoAdmin() );
		hbBtn.setAlignment(Pos.CENTER);
		
		AnchorPane ap = new AnchorPane(getPreviousBtn(), getTitleLbl(), getMemberTab(), getDelMemberLbl(), hbBtn );
		
		
		AnchorPane.setBottomAnchor(getPreviousBtn(),HEIGHT_SCREEN / 6.5);
		AnchorPane.setLeftAnchor(getPreviousBtn(),PADDING );
		
		AnchorPane.setBottomAnchor(getTitleLbl(), HEIGHT_SCREEN / 8.5); // 350
		AnchorPane.setLeftAnchor(getTitleLbl(), WIDTH_SCREEN / 2.75 ); // 700
		
		AnchorPane.setBottomAnchor(getMemberTab(), HEIGHT_SCREEN / 2.0); // 500
		AnchorPane.setLeftAnchor(getMemberTab(), WIDTH_SCREEN / 2.20 ); // 700
		
		AnchorPane.setBottomAnchor(getDelMemberLbl(), HEIGHT_SCREEN / .98 ); // 1000.
		AnchorPane.setLeftAnchor(getDelMemberLbl(), WIDTH_SCREEN / 2.4 ); // 700
		
		AnchorPane.setBottomAnchor(hbBtn, HEIGHT_SCREEN / 3.); // 1000.
		AnchorPane.setLeftAnchor(hbBtn, WIDTH_SCREEN / 2.461); // 700
		
		this.getChildren().addAll(getBack(),ap);
	}
	
	public Button getPromoDemoAdmin() {
		if ( promoDemoAdmin == null ) {
			promoDemoAdmin = new Button("Promote / Demote");
			animateClick(promoDemoAdmin);
			promoDemoAdmin.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if ( getMemberTab().getSelectionModel() != null ) {
							List<Member> membersSelected = getMemberTab().getSelectionModel().getSelectedItems();
							for (Member m : gm.getMembers()) {
								if ( membersSelected.contains(m))
									m.changeAdmin();
							}
							Persistence.writeJson("databaseUser.json", gm);
							refresh();
					}
				}
			});
		}
		return promoDemoAdmin;
	}
	
	/**
	 * renvoit une classe GestionMember avec pour valeur les membres present dans le fichier "databaseUser.json"
	 * @return GestionMember
	 */
	public GestionMember getGm() {
			gm = Persistence.readingJson("databaseUser.json", GestionMember.class);
		return gm;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Button permettant la suppression de membres selectionner
	 * @return Button
	 */
	public Button getDelMember() {
		if (delMember == null ) {
			delMember = new Button("Del player");
			animateClick(delMember);
			delMember.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if ( getMemberTab().getSelectionModel() != null ) {
							//System.out.println(gm);
							List<Member> membersDell = getMemberTab().getSelectionModel().getSelectedItems();
							gm.removeAll(membersDell);
							Persistence.writeJson("databaseUser.json", gm);
							getMemberTab().getItems().removeAll(membersDell);
					}
				}
			});
		}
		return delMember;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Label avec la valeur textuelle "Delete member"
	 * @return Label
	 */
	public Label getDelMemberLbl() {
		if ( delMemberLbl == null ) {
			delMemberLbl = new Label("Member");
			delMemberLbl.getStyleClass().add("lbl_titre");
		}
		return delMemberLbl;
	}
	
	/**
	 * methode qui actualise les donnees presente dans le tableview member
	 */
	public void refresh() {
		getMemberTab().getItems().clear();
		ObservableList<Member>data=FXCollections.observableArrayList(getGm().getMembers());
		getMemberTab().setItems(data);
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe TableView<Member> reprenant les informations des membres
	 * @return TableView<Member>
	 */
	public TableView<Member> getMemberTab() {
		if ( memberTab == null) {
			memberTab = new TableView<Member>();
			
			memberTab.setMaxHeight(500.);
			memberTab.setMaxWidth(TABLEVIEW_WIDTH);
			
			memberTab.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			memberTab.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
			ObservableList<Member>data=FXCollections.observableArrayList(getGm().getMembers());
			memberTab.setItems(data);
			
			TableColumn<Member, String> nickNameTC =  new TableColumn<Member, String>("Nickname");
			nickNameTC.setCellValueFactory(new PropertyValueFactory<>("nickName"));
			
			TableColumn<Member, Boolean> adminTC =  new TableColumn<Member, Boolean>("Administrator");
			adminTC.setCellValueFactory(new Callback<CellDataFeatures<Member, Boolean>, ObservableValue<Boolean>>() {
		        @Override
		        public ObservableValue<Boolean> call(CellDataFeatures<Member, Boolean> param) {
		        	return new SimpleBooleanProperty(param.getValue().isAdmin());
		        }
		      });
			
			memberTab.getColumns().addAll(nickNameTC, adminTC);
		}
		return memberTab;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Labal avec la valeur textuelle "Administrator"
	 * @return
	 */
	public Label getTitleLbl() {
		if ( titleLbl == null ) {
			titleLbl = new Label("Administrator");
			titleLbl.getStyleClass().add("lbl_titre");
		}
		return titleLbl;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe ImageView reprenant l'image de fond
	 * @return ImageView
	 */
	public ImageView getBack() {
		if (backIV == null) {
			backIV = new ImageView(new Image("./images/admin/backAdmin.jpg"));
			//backIV.setFitHeight(HEIGHT_SCREEN);
			backIV.setFitWidth(WIDTH_SCREEN);
			backIV.setPreserveRatio(true);
		}
		return backIV;
	}
	
	/**
	 * renvoit et instancie si besoin un element de classe Button permettant de revenir au menu précédent
	 * @return Button
	 */
	public Button getPreviousBtn() {
		if (previousBtn == null) {
			previousBtn = new Button("Previous menu");
			animateClick(previousBtn);
		}
		return previousBtn;
	}
}
