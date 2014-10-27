package crm.workshop.echonest.playlist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongFragment extends Fragment {
    private String mTitle;
    private String mArtistName;

    public SongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(android.R.layout.simple_list_item_2, container,
                false);
        ((TextView) view.findViewById(android.R.id.text1)).setText(mTitle);
        ((TextView) view.findViewById(android.R.id.text2)).setText(mArtistName);

        return view;
    }


    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setArtistName(String mArtistName) {
        this.mArtistName = mArtistName;
    }
}
