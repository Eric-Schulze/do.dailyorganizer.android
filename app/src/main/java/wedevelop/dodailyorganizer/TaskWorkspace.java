package wedevelop.dodailyorganizer;

import java.text.SimpleDateFormat;
import java.util.*;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.design.widget.TabLayout;

import com.astuetz.PagerSlidingTabStrip;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class TaskWorkspace extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1701;

    public TextView dateHeader;
    public Toolbar menuBar;
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mDrawerToggle;
    public TabLayout dayTabs;
    public LinearLayout workspace_container;
    public ViewPager listPager;

    public static String TW = "TaskWorkspace Activity";
    protected ArrayList<Categories> categories_list;
    protected ArrayList<Lists> lists_list;
    public int currentTab = 0;
    public static Calendar TODAY;
    public static Calendar WEEK_START;
    public Lists.ListAdapter listAdapter;

    public Users currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.task_workspace);

        dateHeader = (TextView)findViewById(R.id.dateHeader_workspace);
        dayTabs = (TabLayout)findViewById(R.id.day_tabs);
        workspace_container = (LinearLayout) findViewById(R.id.task_workspace_container);
        menuBar = (Toolbar)findViewById(R.id.taskWorkspaceToolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.homepage_menu_container);

        menuBar.setTitle(getResources().getString(R.string.app_title));
        setSupportActionBar(menuBar);
        DrawerUtil drawer = new DrawerUtil();
        drawer.getDrawer(this, menuBar, false);


        if(!Users.isUserLoggedIn()){
            signIn();
            return;
        }
        else{
            currentUser = Users.getUserInformation();
            //Users.loadListsIntoSharedPref();
            //Users.loadCategoriesIntoSharedPref();
            Users.loadTestListsIntoSharedPref(this);
            Users.loadTestCategoriesIntoSharedPref(this);

            AddTask.currentDoTask = new DoTask();

            DrawerUtil mDrawer = new DrawerUtil();
            mDrawer.getDrawer(this, menuBar, true);

            loadWorkspace();
            Log.d(TW,"Date Header Populated");

            buildDayTab();
            Log.d(TW,"Day Tab Fragments Populated");
        }

        Log.d(TW,"TaskWorkspace Created");
    }

    @Override
    public void onStart(){
        super.onStart();

    }

    protected void signIn(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                                Arrays.asList(
                                        new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build())
                        )
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                        .setTheme(R.style.AppTheme)
                        .build(),
                RC_SIGN_IN);
        finish();
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                startActivity(new Intent(this, TaskWorkspace.class));
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar(getString(R.string.sign_in_cancelled));
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar(getString(R.string.no_internet_connection));
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackbar(getString(R.string.unknown_error));
                    return;
                }
            }

            showSnackbar(getString(R.string.unknown_sign_in_response));
        }
    }

    protected void loadWorkspace(){
        setDateVariables();
        setDate(TODAY.get(Calendar.DAY_OF_WEEK) - 2);
        loadDayTabs();
    }

    private void setDate(int weekStartOffset){
        Calendar selectedDay = (Calendar)WEEK_START.clone();
        selectedDay.add(selectedDay.DAY_OF_WEEK, weekStartOffset);
        Log.d(TW, "todayCalendar Instance Created");
        Map<String,String> today = getDateStrings(selectedDay);
        Log.d(TW, "todayStringMap Instance Created");

        String label = today.get("WEEKDAY") + ", " + today.get("MONTH") + " " + today.get("DAY") + today.get("ENDING");
        dateHeader.setText(label);
        Log.d(TW, "dateHeader Text Set as " + label);
    }

    private void setDateVariables(){
        WEEK_START = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        WEEK_START.add( Calendar.DATE, -(WEEK_START.get(Calendar.DAY_OF_WEEK) - 2));
        String dateStr = dateFormat.format(WEEK_START.getTime());
        Log.d(TW, "WEEK_START Set as " + dateStr + " and timezone " + TimeZone.getDefault().getDisplayName());

        TODAY = Calendar.getInstance(TimeZone.getDefault());
        dateStr = dateFormat.format(TODAY.getTime());
        Log.d(TW, "TODAY Set as " + dateStr);
    }

    private Map<String, String> getDateStrings(Calendar date){
        Map<String,String> day = new HashMap<String,String>();
        int dayNumber = (int)date.get(Calendar.DATE);
        day.put("MONTH", date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US));
        day.put("DAY", Integer.toString(dayNumber));
        day.put("WEEKDAY", date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
        day.put("YEAR", Integer.toString(date.get(Calendar.YEAR)));

        String ending = "";
        if(dayNumber % 10 == 1 && dayNumber != 11){
            ending = "st";
        }
        else if(dayNumber % 10 == 2 && dayNumber != 12){
            ending = "nd";
        }
        else if(dayNumber % 10 == 3 && dayNumber != 13){
            ending = "rd";
        }
        else{
            ending = "th";
        }
        day.put("ENDING", ending);

        return day;
    }

    private void loadDayTabs(){
        String dayName = getResources().getString(R.string.tab_monday);
        dayTabs.addTab(dayTabs.newTab().setText(dayName));
        dayName = getResources().getString(R.string.tab_tuesday);
        dayTabs.addTab(dayTabs.newTab().setText(dayName));
        dayName = getResources().getString(R.string.tab_wednesday);
        dayTabs.addTab(dayTabs.newTab().setText(dayName));
        dayName = getResources().getString(R.string.tab_thursday);
        dayTabs.addTab(dayTabs.newTab().setText(dayName));
        dayName = getResources().getString(R.string.tab_friday);
        dayTabs.addTab(dayTabs.newTab().setText(dayName));
        dayName = getResources().getString(R.string.tab_saturday);
        dayTabs.addTab(dayTabs.newTab().setText(dayName));
        dayName = getResources().getString(R.string.tab_sunday);
        dayTabs.addTab(dayTabs.newTab().setText(dayName));

        currentTab = (TODAY.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ? 6 : TODAY.get(Calendar.DAY_OF_WEEK) - 2);
        TabLayout.Tab tab = dayTabs.getTabAt(currentTab);
        tab.select();

        dayTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getPosition();
                setDate(currentTab);

                Log.d(TW, "Select Tab at position " + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void buildDayTab(){
        categories_list = Categories.buildTestCategories(currentTab);
        lists_list = Lists.convertArrayToArrayList(Lists.buildTestLists());

        listAdapter = new Lists.ListAdapter(getSupportFragmentManager(), lists_list, 1);

        listPager = (ViewPager)findViewById(R.id.list_container);
        listPager.setAdapter(listAdapter);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.list_names);
        tabs.setViewPager(listPager);
        final int pageMargin = (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 8, getResources() .getDisplayMetrics());
        listPager.setPageMargin(pageMargin);
    }

    protected void showSnackbar(String title){
        View homepage = findViewById(R.id.homepage);
        Snackbar snackbar = Snackbar
                .make(homepage, title, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    public void signOut(View v){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent i = new Intent(v.getContext(), TaskWorkspace.class);
        v.getContext().startActivity(i);
        finish();
    }
}
