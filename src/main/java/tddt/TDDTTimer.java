package tddt;

public class TDDTTimer {
	
	static long test, code, refactor;
	private long startTime;
	
	
	public TDDTTimer() {
		test = 0;
		code = 0;
		refactor = 0;
		startTime = System.currentTimeMillis();
	}
	
	public void addTestTime() {
		test += System.currentTimeMillis() - startTime;
		startTime = System.currentTimeMillis();
	}
	
	public void addCodeTime() {
		code += System.currentTimeMillis() - startTime;
		startTime = System.currentTimeMillis();
	}
	
	public void addRefactorTime() {
		refactor += System.currentTimeMillis() - startTime;
		startTime = System.currentTimeMillis();
	}
	
}
