package wedevelop.dodailyorganizer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Schulze on 11/25/2017.
 */

public class utils {

    /* Time */
    public static Calendar getBaseCalendar(){
        return new GregorianCalendar(2000,0,1,0,0,0);
    }

    public static String getShortDateString(Calendar cal){
        return (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" +
                cal.get(Calendar.YEAR);
    }

    public static String getLongDateString(Calendar cal){
        return cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US) + " " +
                cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) +
                cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.YEAR);
    }

    public static String getShortTimeString(Calendar cal){
        return cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + " " +
                cal.get(Calendar.AM_PM);
    }
}
