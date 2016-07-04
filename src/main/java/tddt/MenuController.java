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
		if(babystepsCheckBox.isSelected() && timer != null) timer.stopAll();
		this.compileCode();
		//RED-Phase
		Color tempPhase = phase;
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
		if(babystepsCheckBox.isSelected() && timer != null) { //Babysteps: now next timer
			if(!phase.equals(tempPhase)) { //Next step button successful
				if(phase.equals(Color.RED)){
					timer.startTestingTimer();
				}
				else if(phase.equals(Color.GREEN)){
					timer.startCodingTimer();
				}
			}
		}
	}

	private void compileCode() {
		// Check if it can be a valid class
		if (!codeArea.getText().contains("class")) {
			outputArea.setText("Die Code-Klasse ist noch keine Klasse...");
		}
		if (!testArea.getText().contains("class")) {
			outputArea.setText(outputArea.getText() + "\nDie Test-Klasse ist noch keine Klasse...");
		} else {
			KataLiveCompiler compiler = new KataLiveCompiler(codeArea.getText(), testArea.getText());
			outputArea.setText(compiler.getErrors());
		}
	}

}