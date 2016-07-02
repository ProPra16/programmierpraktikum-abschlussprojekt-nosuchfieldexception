package tddt;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MenuController {

	@FXML
	private TextArea codeArea;

	@FXML
	private TextArea testArea;

	@FXML
	private TextArea errorArea;
	
	@FXML
	private CheckBox babystepsCheckBox;
	
	@FXML
	private TextField timeTextField;

	@FXML
	public void handleLoadButton() {

	}

	@FXML
	public void handleSaveButton() {
	}

	@FXML
	public void handleNextStepButton() {

	}

	@FXML
	public void handleStartButton() {
		if(babystepsCheckBox.isSelected()) { //Babysteps!
		} else { //No Babysteps
			
		}
	}

}
