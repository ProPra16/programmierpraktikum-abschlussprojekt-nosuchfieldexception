package tddt;

import java.util.ArrayList;
import java.util.Collection;

import javafx.scene.control.TextArea;
import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestFailure;
import vk.core.api.TestResult;

public class KataLiveCompiler {

	private final JavaStringCompiler compiler;
	private final CompilationUnit codeClass, testClass;

	/**
	 * Creates a new Instance of KataLiveCompiler, compiles the code and runs
	 * the tests.
	 * 
	 * @param codeClassSourcecode
	 *            The sourcecode of the class to test
	 * @param testClassSourcecode
	 *            The sourcecode of the class which contains the tests
	 */
	public KataLiveCompiler(String codeClassSourcecode, String testClassSourcecode) {
		codeClass = new CompilationUnit(this.getClassName(codeClassSourcecode), codeClassSourcecode, false);
		testClass = new CompilationUnit(this.getClassName(testClassSourcecode), testClassSourcecode, true);
		compiler = CompilerFactory.getCompiler(codeClass, testClass);
		compiler.compileAndRunTests();
	}

	/**
	 * Constructs a new KataLiveCompiler and checks before that, if the Code can
	 * be compiled without severe errors.
	 * 
	 * @param inputTest
	 *            The Test-Class-Code to compile
	 * @param inputCode
	 *            The Code-Class-Code to compile
	 * @param outputArea
	 *            The TextArea to write errors on
	 * @return A new Compiler if there are no severe errors, else returns null
	 */
	public static KataLiveCompiler constructCompiler(String inputTest, String inputCode, TextArea outputArea) {
		outputArea.setText("");
		// Check if it can be a valid class
		if (!inputTest.contains("public class")) {
			outputArea.setText("Die Test-Klasse enthält noch kein 'public class'.");
		} else if (!inputCode.contains("public class")) {
			outputArea.setText("Die Code-Klasse enthält noch kein 'public class'.");
			// Class name missing
		} else if (inputTest.indexOf("{") < 14) {
			outputArea.setText("Bitte einen Klassennamen für die Test-Klasse angeben.");
		} else if (inputCode.indexOf("{") < 14) {
			outputArea.setText("Bitte einen Klassennamen für die Code-Klasse angeben.");
			// Tests missing
		} else if (!inputTest.contains("@Test")) {
			outputArea.setText("Keine Tests vorhanden.");
			//Duplicate class names
		}else if(getClassName(inputTest).equals(getClassName(inputCode))){
			outputArea.setText("Die Klassen müssen unterschiedliche Namen haben!");
		} else {
			KataLiveCompiler newCompiler = new KataLiveCompiler(inputCode, inputTest);
			outputArea.setText(newCompiler.getErrors() + newCompiler.getFailedTestMessages());
			return newCompiler;
		}
		return null;
	}
	
	
	/**
	 * Checks if the compiled code contains errors
	 * @return True if there are non compile-errors.
	 */
	public boolean codeCompiles(){
		try {
			return !compiler.getCompilerResult().hasCompileErrors();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Compiles the two given sources and tests if all tests are fulfilled.
	 * Useful for the GREEN-Phase.
	 * 
	 * @return True if and only if both codes have no compiler errors and all
	 *         tests are satisfied.
	 */
	public boolean codeCompilesAndFulfillsTests() {
		try {
			return codeCompiles()
					&& compiler.getTestResult().getNumberOfFailedTests() == 0;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Compiles the two given sources and tests if all tests are fulfilled.
	 * Useful for the RED-Phase.
	 * 
	 * @return True if and only if both codes have no compiler errors and
	 *         exactly one test ist not satisfied.
	 */
	public boolean codeCompilesAndDoesNotFulfillOneTest() {
		try {
			return codeCompiles()
					&& compiler.getTestResult().getNumberOfFailedTests() == 1;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Builds a String composed of all Test-Errors and returns them with a new
	 * line at the end of each error.
	 * 
	 * @return The String of Test-Errors
	 */
	public String getFailedTestMessages() {
		try {
			String errors = "";
			TestResult result = compiler.getTestResult();
			for (TestFailure failure : result.getTestFailures()) {
				errors += "Testmethode '" + failure.getMethodName() + "' ist fehlgeschlagen: " + failure.getMessage()
						+ "\n";
			}
			// String empty = All tests happy
			if (errors.equals("")) {
				return "Keine fehlschlagenden Tests!\n";
			}
			return errors;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Creates a big String containing all errors Messages that ocurred.
	 * 
	 * @return A String containing all compiler errors or, if there were none a
	 *         positive message
	 */
	public String getErrors() {
		CompilerResult result = compiler.getCompilerResult();
		if (result.hasCompileErrors()) {
			String errorString = "";
			// Fetch testClass compile-errors
			Collection<CompileError> testClassErrors = result.getCompilerErrorsForCompilationUnit(testClass);
			for (CompileError error : testClassErrors) {
				errorString += "Fehler in der Test-Klasse: \n" + "Zeile " + error.getLineNumber() + ": "
						+ error.getMessage() + "\n";
			}
			// Fetch codeClass compile-errors
			Collection<CompileError> codeClassErrors = result.getCompilerErrorsForCompilationUnit(codeClass);
			for (CompileError error : codeClassErrors) {
				errorString += "Fehler in der Code-Klasse: \n" + "Zeile " + error.getLineNumber() + ": "
						+ error.getMessage() + "\n";
			}
			return errorString;
		} else {
			return "Code kompiliert einwandfrei, gute Arbeit :D\n";
		}
	}

	/**
	 * Searches for the lines in the code, where the error ocurred
	 * 
	 * @return An ArrayList containing als errored lines of code
	 */
	public ArrayList<String> getErroredLines() {
		CompilerResult result = compiler.getCompilerResult();
		ArrayList<String> errorLines = new ArrayList<>();
		if (result.hasCompileErrors()) {
			// Fetch testClass compile-errors-lines
			Collection<CompileError> testClassErrors = result.getCompilerErrorsForCompilationUnit(testClass);
			for (CompileError error : testClassErrors) {
				errorLines.add(error.getLineNumber() + ": " + error.getCodeLineContainingTheError());
			}
			// Fetch codeClass compile-error-lines
			Collection<CompileError> codeClassErrors = result.getCompilerErrorsForCompilationUnit(codeClass);
			for (CompileError error : codeClassErrors) {
				errorLines.add(error.getLineNumber() + ": " + error.getCodeLineContainingTheError());
			}
		}
		return errorLines;
	}

	/**
	 * Returns the name of the class, by looking in the sourcecode
	 * 
	 * @param classCode
	 *            The sourcecode of the class to find a name for
	 * @return The name of the Class.
	 */
	public static String getClassName(String classCode) {
		String className = "";
		// Locate Classname
		int indexBeginName = classCode.indexOf("class") + 5;
		int indexEndName = classCode.indexOf("{");
		// Cut String if possible
		try {
			className = classCode.substring(indexBeginName, indexEndName);
		} catch (StringIndexOutOfBoundsException e) {
			className = "InvalidClassError";
		}
		// Remove whitespace
		className = className.replace(" ", "");
		return className;
	}

	/**
	 * Returns a Collection of Errors
	 * 
	 * @return Errors of the test class
	 */
	public Collection<CompileError> getTestClassErrors() {
		return compiler.getCompilerResult().getCompilerErrorsForCompilationUnit(testClass);
	}

	/**
	 * Returns a Collection of Errors
	 * 
	 * @return Errors of the code class
	 */
	public Collection<CompileError> getCodeClassErrors() {
		return compiler.getCompilerResult().getCompilerErrorsForCompilationUnit(codeClass);
	}
}
