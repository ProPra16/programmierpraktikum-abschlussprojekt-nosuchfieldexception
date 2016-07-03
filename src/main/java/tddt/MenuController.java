package tddt;

import babysteps.BabystepsCycle;
import babysteps.BabystepsUser;
import babysteps.CustomTimer;
import babysteps.TDDCycle;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class MenuController {


	private KataLiveCompiler compiler;
	
	/**
	 * 	Obvious choice, innit?
	 */
	private Color phase = Color.RED;

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

	@FXML
	public void handleNextStepButton() {
		this.compileCode();
		//RED-Phase
		if(phase.equals(Color.RED)){
			
			
			//Condition to get to the next Phase:
			//Have compiling code but erroring test(s)
			if(compiler.codeCompilesAndDoesNotFulfillTests()){
				phase = Color.GREEN;
			}
		//GREEN-PHASE
		}else if(phase.equals(Color.GREEN)){
			
			
			//Condition to get to the next Phase:
			//Have compiling code and satisfied tests!
			if(compiler.codeCompilesAndFulfillsTests()){
				phase = Color.BLACK;
			}
		//Refactor-Phase
		}else if(phase.equals(Color.BLACK)){
			
		}
	}

	private void compileCode() {
		// Check if it can be a valid class
		if (!codeArea.getText().contains("class")) {
			errorArea.setText("Die Code-Klasse ist noch keine Klasse...");
		}
		if (!testArea.getText().contains("class")) {
			errorArea.setText(errorArea.getText() + "\nDie Test-Klasse ist noch keine Klasse...");
		} else {
			KataLiveCompiler compiler = new KataLiveCompiler(codeArea.getText(), testArea.getText());
			errorArea.setText(compiler.getErrors());
		}
	}

}
