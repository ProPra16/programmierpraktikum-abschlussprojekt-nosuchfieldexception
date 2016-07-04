package tddt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileHandling {
	
	File file;
	
	public FileHandling (Stage s){
		
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open .propra File");
		file = fileChooser.showOpenDialog(s);
		
		
		
		
	}
	
	public String readTask(){
		return separateString()[2];
	}
	
	public String readTest(){
		return separateString()[1];
	}
	
	public String readClass(){
		return separateString()[0];
	}
	
	
	
	
	
	public static String[] separateString(){
		
		String line="";
		String file = "";
		
	    try(BufferedReader b = new BufferedReader (new FileReader(file))) {
	      while( (line = b.readLine()) != null ) {
	    	  file+= line + "\n";
	      }
	      b.close();
	      //System.out.println(file);
	      
	    }catch (IOException e) {
	      System.out.println("Fehler: "+e.toString());
	    }
	    
		String[] sep = file.split("-----");
		//System.out.println(sep[0]);
		//System.out.println(sep[1]);
		//System.out.println(sep[2]);
		return sep;
	}
	
	

	
}
