package wedevelop.dodailyorganizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoriesAdapter extends ArrayAdapter<Categories> {
    public CategoriesAdapter(Context context, ArrayList<Categories> categories) {
        super(context, 0, categories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Categories cat = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_container, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.categoryName);
        // Populate the data into the template view using the data object
        tvName.setText(cat.getCategoryName());

        // Return the completed view to render on screen
        return convertView;
    }
}
