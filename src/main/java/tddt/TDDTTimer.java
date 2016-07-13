package tddt;

public class TDDTTimer {
	
	private long test, code, refactor;
	
	
	public TDDTTimer() {
		test = 0;
		code = 0;
		refactor = 0;
	}
	
	public void addTestTime() {
		
	}
	
	public long[] getTimes() {
		return new long[] {test, code, refactor};
	}
	
}
