package view.duringGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.deck.Category;
import model.deck.CategorySpecial;
import view.toolsView.AnimationInterface;
import view.toolsView.WindowData;

public class DiceSP extends StackPane implements WindowData, AnimationInterface {

	private Button title;
	private Rectangle back;
	
	/**
	 * cree un de qui renverra une valeur aleatoire des enums Categorie ou CategoriesSpecial avec animation visuelle
	 */
	public DiceSP() {
			
		this.getChildren().addAll(getBack(), getTitle());
		getBack().getStyleClass().add("dice_view");
		getTitle().getStyleClass().add("dice_view");
	}
	
	
	/**
	 * renvoit et instancie si besoin un élément de classe Rectangle correspondant au fond du de
	 * @return Rectangle
	 */
	public Rectangle getBack() {
		if (back == null ) {
			back = new Rectangle(HEIGHT_DICE, WIDTH_DICE);
			back.setArcHeight(100.0d);
	        back.setArcWidth(110.0d);
		}
		return back;
	}
	
	/**
	 * renvoit et instancie si besoin un élément de classe Button avec valeur texte "Launch" prevu pour
	 * lancer le de et afficher les categories et s'arrete sur la categories choisies aleatoirement
	 * @return Button
	 */
	public Button getTitle() {
		if ( title == null) {
			title = new Button("Launch");
			
		}
		return title;
	}

	/**
	 * actualise la valeur textuelle du bouton par la valeur rentree en parametre
	 * @param str - texte desire
	 */
	public void setTitle(String str) {
		this.title.setText(str);
	}
	
	/**
	 * animation visuel du dice prenant compte des valeurs se trouvant dans les enums Categorie et CategoriesSpecial
	 * @param listStringCategory Liste des valeurs textuelles des enums 
	 * @param strEnd valeur de fin d'animation a afficher
	 */
	public void animateDice(List<String> listStringCategory, String strEnd) {
		Random rand = new Random();	
		SequentialTransition st = new SequentialTransition();
		
		// creation et chargement list des couleurs : on boucle sur les valeurs de color pour recuperer la couleur
		List<Color> color= new ArrayList<Color>();
		for (String s : listStringCategory) {
			try {
				color.add(CategorySpecial.valueOf(s).getColor());				
			} catch (IllegalArgumentException e) {
				
				color.add(Category.valueOf(s).getColor());
			}
		}
		
		//recuperation de la couleur de fin
		Color colorEnd;
		try {
			colorEnd = Category.valueOf(strEnd).getColor();
		}catch (IllegalArgumentException e) {
			colorEnd = CategorySpecial.valueOf(strEnd).getColor();
		}
		
		// animation fade out de dice
		FadeTransition ftOut = new FadeTransition(Duration.seconds(DURATION_FADE), title);
		ftOut.setFromValue(1.0);
		ftOut.setToValue(0.0);
		st.getChildren().add(ftOut);
		
		// chargement de la sequence d'animation
		for ( int i = 1 ; i < TURNS_ANIMATE ; i++) {
			
			// animation qui change aleatoirement la couleur de fond
			FillTransition rFt = new FillTransition();
			rFt.setDuration(Duration.millis(i * RATIO_FAST_ANIMATION));
			rFt.setFromValue(color.get(rand.nextInt(color.size())));
			
			// quand on arrive au dernier tour de boucle, on precise la couleur exact de la category
			if ( i + 1 == TURNS_ANIMATE )
				rFt.setToValue(colorEnd);
			else
				rFt.setToValue(color.get(rand.nextInt(color.size())));
			
			rFt.setShape(back);
			st.getChildren().add(rFt);
		}
		
		// une fois fini on change le text du btn avec le nom de la categorie + animation fadeInt
		st.setOnFinished((e)-> {
			FadeTransition ftInt = new FadeTransition(Duration.seconds(DURATION_FADE), title);
			ftInt.setFromValue(0.0);
			ftInt.setToValue(1.0);
			title.setText(strEnd);
			ftInt.play();
		});
		st.play();
	}
}
