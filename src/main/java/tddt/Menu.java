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
		
		//Szenengr��e bestimmen
		Scene scene = new Scene(root,800 ,800);
		//CSS laden
		scene.getStylesheets().add(getClass().getClassLoader().getResource("menu.css").toExternalForm());

		//Output-Fenster �ffnen, wenn man fertig ist
		primaryStage.setOnCloseRequest(event -> {
			Parent parent;
	        try {
	            parent = FXMLLoader.load(getClass().getClassLoader().getResource("output.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("Output");
	            stage.setScene(new Scene(parent, 800, 800));
	            stage.show();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		});
		
		//Fenster anzeigen
		primaryStage.setScene(scene);
		primaryStage.setTitle("TDDT");
	    primaryStage.centerOnScreen();
	    primaryStage.setMaximized(true);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
