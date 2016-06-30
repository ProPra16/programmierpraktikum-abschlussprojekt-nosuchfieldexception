package babysteps;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class StandardTimer implements BabystepsTimer {
	Timer testingTimer;
	long testingStarted;
	long codingStarted;
	private ArrayList<BabystepsUser> userList;
	
	@Override
	public long getCodingDuration() {
		return 3*60*1000;
	}

	@Override
	public long getTestingDuration() {
		return 3*60*1000;
	}

	@Override
	public long getRemaingTestingTime() {
		return 0;
	}

	@Override
	public long getRemainingCodingTime() {
		return 0;
	}

	@Override
	public void startTestingTimer() {
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
	public void pauseTestingTimer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startCodingTimer() {
	}

	@Override
	public void stopCodingTimer() {
		
	}

	@Override
	public void pauseCodingTimer() {
		// TODO Auto-generated method stub

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
}
