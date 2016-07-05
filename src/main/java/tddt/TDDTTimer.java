package tddt;

import java.util.Timer;

public class TDDTTimer {
	
	private Timer timer;
	
	private TDDTTimerTask red, green, refactor;
	
	public TDDTTimer() {
		red = new TDDTTimerTask();
		green = new TDDTTimerTask();
		refactor = new TDDTTimerTask();
	}
	
	public void changeToRedTimer() {
		timer.cancel();
		timer = new Timer();
		timer.schedule(red, 0, 1000);
	}
	
	public void changeToGreenTimer() {
		timer.cancel();
		timer = new Timer();
		timer.schedule(green, 0, 1000);
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
		return new int[] {red.getTime(), green.getTime(), refactor.getTime()};
	}
	
}
