package tddt;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class KataLiveCompilerTest {

	@Test public void compilerCompilesCorrectClass() {
        KataLiveCompiler compiler = new KataLiveCompiler();
        assertTrue("correct class should return 'true'", compiler.doesThisCompile("Test", "public class Test {}"));
    }
	
}
