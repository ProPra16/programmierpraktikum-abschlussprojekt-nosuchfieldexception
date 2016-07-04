package babysteps;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CustomTimer implements BabystepsTimer {
	private Timer testingTimer, codingTimer;
	private long testingStarted, codingStarted;
	private final long testingTime, codingTime;
	private ArrayList<BabystepsUser> userList;
	
	public CustomTimer(long testingTime, long codingTime) {
		userList = new ArrayList<BabystepsUser>();
		testingTimer = new Timer();
		codingTimer = new Timer();
		this.testingTime = testingTime;
		this.codingTime = codingTime;
	}
	
	public CustomTimer(BabystepsUser user, long testingTime, long codingTime) {
		this(testingTime, codingTime);
		registerBabystepsUser(user);
	}
	
	@Override
	public long getCodingDuration() {
		return codingTime;
	}

	@Override
	public long getTestingDuration() {
		return testingTime;
	}

	@Override
	public long getRemaingTestingTime() {
		return testingTime-(System.currentTimeMillis()-testingStarted);
	}

	@Override
	public long getRemainingCodingTime() {
		return codingTime-(System.currentTimeMillis()-codingStarted);
	}

	@Override
	public void startTestingTimer() {
		testingStarted = System.currentTimeMillis();
		testingTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				notifyAllUsersTestingTimeElapsed();
				stopTestingTimer();
			}
		}, getTestingDuration());
	}

	@Override
	public void stopTestingTimer() {
		testingTimer.cancel();
	}

	@Override
	public void startCodingTimer() {
		codingStarted = System.currentTimeMillis();
		codingTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				notifyAllUsersCodingTimeElapsed();
				stopCodingTimer();
			}
		}, getCodingDuration());
	}

	@Override
	public void stopCodingTimer() {
		codingTimer.cancel();
	}

	@Override
	public void registerBabystepsUser(BabystepsUser user) {
		userList.add(user);
	}
	
	public void notifyAllUsersTestingTimeElapsed() {
		for(int i = 0; i < userList.size(); i++) {
			userList.get(i).notifiyTestingTimerElapsed();
		}
	}

	@Override
	public void notifyAllUsersCodingTimeElapsed() {
		for(int i = 0; i < userList.size(); i++) {
			userList.get(i).notifyCodingTimerElapsed();
		}
	}

	@Override
	public void stopAll() {
		stopCodingTimer();
		stopTestingTimer();
	}
}
