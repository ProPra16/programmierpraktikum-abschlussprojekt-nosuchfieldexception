package tddt;

public class TDDTTimer {
	
	private StopWatch redTimer;
	
	private StopWatch greenTimer;
	
	private StopWatch refactorTimer;
	
	
	public TDDTTimer() {
		redTimer = new StopWatch();
		greenTimer = new StopWatch();
		refactorTimer = new StopWatch();
		
		redTimer.start();
		greenTimer.start();
		greenTimer.suspend();
		refactorTimer.start();
		refactorTimer.suspend();
	}
	
	public changeToRedTimer() {
		redTimer.resume();
		greenTimer.suspend();
		refactorTimer.suspend();
	}
	
	public changeToGreenTimer() {
		redTimer.suspend();
		greenTimer.resume();
		refactorTimer.suspend();
	}
	
	public changeToRefactorTimer() {
		redTimer.suspend();
		greenTimer.suspend();
		refactorTimer.resume();
	}
	
	public void stop() {
		redTimer.stop();
		greenTimer.stop();
		refactorTimer.stop();
	}
	
	public String[] getTimer() {
		String[] allTimer = new String[3];
		allTimer[0] = redTimer.toString();
		allTimer[1] = greenTimer.toString();
		allTimer[2] = refactorTimer.toString();
	}
	
}