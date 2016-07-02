package tddt;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileHandling {
	
	public static void openFile(){
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.showOpenDialog(stage);
	}
}
