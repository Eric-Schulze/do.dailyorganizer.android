package wedevelop.dodailyorganizer;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Schulze on 10/23/2017.
 */

public class DoTask {
    private String title, notes;
    private int taskId;
    private int categoryId;
    private int listId;
    private int taskOrder;
    private String userId;
    private int priority;
    private Calendar assignedDate, dueTimestamp, createTimestamp, updateTimestamp;

    public DoTask(){
        taskId = 0;
        title = "";
        assignedDate = utils.getBaseCalendar();
        dueTimestamp = utils.getBaseCalendar();
        priority = 0;
        listId = 0;
        categoryId = 0;
        notes = "";
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        taskOrder = 1;
        createTimestamp = Calendar.getInstance();
        updateTimestamp = utils.getBaseCalendar();
    }

    private static final String[][] testTaskNames = {{"Make lunch for tomorrow","Call Jo"},
                                                    {"Make lunch for tomorrow","Call Jo"},
                                                    {"Make lunch for tomorrow","Call Jo"},
                                                    {"Make lunch for tomorrow","Call Jo"},
                                                    {"Make lunch for tomorrow","Call Jo"},
                                                    {"Make lunch for tomorrow","Call Jo"},
                                                    {"Make lunch for tomorrow","Call Jo"},
                                                    {"Make lunch for tomorrow","Call Jo"},
                                                    {"Make lunch for tomorrow","Call Jo"}};

    public static ArrayList<DoTask> buildTestTasks(int given_listOrder, int given_categoryOrder){
        ArrayList<DoTask> testTasks = new ArrayList<DoTask>();
        DoTask t;
        for(int i = 0; i < 3; i++){
            t = new DoTask();
            t.userId = "1001";
            t.categoryId = given_categoryOrder;
            t.listId = given_listOrder;
            t.taskId = 54321 + i + given_categoryOrder + given_listOrder;
            t.taskOrder = i+1;
            testTasks.add(t);
        }
        return testTasks;
    }

    public void clear(){
        taskId = 0;
        title = "";
        assignedDate = utils.getBaseCalendar();
        dueTimestamp = utils.getBaseCalendar();
        priority = 0;
        listId = 0;
        categoryId = 0;
        notes = "";
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        taskOrder = 1;
        createTimestamp = Calendar.getInstance();
        updateTimestamp = utils.getBaseCalendar();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getTaskOrder() {
        return taskOrder;
    }

    public void setTaskOrder(int taskOrder) {
        this.taskOrder = taskOrder;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userid) {
        this.userId = userid;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Calendar getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Calendar assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Calendar getDueTimestamp() {
        return dueTimestamp;
    }

    public void setDueTimestamp(Calendar dueTimestamp) {
        this.dueTimestamp = dueTimestamp;
    }

    public Calendar getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Calendar createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Calendar getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Calendar updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
