package babysteps;

public class TimerTest {

	public static void main(String[] args) {
		CustomTimer t = new CustomTimer(new BabystepsUser() {
			
			@Override
			public void notifyCodingTimerElapsed() {
				System.out.println("HO");				
			}
			
			@Override
			public void notifiyTestingTimerElapsed() {
				System.out.println("hi");
			}
		}, 2000, 4000);
		t.startTestingTimer();
		t.stopTestingTimer();
		t.startCodingTimer();
	}

}
