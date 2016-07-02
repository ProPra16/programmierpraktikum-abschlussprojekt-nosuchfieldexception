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
		KataLiveCompiler compiler = new KataLiveCompiler(codeArea.getText(), testArea.getText());
		errorArea.setText(compiler.getErrors());
	}

}
