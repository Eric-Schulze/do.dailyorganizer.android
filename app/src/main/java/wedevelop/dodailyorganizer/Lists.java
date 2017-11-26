package wedevelop.dodailyorganizer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import bolts.Task;

import static java.lang.Integer.getInteger;

/**
 * Created by Schulze on 10/23/2017.
 */

public class Lists extends Fragment implements Comparable<Lists> {
    private String userId;
    private int listId;
    private int listOrder;
    private String listName;
    protected ArrayList<Categories> categories_list;
    static int NUM_ITEMS = 3;

    public static class ListAdapter extends FragmentPagerAdapter {
        protected ArrayList<Categories> categories_list;
        protected Lists currentList;
        protected ArrayList<Lists> lists_list;

        public ListAdapter(FragmentManager fm, ArrayList<Lists> lists, int currentListNumber) {
            super(fm);
            lists_list = lists;
            Arrays.sort(lists_list.toArray());
            currentList = lists_list.get(currentListNumber);
            categories_list = currentList.categories_list;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return MyListFragment.newInstance(currentList);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            Arrays.sort(lists_list.toArray());
            return lists_list.get(position).listName;
        }
    }

    public static class MyListFragment extends ListFragment {
        int mNum;
        protected Lists currentList;
        protected ArrayList<Categories> categories_list;
        protected ScrollView listScroller;

        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static MyListFragment newInstance(Lists list) {
            MyListFragment f = new MyListFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", list.listOrder);
            args.putSerializable("categories", list.getCategories_list());
            f.setArguments(args);

            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        @SuppressWarnings("unchecked")
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
            categories_list = getArguments() != null ? (ArrayList<Categories>)getArguments().getSerializable("categories") : new ArrayList<Categories>();
            Log.d("Lists", "List Fragment Created");
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.category_list_fragment_pager, container, false);
            ListView list_container = (ListView)v.findViewById(R.id.list);

            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
            categories_list = getArguments() != null ? (ArrayList<Categories>)getArguments().getSerializable("categories") : new ArrayList<Categories>();

            if(categories_list != null) {
                CategoriesAdapter adapter = new CategoriesAdapter(getContext(), categories_list);
                list_container.setAdapter(adapter);
            }
            Log.d("Lists", "List Fragment View Created");
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            /*
            String[] categoryNames = new String[categories_list.length];
            for (int i = 0; i < categories_list.length; i++) {
                categoryNames[i] = categories_list[i].getCategoryName();
            }*/
            String[] a = {"One", "Two", "Three"};
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, a));


            Log.d("Lists", "List Fragment activity Created");
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i("Categories", "Item clicked: " + id);
        }

    }

    public static Lists[] getUserLists(Activity act){
        Context context = act;
        SharedPreferences sharedPref = context.getSharedPreferences(
                act.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Set<String> listIds = sharedPref.getStringSet(act.getString(R.string.preference_userlistids), null);
        List<String> lists = new ArrayList<String>(listIds);
        Lists[] la = new Lists[lists.size()];
        String uid = FirebaseAuth.getInstance().getUid();
        Lists l;
        String sp_list;
        String[] sa;
        for(int i = 0;i < lists.size();i++){
            l = new Lists();
            l.userId = uid;
            l.listId = Integer.parseInt(lists.get(i));
            sp_list = sharedPref.getString("list_" + l.listId, "");
            sa = sp_list.split(";");
            l.listName = sa[0];
            l.listOrder = Integer.parseInt(sa[1]);

            la[i] = l;
        }
        return la;
    }

    public static Lists[] buildTestLists(){
        Lists[] testLists = new Lists[3];
        Lists n;
        String[] names = {"Work","Home","School"};
        for(int i = 0; i < 3; i++){
            n = new Lists();
            n.userId = "1001";
            n.listId = 222 + i;
            n.listOrder = i+1;
            n.listName = names[i];
            testLists[i] = n;
        }
        return testLists;
    }

    public static ArrayList<Lists> convertArrayToArrayList(Lists[] array){
        ArrayList<Lists> al = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            al.add(array[i]);
        }
        return al;
    }

    public static Lists[] convertArrayListToArray(ArrayList<Lists> al){
        Lists[] array = new Lists[al.size()];
        for (int i = 0; i < al.size(); i++) {
            array[i] = al.get(i);
        }
        return array;
    }

    public int compareTo(Lists compareList){
        int compareOrder = compareList.getListOrder();
        return this.listOrder - compareOrder; //ASC ORDER
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getListOrder() {
        return listOrder;
    }

    public void setListOrder(int listOrder) {
        this.listOrder = listOrder;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ArrayList<Categories> getCategories_list() {
        return categories_list;
    }

    public void setCategories_list(ArrayList<Categories> categories_list) {
        this.categories_list = categories_list;
    }
}
