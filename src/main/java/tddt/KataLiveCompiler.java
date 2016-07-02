package tddt;

import java.util.ArrayList;
import java.util.Collection;

import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;

public class KataLiveCompiler {

	private JavaStringCompiler compiler;
	private CompilationUnit codeClass, testClass;

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
		testClass = new CompilationUnit(this.getClassName(testClassSourcecode), testClassSourcecode, false);
		compiler = CompilerFactory.getCompiler(codeClass, testClass);
		compiler.compileAndRunTests();
	}

	/**
	 * Compiles the two given sources and tests if all tests are fulfilled.
	 * Useful for the GREEN-Phase.
	 * 
	 * @return True if and only if both codes have no compiler errors and all
	 *         tests are satisfied.
	 */
	public boolean codeCompilesAndFulfillsTests() {
		return !compiler.getCompilerResult().hasCompileErrors()
				&& compiler.getTestResult().getNumberOfFailedTests() == 0;
	}

	/**
	 * Compiles the two given sources and tests if all tests are not fulfilled.
	 * Useful for the RED-Phase.
	 * 
	 * @return True if and only if both codes have no compiler errors and some
	 *         tests are not satisfied.
	 */
	public boolean codeCompilesAndDoesNotFulfillTests() {
		return !compiler.getCompilerResult().hasCompileErrors()
				&& compiler.getTestResult().getNumberOfFailedTests() > 0;
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
			// Fetch codeClass compile-errors
			Collection<CompileError> codeClassErrors = result.getCompilerErrorsForCompilationUnit(codeClass);
			for (CompileError error : codeClassErrors) {
				errorString += "Fehler in der Code-Klasse: \n" + "Zeile " + error.getLineNumber() + ": "
						+ error.getMessage() + "\n";
			}
			// Fetch testClass compile-errors
			Collection<CompileError> testClassErrors = result.getCompilerErrorsForCompilationUnit(testClass);
			for (CompileError error : testClassErrors) {
				errorString += "Fehler in der Test-Klasse: \n" + "Zeile " + error.getLineNumber() + ": "
						+ error.getMessage() + "\n";
			}
			return errorString;
		} else {
			return "No compile-error detected, good job! :D";
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
			// Fetch codeClass compile-error-lines
			Collection<CompileError> codeClassErrors = result.getCompilerErrorsForCompilationUnit(codeClass);
			for (CompileError error : codeClassErrors) {
				errorLines.add(error.getCodeLineContainingTheError());
			}
			// Fetch testClass compile-errors-lines
			Collection<CompileError> testClassErrors = result.getCompilerErrorsForCompilationUnit(testClass);
			for (CompileError error : testClassErrors) {
				errorLines.add(error.getCodeLineContainingTheError());
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
	public String getClassName(String classCode) {
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
}
