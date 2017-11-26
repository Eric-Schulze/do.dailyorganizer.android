package wedevelop.dodailyorganizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by Schulze on 11/25/2017.
 */

public class AddTask extends AppCompatActivity {
    public static DoTask currentDoTask;

    private TextView addTaskTitle, taskId;
    private EditText taskTitle, taskDate, taskTime, taskNotes;
    private Spinner taskList, taskCategory;
    private CheckBox taskPriority;

    private ListSpinAdapter listAdapter;
    private CategorySpinAdapter categoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtask);

        addTaskTitle = (TextView)findViewById(R.id.lbl_addtasktitle);
        taskId = (TextView)findViewById(R.id.txt_task_taskid);
        taskTitle = (EditText)findViewById(R.id.txt_task_title);
        taskDate = (EditText)findViewById(R.id.txt_task_date);
        taskTime = (EditText)findViewById(R.id.txt_task_time);
        taskNotes = (EditText)findViewById(R.id.txt_task_notes);
        taskList = (Spinner)findViewById(R.id.ddl_task_list);
        taskCategory = (Spinner)findViewById(R.id.ddl_task_category);
        taskPriority = (CheckBox)findViewById(R.id.chk_task_priority);

        Lists[] la = Lists.getUserLists(this);
        listAdapter = new ListSpinAdapter(this, android.R.layout.simple_spinner_item, la);
        taskList.setAdapter(listAdapter);

        Categories[] ca = Categories.getUserCategories(this);
        categoryAdapter = new CategorySpinAdapter(this, android.R.layout.simple_spinner_item, ca);
        taskCategory.setAdapter(categoryAdapter);

        setTaskValues();
    }

    private void setTaskValues(){
        taskId.setText(String.valueOf(currentDoTask.getTaskId()));
        if(currentDoTask.getTaskId() > 0){
            addTaskTitle.setText(R.string.txt_edittasktitle);
        }
        else{
            addTaskTitle.setText(R.string.txt_addtasktitle);
        }

        taskTitle.setText(currentDoTask.getTitle());
        taskDate.setText(utils.getShortDateString(currentDoTask.getAssignedDate()));
        taskTime.setText(utils.getShortTimeString(currentDoTask.getDueTimestamp()));
        taskPriority.setChecked(currentDoTask.getPriority() == 1);
        taskList.setSelection(0);
        taskCategory.setSelection(0);
        taskNotes.setText(currentDoTask.getNotes());
    }

    public void cancel(View v){
        this.finish();
    }

}
