package wedevelop.dodailyorganizer;

import java.util.*;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class TaskWorkspace extends AppCompatActivity {
    public static String TW = "TaskWorkspace Activity";
    public TextView dateHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_workspace);
        dateHeader = (TextView)findViewById(R.id.dateHeader_workspace);
        Log.d(TW,"TaskWorkspace Created");

        loadWorkspace();
        Log.d(TW,"Date Header Populated");
    }

    protected void loadWorkspace(){
        Calendar todayCalendar = Calendar.getInstance();
        Log.d(TW, "todayCalendar Instance Created");
        Map<String,String> today = getDateStrings(todayCalendar);
        Log.d(TW, "todayStringMap Instance Created");

        String label = today.get("WEEKDAY") + ", " + today.get("MONTH") + " " + today.get("DAY") + " " + today.get("YEAR");
        dateHeader.setText(label);
        Log.d(TW, "dateHeader Text Set");
    }

    private Map<String, String> getDateStrings(Calendar date){
        Map<String,String> day = new HashMap<String,String>();
        day.put("MONTH", date.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US));
        day.put("DAY", Integer.toString(date.get(Calendar.DATE)));
        day.put("WEEKDAY", date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
        day.put("YEAR", Integer.toString(date.get(Calendar.YEAR)));

        return day;
    }
}
