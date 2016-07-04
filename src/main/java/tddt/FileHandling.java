package tddt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileHandling {
	
	public static String[] openFile(Stage s){
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File f = fileChooser.showOpenDialog(s);
		
		String line="";
		String file = "";
		
	    try(BufferedReader b = new BufferedReader (new FileReader(f))) {
	      while( (line = b.readLine()) != null ) {
	    	  file+= line + "\n";
	      }
	      b.close();
	      //System.out.println(file);
	      
	    }catch (IOException e) {
	      System.out.println("Fehler: "+e.toString());
	    }
		
	    return separateString(file);
		
	}
	
	public static String[] separateString(String full){
		String[] sep = full.split("-----");
		System.out.println(sep[0]);
		System.out.println(sep[1]);
		System.out.println(sep[2]);
		return sep;
	}
	
	

	
}
