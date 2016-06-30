package tddt;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.JavaStringCompiler;

public class KataLiveCompiler {

	/**
	 * Compiles the two given sources and tests if all tests are fulfilled.
	 * Useful for the GREEN-Phase.
	 * @param codeClassName The name of the class to test
	 * @param codeClassSourcecode The sourcecode of the class to test
	 * @param testClassName The name of the class which contains the tests
	 * @param testClassSourcecode The sourcecode of the class which contains the tests
	 * @return True if and only if both codes have no compiler errors and all tests are satisfied.
	 */
	public boolean codeCompilesAndFulfillsTests(String codeClassName, String codeClassSourcecode, String testClassName, String testClassSourcecode){
		JavaStringCompiler compiler = this.createCompilerAndCompiler(codeClassName, codeClassSourcecode, testClassName, testClassSourcecode);
		return !compiler.getCompilerResult().hasCompileErrors() && compiler.getTestResult().getNumberOfFailedTests() == 0;
	}
	
	/**
	 * Compiles the two given sources and tests if all tests are not fulfilled.
	 * Useful for the RED-Phase.
	 * @param codeClassName The name of the class to test
	 * @param codeClassSourcecode The sourcecode of the class to test
	 * @param testClassName The name of the class which contains the tests
	 * @param testClassSourcecode The sourcecode of the class which contains the tests
	 * @return True if and only if both codes have no compiler errors and some tests are not satisfied.
	 */
	public boolean codeCompilesAndDoesNotFulfillTests(String codeClassName, String codeClassSourcecode, String testClassName, String testClassSourcecode){
		JavaStringCompiler compiler = this.createCompilerAndCompiler(codeClassName, codeClassSourcecode, testClassName, testClassSourcecode);
		return !compiler.getCompilerResult().hasCompileErrors() && compiler.getTestResult().getNumberOfFailedTests() > 0;
	}
	
	/**
	 * Compiles the two given sources, runs the tests and hands over the compiler.
	 * @param codeClassName The name of the class to test
	 * @param codeClassSourcecode The sourcecode of the class to test
	 * @param testClassName The name of the class which contains the tests
	 * @param testClassSourcecode The sourcecode of the class which contains the tests
	 * @return The compiler which already has run the code.
	 */
	private JavaStringCompiler createCompilerAndCompiler(String codeClassName, String codeClassSourcecode, String testClassName, String testClassSourcecode){
		CompilationUnit codeClass = new CompilationUnit(codeClassName, codeClassSourcecode, false);
		CompilationUnit testClass = new CompilationUnit(testClassName, testClassSourcecode, false);
		JavaStringCompiler compiler = CompilerFactory.getCompiler(codeClass, testClass);
		compiler.compileAndRunTests();
		return compiler;
	}
}
