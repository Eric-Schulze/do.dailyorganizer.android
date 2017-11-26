package wedevelop.dodailyorganizer;

/**
 * Created by Schulze on 11/7/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashSet;
import java.util.Set;

public class Users extends AppCompatActivity {
    private String userid;
    private String email;
    private String displayname;

    //Check if user is logged in
    public static boolean isUserLoggedIn(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser() != null;
    }

    //Check if user is logged in
    public static boolean isUserLoggedIn(FirebaseAuth auth){
        auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser() != null;
    }

    public static Users getUserInformation(){
        Users newUser = new Users();
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        newUser.userid = fbUser.getUid();
        newUser.email = fbUser.getEmail();
        newUser.displayname = fbUser.getDisplayName();

        return newUser;
    }

    public static void loadTestListsIntoSharedPref(Activity act){
        SharedPreferences sharedPref = act.getSharedPreferences(act.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Set<String> set = new HashSet<String>();
        set.add("1");
        set.add("2");
        set.add("3");
        editor.putStringSet(act.getString(R.string.preference_userlistids), set);

        editor.putString("list_1", "Home;1");
        editor.putString("list_2", "Work;2");
        editor.putString("list_3", "Around the House;3");

        editor.commit();
    }

    public static void loadTestCategoriesIntoSharedPref(Activity act){
        SharedPreferences sharedPref = act.getSharedPreferences(act.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Set<String> set = new HashSet<String>();
        set.add("1");
        set.add("2");
        set.add("3");
        editor.putStringSet(act.getString(R.string.preference_usercategoryids), set);

        editor.putString("category_1", "Tasks;1");
        editor.putString("category_2", "Goals;2");
        editor.putString("category_3", "Events;3");

        editor.commit();
    }

    public static void loadCategoriesIntoSharedPref(){
        //TODO: Bring in custom categories from db
    }

    public static void loadListsIntoSharedPref(){
        //TODO: Bring in custom lists from db
    }
}
