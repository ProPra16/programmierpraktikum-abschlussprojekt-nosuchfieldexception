package babysteps;

public interface BabystepsTimer {
	
	/**
	 * Returns the time one coding step is allowed to take
	 * @return The time one coding cycle takes in milliseconds
	 */
	public long getCodingDuration();
	
	/**
	 * Returns the time one testing step is allowed to take
	 * @return The time one testing cycle takes in milliseconds
	 */
	public long getTestingDuration();
	
	/**
	 * Returns the remaining testing time
	 * @return the remaining testing time in milliseconds
	 */
	public long getRemaingTestingTime();
	
	/**
	 * Returns the remaining coding time
	 * @return the remaining coding time in milliseconds
	 */
	public long getRemainingCodingTime();
	
	/**
	 * Registers a BabystepsUser who will be be notified for both running timers
	 * @param user A BabystepsUser instance that wants to receive a call when one timer ran out.
	 */
	public void registerBabystepsUser(BabystepsUser user);
	
	/**
	 * Starts the testing cycle timer
	 */
	public void startTestingTimer();
	
	/**
	 * Stops the testing cycle timer
	 */
	public void stopTestingTimer();
	
//	/**
//	 * Pauses the testing timer
//	 */
//	public void pauseTestingTimer();
	
	/**
	 * Starts the coding cycle timer
	 */
	public void startCodingTimer();
	
	/**
	 * Stops the coding cycle timer
	 */
	public void stopCodingTimer();
	
//	/**
//	 * Pauses the coding timer
//	 */
//	public void pauseCodingTimer();
	
	void notifyAllUsersTestingTimeElapsed();
	
	void notifyAllUsersCodingTimeElapsed();

}
