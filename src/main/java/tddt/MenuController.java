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
	private CustomTimer timer;

	@FXML
	private TextArea codeArea;

	@FXML
	private TextArea testArea;

	@FXML
	private TextArea outputArea;

	@FXML
	private CheckBox babystepsCheckBox;

	@FXML
	private TextField timeTextField;

	private TDDTTimer tddttimer;

	@FXML
	public void handleLoadButton() {

	}

	@FXML
	public void handleSaveButton() {

	}


	@FXML
	public void handleStartButton() {
		//TDDCycle cycle = new BabystepsCycle(); //Wherever it comes from? Just for progress
		if(babystepsCheckBox.isSelected()) { //Babysteps!
			timer = new CustomTimer(new BabystepsUser() {
				
				@Override
				public void notifyCodingTimerElapsed() { //Delete and Go back to testing!
					codeArea.setText("time elapsed coding"); //Mit was anderem ersetzen
					//cycle.returnToLastPhase();
					phase = Color.RED;
				}
				
				@Override
				public void notifiyTestingTimerElapsed() { 
					testArea.setText("time elapsed testing");
					//cycle.returnToLastPhase();
					phase = Color.BLACK;
				}
			}, (long) (Double.parseDouble(timeTextField.getText(0,timeTextField.getText().length()-3))*1000*60), (long) (Double.parseDouble(timeTextField.getText(0,timeTextField.getText().length()-3))*1000*60)); //Missing 2nd field
			
			if(phase.equals(Color.RED)) { //For example
				timer.startTestingTimer();				
			}
			else if(phase.equals(Color.GREEN)) {
				timer.startCodingTimer();
			}
		} else { //No Babysteps
			
		}
	}

	@FXML
	public void handleNextStepButton() {
		if (babystepsCheckBox.isSelected() && timer != null)
			timer.stopAll();
		//Check & Compile
		if (this.compileCode()) {
			// RED-Phase
			Color tempPhase = phase;
			if (phase.equals(Color.RED)) {

				// Condition to get to the next Phase:
				// Have compiling code but erroring test(s)
				if (compiler.codeCompilesAndDoesNotFulfillTests()) {
					phase = Color.GREEN;
					tddttimer.changeToCodingTimer();
				}
				// GREEN-PHASE
			} else if (phase.equals(Color.GREEN)) {

				// Condition to get to the next Phase:
				// Have compiling code and satisfied tests!
				if (compiler.codeCompilesAndFulfillsTests()) {
					phase = Color.BLACK;
					tddttimer.changeToRefactorTimer();
				}
				// Refactor-Phase
			} else if (phase.equals(Color.BLACK)) {

				// Condition to get to the next Phase:
				// Have compiling code and satisfied tests!
				if (compiler.codeCompilesAndFulfillsTests()) {
					phase = Color.RED;
					tddttimer.changeToTestingTimer();
				}
			}
			if (babystepsCheckBox.isSelected() && timer != null) { // Babysteps:
																	// now
																	// next
																	// timer
				if (!phase.equals(tempPhase)) { // Next step button successful
					if (phase.equals(Color.RED)) {
						timer.startTestingTimer();
					} else if (phase.equals(Color.GREEN)) {
						timer.startCodingTimer();
					}
				}
			}
		}
	}

	/**
	 * Compiles the code and checks if it compiles without severe errors.
	 * 
	 * @return True if no severe errors detected.
	 */
	private boolean compileCode() {
		outputArea.setText("");
		// Check if it can be a valid class
		if (!testArea.getText().contains("public class")) {
			outputArea.setText("Die Test-Klasse enthält noch kein 'public class'.");
		} else if (!codeArea.getText().contains("public class")) {
			outputArea.setText("Die Code-Klasse enthält noch kein 'public class'.");
			// Class name missing
		} else if (testArea.getText().indexOf("{") < 14) {
			outputArea.setText("Bitte einen Klassennamen für die Test-Klasse angeben.");
		} else if (codeArea.getText().indexOf("{") < 14) {
			outputArea.setText("Bitte einen Klassennamen für die Code-Klasse angeben.");
			//Tests missing
		} else if(!testArea.getText().contains("@Test")){
			outputArea.setText("Keine Tests vorhanden.");
		} else {
			compiler = new KataLiveCompiler(codeArea.getText(), testArea.getText());
			outputArea.setText(compiler.getErrors());
			return true;
		}
		return false;
	}

}
