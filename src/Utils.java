import java.util.Calendar;
import java.util.Date;

public class Utils {
	public static String getReadableDate(long timestamp) {
		Date date = new Date(timestamp);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String dateString = 
				String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))+"/"
				+String.valueOf(calendar.get(Calendar.MONTH)) +"/"
				+String.valueOf(calendar.get(Calendar.YEAR));
		return dateString;
	}
}
