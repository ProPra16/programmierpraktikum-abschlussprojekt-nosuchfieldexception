package tddt;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.JavaStringCompiler;

public class KataLiveCompiler {

	public boolean codeFulfillsTests(String codeClassName, String codeClassSourcecode, String testClassName, String testClassSourcecode){
		CompilationUnit codeClass = new CompilationUnit(codeClassName, codeClassSourcecode, false);
		CompilationUnit testClass = new CompilationUnit(testClassName, testClassSourcecode, false);
		JavaStringCompiler compiler = CompilerFactory.getCompiler(codeClass, testClass);
		compiler.compileAndRunTests();
		//Verursacht eine NPE falls Kompilierfehler auftreten
		return compiler.getTestResult().getNumberOfFailedTests() == 0;
	}
}
