package babysteps;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	public static String millisecondsToTimerString(long millis) {
		Date date = new Date(millis);
		DateFormat formatter = new SimpleDateFormat("mm:ss:SSS");
		return formatter.format(date);
	}
}
