package babysteps;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CustomTimer implements BabystepsTimer {
	private Timer timer;
	private long timeStarted;
	private final long time;
	private ArrayList<BabystepsUser> userList;
	
	public CustomTimer(long testingTime, long codingTime) {
		userList = new ArrayList<BabystepsUser>();
		timer = new Timer();
		this.time = testingTime;
	}
	
	public CustomTimer(BabystepsUser user, long testingTime, long codingTime) {
		this(testingTime, codingTime);
		registerBabystepsUser(user);
	}
	
	@Override
	public long getDuration() {
		return time;
	}

	@Override
	public long getRemainingTime() {
		return time-(System.currentTimeMillis()-timeStarted);
	}

	@Override
	public void startTimer() {
		System.out.println("new Timer started");
		timeStarted = System.currentTimeMillis();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				notifyAllUsersTimeElapsed();
				stopTimer();
			}
		}, getDuration());
	}

	@Override
	public void stopTimer() {
		System.out.println("timer stopped");
		timer.cancel();
		timer = new Timer();
	}

	@Override
	public void registerBabystepsUser(BabystepsUser user) {
		userList.add(user);
	}
	
	public void notifyAllUsersTimeElapsed() {
		for(int i = 0; i < userList.size(); i++) {
			userList.get(i).notifyTimerElapsed();
		}
	}
}
