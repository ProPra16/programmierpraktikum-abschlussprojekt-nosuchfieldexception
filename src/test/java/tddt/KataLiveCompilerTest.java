package tddt;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class KataLiveCompilerTest {

	@Test public void compilesAndTestsCorrectClassesAndCorrectTests(){
		KataLiveCompiler compiler = new KataLiveCompiler("TwentyFour",
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
		boolean correct = compiler.codeCompilesAndFulfillsTests();
		assertTrue("correct class and correct tests should return 'true'", correct);
	}
	
	@Test public void compilesAndTestsCorrectClassesAndUnfulfillingTests(){
		KataLiveCompiler compiler = new KataLiveCompiler("TwentyFour",
				"public class TwentyFour { \n"
						+ " public static int twentyFour() { \n"
						+ "    return 27; \n"
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
		boolean wrong = compiler.codeCompilesAndDoesNotFulfillTests();
		assertTrue("correct class and wrong tests should return 'false'", !wrong);
	}
	
	@Test public void compilerCreatesTheCorrectErrorString(){
		KataLiveCompiler compiler = new KataLiveCompiler("TwentyFour",
				"public class TwentyFour { \n"
						+ " public static int twentyFour() { \n"
						+ "    return 24; \n"
						+ " }\n"
						+ "}",
				"TwentyFourTest",
					"import static org.junit.Assert.*;\n"
						+ "import org.junit.Test;\n"
						+ "public class TwentyFiveTest { \n"
						+ "   @Test\n"
						+ "   public void isItReallyTwentyFour() { \n "
						+ "       assertEquals(24, TwentyFour.twentyFour()); \n"
						+ "   }\n "
						+ "}");
		String errors = compiler.getErrors();
		assertTrue("There is an error therefore the String should not be positive", errors != "No compile-error detected, good job! :D");
	}
	
	@Test public void compilerCreatesTheCorrectEmptyErrorString(){
		KataLiveCompiler compiler = new KataLiveCompiler("TwentyFour",
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
		String errors = compiler.getErrors();
		assertTrue("There is no error therefore the String should be positive", errors == "No compile-error detected, good job! :D");
	}
	
}
