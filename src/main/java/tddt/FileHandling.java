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
	
	/**
	 * upon initialization, user is prompted to specify a file that will be opened later
	 */
	public FileHandling (){
		//opens an Explorer window at the start and and saves file in a local var
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open .propra File");
		file = fileChooser.showOpenDialog(new Stage());
	}
	
	public String readTask(){
		//Reads the task out of the specified .propra file
		return separateString()[2];
	}
	
	public String readTest(){
		//Reads the tests out of the specified .propra file
		return separateString()[1];
	}
	
	public String readClass(){
		//Reads the Java class out of the specified .propra file
		return separateString()[0];
	}
	
	/**
	 * separates the local file into the three strings
	 * @return an array of code/tests/task in this specific order
	 */
	public String[] separateString(){
		//Separates the .propra File according to the separator String
		String line="";
		String full = "";
		
	    try(BufferedReader b = new BufferedReader (new FileReader(file))) {
	      while( (line = b.readLine()) != null ) {
	    	  full+= line + "\n";
	      }
	      b.close();
	    }catch (IOException e) {
	      System.err.println("Fehler beim Verarbeiten der Datei!");
	    }
		String[] sep = full.split("~~NoSuchFieldException-ProPra16~~");
		return sep;
	}
	
	/**
	 * Saves the current state of the project into a .propra file of the user's choosing
	 * @param code the content of the coding window that needs to be saved
	 * @param test the content of the tests window that needs to be saved
	 * @param task the content of the task window that needs to be saved
	 */
	
	public static void saveFile(String code, String test, String task){
		FileChooser fileChooser = new FileChooser();
		  
        //Specifies the desired output format, which is .propra
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PROPRA files (*.propra)", "*.propra");
        fileChooser.getExtensionFilters().add(extFilter);
        //Show save file window
        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null){
        	//Writes the content into the file
        	try {
                FileWriter fileWriter = null;
                fileWriter = new FileWriter(file);
                //Formats the String according to the separator string
                String full = code + "~~NoSuchFieldException-ProPra16~~"  + test +  "~~NoSuchFieldException-ProPra16~~"  + task;
                fileWriter.write(full);
                fileWriter.close();
            } catch (IOException ex) {
                System.err.println("Fehler bei Dateispeicherung!");
            }
        }
	}
	

	
}
