package utilities;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.menu.PrincipalViewSP;
import view.toolsView.WindowData;

	public class MainJar extends Application implements WindowData{
		@Override
		public void start(Stage primaryStage) {
			try {
				
				PrincipalViewSP jeu = new PrincipalViewSP();

				primaryStage.setTitle("To The Moon");
				primaryStage.getIcons().addAll(new Image("./images/iconStage/moon_icon2.png"));
				//primaryStage.setResizable(false);
				
				
				Scene scene = new Scene(jeu, WIDTH_SCREEN, HEIGHT_SCREEN );
				
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				
				//primaryStage.setFullScreen(true);
				
				
				primaryStage.setScene(scene);
				primaryStage.show();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void main(String[] args) {
			launch(args);

		}
}
