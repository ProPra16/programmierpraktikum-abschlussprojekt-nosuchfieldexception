package tddt;

import babysteps.BabystepsCycle;
import babysteps.BabystepsUser;
import babysteps.CustomTimer;
import babysteps.TDDCycle;
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
		TDDCycle cycle = new BabystepsCycle(); //Wherever it comes from? Just for progress
		if(babystepsCheckBox.isSelected()) { //Babysteps!
			CustomTimer timer = new CustomTimer(new BabystepsUser() {
				
				@Override
				public void notifyCodingTimerElapsed() { //Delete and Go back to testing!
					codeArea.setText("time elapsed coding"); //Mit was anderem ersetzen
					cycle.returnToLastPhase(); 
				}
				
				@Override
				public void notifiyTestingTimerElapsed() { 
					testArea.setText("time elapsed testing");
					cycle.returnToLastPhase();
				}
			}, (long) (Double.parseDouble(timeTextField.getText(0,timeTextField.getText().length()-3))*1000*60), (long) (Double.parseDouble(timeTextField.getText(0,timeTextField.getText().length()-3))*1000*60)); //Missing 2nd field
			
			if(cycle.getCurrentPhase() == 0) { //For example
				timer.startTestingTimer();				
			}
			else {
				timer.startCodingTimer();
			}
		} else { //No Babysteps
			
		}
	}

}
