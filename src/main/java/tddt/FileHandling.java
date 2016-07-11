package tddt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
	
	
	
	
	
	public String[] separateString(){
		
		String line="";
		String full = "";
		
	    try(BufferedReader b = new BufferedReader (new FileReader(file))) {
	      while( (line = b.readLine()) != null ) {
	    	  full+= line + "\n";
	      }
	      b.close();
	      //System.out.println(file);
	      
	    }catch (IOException e) {
	      System.out.println("Fehler: "+e.toString());
	    }
	    
		String[] sep = full.split("~~NoSuchFieldException-ProPra16~~");
		//System.out.println(sep[0]);
		//System.out.println(sep[1]);
		//System.out.println(sep[2]);
		return sep;
	}
	
	public static void saveFile(String code, String test, String task){
		FileChooser fileChooser = new FileChooser();
		  
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PROPRA files (*.propra)", "*.propra");
        fileChooser.getExtensionFilters().add(extFilter);
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(new Stage());
        
        if(file != null){
        	try {
                FileWriter fileWriter = null;
                 
                fileWriter = new FileWriter(file);
                
                String full = code + "~~NoSuchFieldException-ProPra16~~"  + test +  "~~NoSuchFieldException-ProPra16~~"  + task;
                
                fileWriter.write(full);
                
                fileWriter.close();
            } catch (IOException ex) {
                System.err.println("Fehler bei Dateispeicherung!");
            }
        }
	}
	

	
}
