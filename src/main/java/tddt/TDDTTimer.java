package tddt;

public class TDDTTimer {
	
	private long test, code, refactor, startTime;
	
	
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
	
	public long[] getTimes() {
		return new long[] {test, code, refactor};
	}
	
}
