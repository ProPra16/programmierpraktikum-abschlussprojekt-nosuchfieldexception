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
	 * Creates a new Instance of KataLiveCompiler, compiles the code and runs the tests.
	 * @param codeClassName The name of the class to test
	 * @param codeClassSourcecode The sourcecode of the class to test
	 * @param testClassName The name of the class which contains the tests
	 * @param testClassSourcecode The sourcecode of the class which contains the tests
	 */
	public KataLiveCompiler(String codeClassName, String codeClassSourcecode, String testClassName, String testClassSourcecode){
		codeClass = new CompilationUnit(codeClassName, codeClassSourcecode, false);
		testClass = new CompilationUnit(testClassName, testClassSourcecode, false);
		compiler = CompilerFactory.getCompiler(codeClass, testClass);
		compiler.compileAndRunTests();
	}

	/**
	 * Compiles the two given sources and tests if all tests are fulfilled.
	 * Useful for the GREEN-Phase.
	 * @return True if and only if both codes have no compiler errors and all tests are satisfied.
	 */
	public boolean codeCompilesAndFulfillsTests(){
		return !compiler.getCompilerResult().hasCompileErrors() && compiler.getTestResult().getNumberOfFailedTests() == 0;
	}
	
	/**
	 * Compiles the two given sources and tests if all tests are not fulfilled.
	 * Useful for the RED-Phase.
	 * @return True if and only if both codes have no compiler errors and some tests are not satisfied.
	 */
	public boolean codeCompilesAndDoesNotFulfillTests(){
		return !compiler.getCompilerResult().hasCompileErrors() && compiler.getTestResult().getNumberOfFailedTests() > 0;
	}
	
	/**
	 * Creates a big String containing all errors Messages that ocurred.
	 * @return A String containing all compiler errors or, if there were none a positive message
	 */
	public String getErrors(){
		CompilerResult result = compiler.getCompilerResult();
		if(result.hasCompileErrors()){
			String errorString = "";
			//Fetch codeClass compile-errors
			Collection<CompileError> codeClassErrors = result.getCompilerErrorsForCompilationUnit(codeClass);
			for(CompileError error : codeClassErrors){
				errorString += error.getMessage();
			}
			//Fetch testClass compile-errors
			Collection<CompileError> testClassErrors = result.getCompilerErrorsForCompilationUnit(testClass);
			for(CompileError error : testClassErrors){
				errorString += error.getMessage();
			}
			return errorString;
		}else{
			return "No compile-error detected, good job! :D";
		}
	}
	
	/**
	 * Searches for the lines in the code, where the error ocurred
	 * @return An ArrayList containing als errored lines of code
	 */
	public ArrayList<String> getErroredLines(){
		CompilerResult result = compiler.getCompilerResult();
		ArrayList<String> errorLines = new ArrayList<>();
		if(result.hasCompileErrors()){
			//Fetch codeClass compile-error-lines
			Collection<CompileError> codeClassErrors = result.getCompilerErrorsForCompilationUnit(codeClass);
			for(CompileError error : codeClassErrors){
				errorLines.add(error.getCodeLineContainingTheError());
			}
			//Fetch testClass compile-errors-lines
			Collection<CompileError> testClassErrors = result.getCompilerErrorsForCompilationUnit(testClass);
			for(CompileError error : testClassErrors){
				errorLines.add(error.getCodeLineContainingTheError());
			}
		}
		return errorLines;
	}

}
