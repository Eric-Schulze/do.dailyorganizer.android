package wedevelop.dodailyorganizer;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Schulze on 10/23/2017.
 */

public class Categories implements Serializable{
    private String userId;
    private int categoryId;
    private int categoryOrder;
    private String categoryName;
    protected ArrayList<DoTask> task_list;

    public Categories(){}

    public static Categories[] getUserCategories(Activity act){
        Context context = act;
        SharedPreferences sharedPref = context.getSharedPreferences(
                act.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Set<String> catIds = sharedPref.getStringSet(act.getString(R.string.preference_usercategoryids), null);
        List<String> cats = new ArrayList<String>(catIds);
        Categories[] ca = new Categories[cats.size()];
        String uid = FirebaseAuth.getInstance().getUid();
        Categories c;
        String sp_cat;
        String[] sa;
        for(int i = 0;i < cats.size();i++){
            c = new Categories();
            c.userId = uid;
            c.categoryId = Integer.parseInt(cats.get(i));
            sp_cat = sharedPref.getString("category_" + c.categoryId, "");
            sa = sp_cat.split(";");
            c.categoryName = sa[0];
            c.categoryOrder = Integer.parseInt(sa[1]);

            ca[i] = c;
        }
        return ca;
    }

    public String getCategoryName(){
        return categoryName;
    }

    public static ArrayList<Categories> buildTestCategories(int given_listid){
        ArrayList<Categories> testCategories = new ArrayList<Categories>();
        Categories c;
        String[] names = {"Tasks","Goals","Events"};
        for(int i = 0; i < 3; i++){
            c = new Categories();
            c.userId = "1001";
            c.categoryId = i + 76;
            c.categoryName = names[i];
            c.task_list = DoTask.buildTestTasks(given_listid, i);
            testCategories.add(c);
        }
        return testCategories;
    }

    public static ArrayList<Categories> convertArrayToArrayList(Categories[] array){
        ArrayList<Categories> al = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            al.add(array[i]);
        }
        return al;
    }

    public static Categories[] convertArrayListToArray(ArrayList<Categories> al){
        Categories[] array = new Categories[al.size()];
        for (int i = 0; i < al.size(); i++) {
            array[i] = al.get(i);
        }
        return array;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(int categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}

