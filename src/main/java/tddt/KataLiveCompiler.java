package tddt;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.JavaStringCompiler;

public class KataLiveCompiler {
	
	public boolean doesThisCompile(String classname, String sourcecode){
		CompilationUnit classToCompile = new CompilationUnit(classname, sourcecode, false);
		JavaStringCompiler compiler = CompilerFactory.getCompiler(classToCompile);
		compiler.compileAndRunTests();
		compiler.getCompilerResult();
		return !compiler.getCompilerResult().hasCompileErrors();
	}
	
}
