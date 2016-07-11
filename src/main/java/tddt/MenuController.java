package tddt;

import babysteps.BabystepsCycle;
import babysteps.BabystepsUser;
import babysteps.CustomTimer;
import babysteps.TDDCycle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuController {

	private KataLiveCompiler compiler;
	private String oldCodeClass;

	/**
	 * Obvious choice, innit?
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
	private TextArea taskArea;

	@FXML
	private CheckBox babystepsCheckBox;

	@FXML
	private TextField timeTextField;
	
	@FXML
	private Button backToRedButton;

	private TDDTTimer tddttimer;

	@FXML
	public void handleLoadButton() {
		FileHandling fileHandling = new FileHandling(new Stage());
		codeArea.setText(fileHandling.readClass());
		testArea.setText(fileHandling.readTest());
		taskArea.setText(fileHandling.readTask());
	}

	@FXML
	public void handleSaveButton() {

	}

	@FXML
	public void handleStartButton() {
		// TDDCycle cycle = new BabystepsCycle(); //Wherever it comes from? Just
		// for progress
		if (babystepsCheckBox.isSelected()) { // Babysteps!
			timer = new CustomTimer(new BabystepsUser() {

				@Override
				public void notifyCodingTimerElapsed() { // Delete and Go back
															// to testing!
					codeArea.setText("time elapsed coding"); // Mit was anderem
																// ersetzen
					// cycle.returnToLastPhase();
					phase = Color.RED;
				}

				@Override
				public void notifiyTestingTimerElapsed() {
					testArea.setText("time elapsed testing");
					// cycle.returnToLastPhase();
					phase = Color.BLACK;
				}
			}, (long) (Double.parseDouble(timeTextField.getText(0, timeTextField.getText().length() - 3)) * 1000 * 60),
					(long) (Double.parseDouble(timeTextField.getText(0, timeTextField.getText().length() - 3)) * 1000
							* 60)); // Missing 2nd field

			if (phase.equals(Color.RED)) { // For example
				timer.startTestingTimer();
			} else if (phase.equals(Color.GREEN)) {
				timer.startCodingTimer();
			}
		} else { // No Babysteps

		}
	}

	@FXML
	public void handleNextStepButton() {
		if (babystepsCheckBox.isSelected() && timer != null)
			timer.stopAll();
		// Check & Compile
		compiler = KataLiveCompiler.constructCompiler(testArea.getText(), codeArea.getText(), outputArea);
		if (compiler != null) {
			// RED-Phase
			Color tempPhase = phase;
			if (phase.equals(Color.RED)) {
				switchToGreenPhase();
				// GREEN-PHASE
			} else if (phase.equals(Color.GREEN)) {
				switchToRefactorPhase();
				// REFACTOR-Phase
			} else if (phase.equals(Color.BLACK)) {
				switchToRedPhase();
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
	 * This code is executed when the user tries to switch to the GREEN-Phase
	 */
	private void switchToGreenPhase(){
		// Condition to get to the next Phase:
		// Have non-compiling code or one failing test
		if (!compiler.codeCompiles() || compiler.codeCompilesAndDoesNotFulfillOneTest()) {
			phase = Color.GREEN;
			// Notify the user
			outputArea.setText(outputArea.getText() + "\nBedingung erfüllt. Willkommen in der GREEN-Phase:\n"
					+ "Den fehlschlagenden Test erfüllen :)");
			// Activate/Deactivate TextAreas/Buttons
			testArea.setEditable(false);
			codeArea.setEditable(true);
			backToRedButton.setDisable(false);
			//Save the code class code
			oldCodeClass = codeArea.getText();
			// tddttimer.changeToCodingTimer();
		} else {
			// Requirements not met
			outputArea.setText(outputArea.getText()
					+ "\nCode erfüllt nicht die Bedingung um in die GREEN-Phase zu wechseln:"
					+ "\nEs muss genau ein Test fehlschlagen");
		}
	}
	
	/**
	 * This code is executed when the user tries to switch to the REFACTOR-Phase
	 */
	private void switchToRefactorPhase(){
		// Condition to get to the next Phase:
		// Have compiling code and satisfied tests!
		if (compiler.codeCompilesAndFulfillsTests()) {
			phase = Color.BLACK;
			// Notify the user
			outputArea.setText(outputArea.getText() + "\nBedingung erfüllt. Willkommen in der REFACTOR-Phase:\n"
					+ "Code verbessern falls gewünscht, ansonsten einfach Next Step! :)");
			// Activate/Deactivate TextAreas/Buttons
			testArea.setEditable(false);
			codeArea.setEditable(true);
			backToRedButton.setDisable(true);
			// tddttimer.changeToRefactorTimer();
		} else {
			// Requirements not met
			outputArea.setText(outputArea.getText()
					+ "\nCode erfüllt nicht die Bedingung um in die REFACTOR-Phase zu wechseln:"
					+ "\nAlle Tests müssn erfüllt werden");
		}
	}
	
	/**
	 * This code is executed when the user tries to switch to the RED-Phase
	 */
	private void switchToRedPhase(){
		// Condition to get to the next Phase:
		// Have compiling code and satisfied tests!
		if (compiler.codeCompilesAndFulfillsTests()) {
			phase = Color.RED;
			// Notify the user
			outputArea.setText(outputArea.getText() + "\nBedingung erfüllt. Willkommen in der RED-Phase:\n"
					+ "Einen fehlschlagenden Test schreiben :)");
			// Activate/Deactivate TextAreas
			testArea.setEditable(true);
			codeArea.setEditable(false);
			backToRedButton.setDisable(true);
			// tddttimer.changeToTestingTimer();
		} else {
			// Requirements not met
			outputArea.setText(
					outputArea.getText() + "\nCode erfüllt nicht die Bedingung um in die RED-Phase zu wechseln:"
							+ "\nNach dem Refactoren müssen immer noch alle Tests erfüllt werden");
		}
	}
	

	@FXML
	/**
	 * This method (and button) is for the case that the user made a mistake in
	 * the RED-Phase and wrote not compileable code (which ist a legimitate way
	 * of enerting the GREEN phase), but the Test-class itself has severe errors
	 * that cant be fixed in the code-class
	 */
	public void handleBackToRedButton() {
		if (phase == Color.GREEN) {
			phase = Color.RED;
			// Notify the user
			outputArea.setText("\nWillkommen zurück in der RED-Phase:\n"
					+ "Einen fehlschlagenden Test schreiben :)");
			// Activate/Deactivate TextAreas/Buttons
			testArea.setEditable(true);
			codeArea.setEditable(false);
			backToRedButton.setDisable(true);
			//Reset code class code
			codeArea.setText(oldCodeClass);
			// tddttimer.changeToTestingTimer();
		} else {
			outputArea.setText(outputArea.getText() + "\nVon hier aus geht es nicth zu RED zurück :)");
		}
	}
}
