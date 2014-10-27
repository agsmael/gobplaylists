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


import crm.workshop.echonest.R;

/**
 * Created by lbeltramo on 14/10/2014.
 */
public class PlayListAdapter extends ArrayAdapter<Song> {

    private final LayoutInflater mInflater;

    public PlayListAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            //view = mInflater.inflate(R.layout.fragment_song,
                    //parent, false);
            view  = new GestureView(getContext());

        } else {
            view = convertView;
        }

        Song song = getItem(position);

        String coverArt;

        try{
            coverArt = song.getString("tracks[0].release_image");
        } catch (IndexOutOfBoundsException e) {
            coverArt = null;
        }

        ((TextView) view.findViewById(android.R.id.text1)).setText(song.getArtistName());
        ((TextView) view.findViewById(android.R.id.text2)).setText(song.getTitle());

        if (coverArt != null) {
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            Picasso.with(getContext()).load(coverArt).into(imageView);
        }

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();


    }
}
