package wedevelop.dodailyorganizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


public class DrawerUtil extends TaskWorkspace {
    public static String TW = "TaskWorkspace Activity";
    public Drawer mDrawer;
    protected static enum DrawerOptions{
        NO_OPTION(0),
        ADD_TASK(1),
        WEEKLY_TEMPLATE(2),
        NEXT_WEEK(3),
        PREVIOUS_WEEK(4),
        MIGRATE(5),
        SETTINGS(6),
        ABOUT(7),
        HELP(8),
        SIGNOUT(9);

        private final int id;
        DrawerOptions(int id) { this.id = id; }
        public int getValue() { return id; }
        public static DrawerOptions valueOf(int index) {
            return values()[index];
        }
    }

    public void getDrawer(final Activity activity, Toolbar toolbar, boolean withProfile) {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem drawerEmptyItem= new PrimaryDrawerItem().withIdentifier((long)DrawerOptions.NO_OPTION.getValue()).withName("");
        drawerEmptyItem.withEnabled(false);

        PrimaryDrawerItem drawerItemAddTask = new PrimaryDrawerItem().withIdentifier((long)DrawerOptions.ADD_TASK.getValue())
                .withName(R.string.add_task);
        PrimaryDrawerItem drawerItemWeeklyTemplate = new PrimaryDrawerItem()
                .withIdentifier((long)DrawerOptions.WEEKLY_TEMPLATE.getValue()).withName(R.string.weekly_template);
        PrimaryDrawerItem drawerItemNextWeek = new PrimaryDrawerItem()
                .withIdentifier((long)DrawerOptions.NEXT_WEEK.getValue()).withName(R.string.next_week);
        PrimaryDrawerItem drawerItemPreviousWeek = new PrimaryDrawerItem()
                .withIdentifier((long)DrawerOptions.PREVIOUS_WEEK.getValue()).withName(R.string.previous_week);
        PrimaryDrawerItem drawerItemMigrate = new PrimaryDrawerItem()
                .withIdentifier((long)DrawerOptions.MIGRATE.getValue()).withName(R.string.migrate);


        SecondaryDrawerItem drawerItemSettings = new SecondaryDrawerItem().withIdentifier((long)DrawerOptions.SETTINGS.getValue())
                .withName(R.string.settings);
        SecondaryDrawerItem drawerItemAbout = new SecondaryDrawerItem().withIdentifier((long)DrawerOptions.ABOUT.getValue())
                .withName(R.string.about);
        SecondaryDrawerItem drawerItemHelp = new SecondaryDrawerItem().withIdentifier((long)DrawerOptions.HELP.getValue())
                .withName(R.string.help);
        SecondaryDrawerItem drawerItemSignOut = new SecondaryDrawerItem().withIdentifier((long)DrawerOptions.SIGNOUT.getValue())
                .withName(R.string.sign_out);





        //create the drawer and remember the `Drawer` result object
        mDrawer = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(-1)
                .addDrawerItems(
                        drawerEmptyItem,drawerEmptyItem,
                        drawerItemAddTask,
                        drawerItemWeeklyTemplate,
                        drawerItemNextWeek,
                        drawerItemPreviousWeek,
                        drawerItemMigrate,
                        new DividerDrawerItem(),
                        drawerItemAbout,
                        drawerItemSettings,
                        drawerItemHelp,
                        drawerItemSignOut
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        return onOptionClick(view, position, drawerItem);
                    }
                })
                .build();
    }

    public boolean onOptionClick(View v, int position, IDrawerItem drawerItem){
        switch(DrawerOptions.valueOf((int)drawerItem.getIdentifier())){
            case NO_OPTION:
                Log.d(TW,"Action Drawer: No Option Selected");
                break;
            case ADD_TASK:
                Log.d(TW,"Action Drawer: Add Task Selected");
                AddTask.currentDoTask.clear();
                Intent addTaskIntent = new Intent(v.getContext(), AddTask.class);
                v.getContext().startActivity(addTaskIntent);
                mDrawer.closeDrawer();
                break;
            case WEEKLY_TEMPLATE:
                Log.d(TW,"Action Drawer: Weekly Template Selected");
                break;
            case NEXT_WEEK:
                Log.d(TW,"Action Drawer: Next Week Selected");
                break;
            case PREVIOUS_WEEK:
                Log.d(TW,"Action Drawer: Previous Week Selected");
                break;
            case MIGRATE:
                Log.d(TW,"Action Drawer: Migrate Selected");
                break;
            case SETTINGS:
                Log.d(TW,"Action Drawer: Settings Selected");
                break;
            case ABOUT:
                Log.d(TW,"Action Drawer: About Selected");
                break;
            case HELP:
                Log.d(TW,"Action Drawer: Help Selected");
                break;
            case SIGNOUT:
                Log.d(TW,"Action Drawer: Sign Out Selected");
                signOut(v);
                break;
            default:
                break;
        }
        return true;
    }
}