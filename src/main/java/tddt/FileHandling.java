package tddt;

import java.awt.Desktop;
import java.io.File;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileHandling {
	
	public static void openFile(Stage s){
		Desktop desktop = Desktop.getDesktop();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File f = fileChooser.showOpenDialog(s);
		try{
			desktop.open(f);
			System.out.println("eyoooo");
		}catch(Exception e){
			System.out.println("noooooooo");
		}
		
	}

	
}
