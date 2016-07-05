package tddt;

import java.util.TimerTask;

public class TDDTTimerTask extends TimerTask{

	private int time;
	
	public TDDTTimerTask() {
		time = 0;
	}
	
	@Override
	public void run() {
		time++;
	}
	
	public int getTime(){
		return time;
	}

}
