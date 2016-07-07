package tddt;

import org.junit.*;

public class TDDTTimerTaskTest {
	
	private TDDTTimerTask task = new TDDTTimerTask();
	
	@Test
	public void testCounterNoCall() {
		Assert.assertEquals(task.getTime(), 0);
	}
	
	@Test
	public void testCounterOneCall() {
		task.run();
		Assert.assertEquals(task.getTime(), 1);
	}
	
	@Test
	public void testCounterFiveCalls() {
		for (int i = 0; i < 5; i++) {
			task.run();
		}
		Assert.assertEquals(task.getTime(), 5);
	}
	
	@Test
	public void testCounterOneHundredCalls() {
		for (int i = 0; i < 100; i++){
			task.run();
		}
		Assert.assertEquals(task.getTime(), 100);
	}
	
}
