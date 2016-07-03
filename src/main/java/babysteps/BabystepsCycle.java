package babysteps;

public class BabystepsCycle implements TDDCycle {

	private int phase;

	@Override
	public void nextPhase() {
		phase = ++phase%3;
	}

	@Override
	public boolean phaseIsFinished() {
		return false; //Maybe later
	}

	@Override
	public int getCurrentPhase() {
		return phase;
	}

	@Override
	public void returnToLastPhase() {
		if(phase == 2) return;
		phase=(phase+2)%3;
	}

	
}
