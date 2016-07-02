package tddt;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
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
	public void handleLoadButton() {

	}

	@FXML
	public void handleSaveButton() {

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
			if(compiler.codeCompilesAndDoesNotFulfillTests()){
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
