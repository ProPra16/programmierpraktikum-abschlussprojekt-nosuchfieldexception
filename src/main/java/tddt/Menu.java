package tddt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Menu extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Load FXML
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("menu.fxml"));
		
		//Set scene
		Scene scene = new Scene(root,800 ,800);
		//Load CSS
		scene.getStylesheets().add(getClass().getClassLoader().getResource("menu.css").toExternalForm());

		//Show window
		primaryStage.setScene(scene);
		primaryStage.setTitle("TDDT");
	    primaryStage.centerOnScreen();
	    primaryStage.setMaximized(true);
	    primaryStage.getIcons().add((new Image("Logo.PNG")));
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}

