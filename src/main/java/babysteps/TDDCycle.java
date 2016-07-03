package babysteps;

public interface TDDCycle {
	
	public void nextPhase();
	
	public boolean phaseIsFinished();
	
	public int getCurrentPhase();
	
	public void returnToLastPhase();

}
