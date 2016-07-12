package babysteps;

import org.junit.Test;

public class TimerTestJUnit {

	@Test
	public void testTimerFunctionality() { //Bricht zu früh ab?
		CustomTimer t = new CustomTimer(1000,2000);
		BabystepsUser u = new BabystepsUser() {
			
			@Override
			public void notifyTimerElapsed() {
				System.out.println("Finished testing");
			}
		};
		t.registerBabystepsUser(u);
		t.startTimer();
	}

}
