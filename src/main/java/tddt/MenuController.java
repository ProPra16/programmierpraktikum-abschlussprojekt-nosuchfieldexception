package tddt;

import java.util.HashMap;

import babysteps.BabystepsCycle;
import babysteps.BabystepsUser;
import babysteps.CustomTimer;
import babysteps.TDDCycle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
	private CheckBox babystepsCheckBox;

	@FXML
	private TextField timeTextField;
	
	@FXML
	private Button backToRedButton;

	private TDDTTimer tddttimer = new TDDTTimer();
	
	private HashMap<String, Integer> compileErrors = new HashMap<>();

	@FXML
	public void handleLoadButton() {

	}

	@FXML
	public void handleSaveButton() {

	}

	@FXML
	public void handleStartButton() {
		// TDDCycle cycle = new BabystepsCycle(); //Wherever it comes from? Just
		// for progress
		if (babystepsCheckBox.isSelected()) { // Babysteps!
			long time = (long) (Double.parseDouble(timeTextField.getText(0, timeTextField.getText().length() - 3)) * 1000 * 60);
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
			}, time, time); // Missing 2nd field

			if (phase.equals(Color.RED)) { // For example
				timer.startTestingTimer();
			} else if (phase.equals(Color.GREEN)) {
				timer.startCodingTimer();
			}
			Timeline refreshTimer = new Timeline(new KeyFrame(
			        Duration.millis(10),
			        ae -> outputArea.setText(babysteps.Utils.millisecondsToTimerString(timer.getRemaingTestingTime()))));
			refreshTimer.setCycleCount(Timeline.INDEFINITE);
			refreshTimer.play();
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
				tddttimer.addTestTime();
				switchToGreenPhase();
				// GREEN-PHASE
			} else if (phase.equals(Color.GREEN)) {
				tddttimer.addCodeTime();
				switchToRefactorPhase();
				// REFACTOR-Phase
			} else if (phase.equals(Color.BLACK)) {
				tddttimer.addCodeTime();
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
			//System.out.println(tddttimer.getTimes()[0]);
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
			//System.out.println(tddttimer.getTimes()[1]);
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
			//System.out.println(tddttimer.getTimes()[2]);
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
			tddttimer.addCodeTime();
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
			//System.out.println(tddttimer.getTimes()[1]);
		} else {
			outputArea.setText(outputArea.getText() + "\nVon hier aus geht es nicth zu RED zurück :)");
		}
	}
}
=======
package tddt;

import babysteps.BabystepsUser;
import babysteps.CustomTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
	private Spinner<Integer> timeSpinner;

	@FXML
	private Label timeOutLabel;

	@FXML
	private Button backToRedButton;

	@FXML
	private Button saveButton;

	@FXML
	private Button startButton;

	@FXML
	private Button nextStepButton;

	private TDDTTimer tddttimer;

	private boolean babystepsEnabled;
	private String latestTestString;
	private String latestCodeString;
	private Timeline refreshTimer;

	@FXML
	public void handleLoadButton() {
		// Handle file
		FileHandling fileHandling = new FileHandling();
		codeArea.setText(fileHandling.readClass());
		testArea.setText(fileHandling.readTest());
		taskArea.setText(fileHandling.readTask());
		// Activate Buttons/TextAreas
		saveButton.setDisable(false);
		startButton.setDisable(false);
		nextStepButton.setDisable(false);
		testArea.setEditable(true);

		latestTestString = testArea.getText();
		latestCodeString = codeArea.getText();
	}

	@FXML
	public void handleSaveButton() {
		FileHandling.saveFile(codeArea.getText(), testArea.getText(), taskArea.getText());
	}

	@FXML
	public void handleStartButton() {
		babystepsEnabled = true;
		startButton.setDisable(true);
		long time = timeSpinner.getValue() * 60 * 1000;
		refreshTimer = new Timeline();
		timer = new CustomTimer(new BabystepsUser() {

			@Override
			public void notifyTimerElapsed() {
				timer.stopTimer();
				refreshTimer.stop();
				timeOutLabel.setText("00:00");
				babystepsEnabled = false;
				startButton.setDisable(false);
				outputArea.appendText(
						"Babysteps Timer abgelaufen. Setze Änderungen zurück...\nTimer gestoppt. Drücke Start um weiter Babysteps zu nutzen\n");
				if (phase.equals(Color.RED)) {
					testArea.setText(latestTestString);
					// GREEN-PHASE
				} else if (phase.equals(Color.GREEN)) {
					codeArea.setText(latestCodeString);
					// REFACTOR-Phase
				} else if (phase.equals(Color.BLACK)) {
					// Hm nö
					outputArea.appendText("\nMagikarp used SPLASH!\n But nothing happenend!\n");
				}
			}
			
		}, time, time); // Missing 2nd field

		if (phase.equals(Color.RED) || phase.equals(Color.GREEN)) { // For example
			timer.startTimer();
		}
		refreshTimer.getKeyFrames().add(new KeyFrame(Duration.millis(10),
				ae -> timeOutLabel.setText(babysteps.Utils.millisecondsToTimerString(timer.getRemainingTime()))));
		refreshTimer.setCycleCount(Timeline.INDEFINITE);
		refreshTimer.play();
	}

	@FXML
	public void handleNextStepButton() {
		// Check & Compile
		compiler = KataLiveCompiler.constructCompiler(testArea.getText(), codeArea.getText(), outputArea);
		if (compiler != null) {
				// RED-Phase
			if (phase.equals(Color.RED)) {
				switchToGreenPhase();
				if (babystepsEnabled && phase.equals(Color.GREEN)) {
					timer.stopTimer();
					timer.startTimer();
					refreshTimer.playFromStart();
				}
				// GREEN-PHASE
			} else if (phase.equals(Color.GREEN)) {
				switchToRefactorPhase();
				if (babystepsEnabled && phase.equals(Color.BLACK)) {
					refreshTimer.stop();
					timer.stopTimer();
				}
				// REFACTOR-Phase
			} else if (phase.equals(Color.BLACK)) {
				switchToRedPhase();
				if (babystepsEnabled && phase.equals(Color.RED)) {
					timer.stopTimer();
					timer.startTimer();
					refreshTimer.playFromStart();
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
			outputArea.appendText("Bedingung erfüllt. Willkommen in der GREEN-Phase:\n"
					+ "Den fehlschlagenden Test erfüllen :)\n");
			// Activate/Deactivate TextAreas/Buttons
			testArea.setEditable(false);
			codeArea.setEditable(true);
			backToRedButton.setDisable(false);
			//Save the code class code
			oldCodeClass = codeArea.getText();
			latestTestString = testArea.getText();
			// tddttimer.changeToCodingTimer();
		} else {
			// Requirements not met
			outputArea.appendText("Code erfüllt nicht die Bedingung um in die GREEN-Phase zu wechseln:"
					+ "\nEs muss genau ein Test fehlschlagen, oder der Code nicht kompilieren\n");
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
			outputArea.appendText("Bedingung erfüllt. Willkommen in der REFACTOR-Phase:\n"
					+ "Code verbessern falls gewünscht, ansonsten einfach Next Step! :)\n");
			// Activate/Deactivate TextAreas/Buttons
			testArea.setEditable(false);
			codeArea.setEditable(true);
			backToRedButton.setDisable(true);
			
			latestCodeString = codeArea.getText();
			// tddttimer.changeToRefactorTimer();
		} else {
			// Requirements not met
			outputArea.appendText("Code erfüllt nicht die Bedingung um in die REFACTOR-Phase zu wechseln:"
					+ "\nAlle Tests müssn erfüllt werden.\n");
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
			outputArea.appendText(
					"Bedingung erfüllt. Willkommen in der RED-Phase:\n" 
					+ "Einen fehlschlagenden Test schreiben, oder nicht kompilierbaren Code schreiben :)\n");
			// Activate/Deactivate TextAreas
			testArea.setEditable(true);
			codeArea.setEditable(false);
			backToRedButton.setDisable(true);
			// tddttimer.changeToTestingTimer();
		} else {
			// Requirements not met
			outputArea.appendText("Code erfüllt nicht die Bedingung um in die RED-Phase zu wechseln:"
					+ "\nNach dem Refactoren müssen immer noch alle Tests erfüllt werden.\n");
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
			outputArea.setText("Willkommen zurück in der RED-Phase:\n"
					+ "Einen fehlschlagenden Test schreiben :)\n");
			// Activate/Deactivate TextAreas/Buttons
			testArea.setEditable(true);
			codeArea.setEditable(false);
			backToRedButton.setDisable(true);
			//Reset code class code
			codeArea.setText(oldCodeClass);

			if (babystepsEnabled) {
				timer.stopTimer();
				timer.startTimer();
				refreshTimer.playFromStart();
			}
			// tddttimer.changeToTestingTimer();
		} else {
			outputArea.appendText("\nVon hier aus geht es nicht zu RED zurück :)");
		}
	}
}
