package tddt;

import java.util.Timer;

public class TDDTTimer {
	
	private Timer timer;
	
	private TDDTTimerTask test, code, refactor;
	
	public TDDTTimer() {
		test = new TDDTTimerTask();
		code = new TDDTTimerTask();
		refactor = new TDDTTimerTask();
	}
	
	public void changeToTestingTimer() {
		timer.cancel();
		timer = new Timer();
		timer.schedule(test, 0, 1000);
	}
	
	public void changeToCodingTimer() {
		timer.cancel();
		timer = new Timer();
		timer.schedule(code, 0, 1000);
	}
	
	public void changeToRefactorTimer() {
		timer.cancel();
		timer = new Timer();
		timer.schedule(refactor, 0, 1000);
	}
	
	public void stop() {
		timer.cancel();
	}
	
	public int[] getTimes() {
		return new int[] {test.getTime(), code.getTime(), refactor.getTime()};
	}
	
}
