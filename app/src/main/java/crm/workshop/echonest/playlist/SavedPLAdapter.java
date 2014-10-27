package crm.workshop.echonest.playlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.echonest.api.v4.Song;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import crm.workshop.echonest.R;


public class SavedPLAdapter extends ArrayAdapter<HashMap<String, String>> {

    private final LayoutInflater mInflater;

    public SavedPLAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.fragment_savedpl, null);

        HashMap<String, String> pl = getItem(position);

        String test = pl.get("date");
        /*((TextView) view.findViewById(android.R.id.text1)).setText(pl.get("name"));
        ((TextView) view.findViewById(android.R.id.text2)).setText(pl.get("date"));*/

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
