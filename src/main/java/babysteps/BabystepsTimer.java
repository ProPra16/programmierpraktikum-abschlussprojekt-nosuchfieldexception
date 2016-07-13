package babysteps;

public interface BabystepsTimer {
	
	/**
	 * Returns the time one coding step is allowed to take
	 * @return The time one coding cycle takes in milliseconds
	 */
	public long getDuration();
	
	/**
	 * Returns the remaining coding time
	 * @return the remaining coding time in milliseconds
	 */
	public long getRemainingTime();
	
	/**
	 * Registers a BabystepsUser who will be be notified for both running timers
	 * @param user A BabystepsUser instance that wants to receive a call when one timer ran out.
	 */
	public void registerBabystepsUser(BabystepsUser user);
	
	/**
	 * Starts the testing cycle timer
	 */
	public void startTimer();
	
	/**
	 * Stops the coding cycle timer
	 */
	public void stopTimer();
	
	/**
	 * Informs all subscribed Users about Timer finish
	 */
	void notifyAllUsersTimeElapsed();

}
