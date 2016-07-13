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
		//Handle file
		FileHandling fileHandling = new FileHandling();
		codeArea.setText(fileHandling.readClass());
		testArea.setText(fileHandling.readTest());
		taskArea.setText(fileHandling.readTask());
		//Activate Buttons/TextAreas
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
				outputArea.appendText("Babysteps Timer abgelaufen. Setze Änderungen zurück...\nTimer gestoppt. Drücke Start um weiter Babysteps zu nutzen\n");
				if (phase.equals(Color.RED)) {
					testArea.setText(latestTestString);
					// GREEN-PHASE
				} else if (phase.equals(Color.GREEN)) {
					codeArea.setText(latestCodeString);
					// REFACTOR-Phase
				} else if (phase.equals(Color.BLACK)) {
					//Hm nö
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
				if(babystepsEnabled && phase.equals(Color.GREEN)) {
					timer.stopTimer();
					timer.startTimer();
					refreshTimer.playFromStart();
				}
				// GREEN-PHASE
			} else if (phase.equals(Color.GREEN)) {
				switchToRefactorPhase();
				if(babystepsEnabled && phase.equals(Color.BLACK)) {
					refreshTimer.stop();
					timer.stopTimer();
				}
				// REFACTOR-Phase
			} else if (phase.equals(Color.BLACK)) {
				switchToRedPhase();
				if(babystepsEnabled && phase.equals(Color.RED)) {					
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
			outputArea.setText("Bedingung erfüllt. Willkommen in der GREEN-Phase:\n"
					+ "Den fehlschlagenden Test erfüllen :)");
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
			outputArea.appendText(
					  "Code erfüllt nicht die Bedingung um in die GREEN-Phase zu wechseln:"
					+ "\nEs muss genau ein Test fehlschlagen\n");
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
			outputArea.setText("Bedingung erfüllt. Willkommen in der REFACTOR-Phase:\n"
					+ "Code verbessern falls gewünscht, ansonsten einfach Next Step!\n");
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
			outputArea.appendText("Bedingung erfüllt. Willkommen in der RED-Phase:\n"
					+ "Einen fehlschlagenden Test schreiben.\n");
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
			outputArea.setText("\nWillkommen zurück in der RED-Phase:\n"
					+ "Einen fehlschlagenden Test schreiben :)");
			// Activate/Deactivate TextAreas/Buttons
			testArea.setEditable(true);
			codeArea.setEditable(false);
			backToRedButton.setDisable(true);
			//Reset code class code
			codeArea.setText(oldCodeClass);
			
			if(babystepsEnabled) {
				timer.stopTimer();
				timer.startTimer();
				refreshTimer.playFromStart();
			}
			// tddttimer.changeToTestingTimer();
		} else {
			outputArea.setText(outputArea.getText() + "\nVon hier aus geht es nicth zu RED zurück :)");
		}
	}
}
