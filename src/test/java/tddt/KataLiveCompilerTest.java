package tddt;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class KataLiveCompilerTest {

	@Test public void compilesAndTestsCorrectClassesAndCorrectTests(){
		KataLiveCompiler compiler = new KataLiveCompiler();
		boolean correct = compiler.codeFulfillsTests("TwentyFour",
														"public class TwentyFour { \n"
															+ " public static int twentyFour() { \n"
															+ "    return 24; \n"
															+ " }\n"
															+ "}",
													"TwentyFourTest",
														"import static org.junit.Assert.*;\n"
															+ "import org.junit.Test;\n"
															+ "public class TwentyFourTest { \n"
															+ "   @Test\n"
															+ "   public void isItReallyTwentyFour() { \n "
															+ "       assertEquals(24, TwentyFour.twentyFour()); \n"
															+ "   }\n "
															+ "}");
		assertTrue("correct class and correct tests should return 'true'", correct);
	}
	
}
