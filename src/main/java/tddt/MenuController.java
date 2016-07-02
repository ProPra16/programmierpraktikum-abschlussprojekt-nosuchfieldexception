package tddt;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class MenuController {

	@FXML
	private TextArea codeArea;
	
	@FXML
	private TextArea testArea;
	
	@FXML
	private TextArea errorArea;
	
	
	@FXML
	public void handleLoadButton(){
		
	}
	
	@FXML
	public void handleSaveButton(){

	}
	
	@FXML
	public void handleNextStepButton(){
		//Check if it can be a valid class
		if(!codeArea.getText().contains("class")){
			errorArea.setText("Die Code-Klasse ist noch keine Klasse...");
		}
		if(!testArea.getText().contains("class")){
			errorArea.setText(errorArea.getText() + "\nDie Test-Klasse ist noch keine Klasse...");
		}else{
			KataLiveCompiler compiler = new KataLiveCompiler(codeArea.getText(), testArea.getText());
			errorArea.setText(compiler.getErrors());
		}
	}

}
