package tddt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Menu extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		//FXML laden
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("menu.fxml"));
		
		//Szenengröße bestimmen
		Scene scene = new Scene(root,800 ,800);
		//CSS laden
		scene.getStylesheets().add(getClass().getClassLoader().getResource("menu.css").toExternalForm());

		//Fenster anzeigen
		primaryStage.setScene(scene);
		primaryStage.setTitle("TDDT");
	    primaryStage.centerOnScreen();
		primaryStage.show();
		//FileHandling.openFile(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
