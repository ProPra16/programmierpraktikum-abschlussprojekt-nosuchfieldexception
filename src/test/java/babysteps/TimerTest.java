package babysteps;

public class TimerTest {

	public static void main(String[] args) {
		CustomTimer t = new CustomTimer(new BabystepsUser() {
			
			@Override
			public void notifyTimerElapsed() {
				System.out.println("hi");
			}
		}, 2000, 4000);
		t.startTimer();
		t.stopTimer();
		t.startTimer();
	}

}
