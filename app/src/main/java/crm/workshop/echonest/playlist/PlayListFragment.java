package crm.workshop.echonest.playlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.echonest.api.v4.Playlist;
import com.echonest.api.v4.Song;

import java.util.Date;
import java.util.HashMap;

import crm.workshop.echonest.R;
import crm.workshop.echonest.playlist.data.DataManager;
import crm.workshop.echonest.utils.SwipeDismissListViewTouchListener;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * interface.
 */
public class PlayListFragment extends android.support.v4.app.Fragment implements AbsListView
        .OnItemClickListener, android.support.v4.app.LoaderManager
        .LoaderCallbacks<Playlist> {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DISPLAYED       = "displayed";
    private static final String ARG_RESULTS         = "results";
    private static final String ARG_ARTIST          = "artist";
    private static final String ARG_LOUDNESS        = "loudness";
    private static final String ARG_VARIETY         = "variety";
    private static final String ARG_ADVENTUROUSNESS = "adventurousness";
    private static final String ARG_GENRE           = "genre";
    private static final String ARG_TYPE            = "type";
    private static final int    PLAYLIST_LOADER_ID  = 12;

    private String  mType;
    private int     mDisplayed;
    private int     mResults;
    private String  mArtist;
    private float   mLoudness;
    private float   mVariety;
    private float   mAdventurousness;
    private String  mGenre;

    private CustomPlaylist customPlaylist;

    private OnSongClickedListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private PlayListAdapter mAdapter;
    private RelativeLayout  loaderLayout;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlayListFragment() {
    }

    public static PlayListFragment newInstance(String type, int displayed, int results, String artist,
                                               float variety, float adventurousness, float loudness) {
        PlayListFragment fragment = new PlayListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RESULTS, results);
        args.putInt(ARG_DISPLAYED, displayed);
        args.putString(ARG_ARTIST, artist);
        args.putFloat(ARG_VARIETY, variety);
        args.putFloat(ARG_ADVENTUROUSNESS, adventurousness);
        args.putFloat(ARG_LOUDNESS, loudness);
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }


    public static PlayListFragment newInstance(String type, String genre) {
        PlayListFragment fragment = new PlayListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        args.putString(ARG_GENRE, genre);
        args.putInt(ARG_DISPLAYED, 10);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mDisplayed          = getArguments().getInt(ARG_DISPLAYED);
            mResults            = getArguments().getInt(ARG_RESULTS);
            mArtist             = getArguments().getString(ARG_ARTIST);
            mLoudness           = getArguments().getFloat(ARG_LOUDNESS);
            mVariety            = getArguments().getFloat(ARG_VARIETY);
            mAdventurousness    = getArguments().getFloat(ARG_ADVENTUROUSNESS);
            mGenre              = getArguments().getString(ARG_GENRE);
            mType               = getArguments().getString(ARG_TYPE);
        }

        mAdapter = new PlayListAdapter(getActivity());
        getLoaderManager().initLoader(PLAYLIST_LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container,
                false);

        loaderLayout = (RelativeLayout) view.findViewById(R.id.loadingPanel);

        final PlayListFragment self = this;

        Button savePlaylist = (Button) view.findViewById(R.id.save_pl);
        savePlaylist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HashMap params = new HashMap();
                params.put("name", "manual_save");
                params.put("artiste", mArtist);
                params.put("max_results", mDisplayed);
                params.put("variety", (float)  mVariety);
                params.put("adventurousness", (float) mAdventurousness);
                params.put("loudness", (float) mLoudness);
                DataManager.getInstance().savePlaylist(self.getActivity(), params);
            }
        });

        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.list_song);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        // Set up ListView example
        String[] items = new String[mDisplayed];
        for (int i = 0; i < items.length; i++) {
            items[i] = "Song " + (i + 1);
        }

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        (ListView) mListView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    mAdapter.remove(mAdapter.getItem(position));
                                    mAdapter.add(customPlaylist.getSomeSongs(1).get(0));
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        });

        mListView.setOnTouchListener(touchListener);
        mListView.setOnScrollListener(touchListener.makeScrollListener());

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSongClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSongClickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onSongClicked(mAdapter.getItem(position));
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    @Override
    public android.support.v4.content.Loader<Playlist> onCreateLoader(int i, Bundle bundle) {
        PlayListLoader loader = new PlayListLoader(getActivity());
        loader.setType(mType);
        loader.setArtist(mArtist);
        loader.setResults(mResults);
        loader.setAdventurousness(mAdventurousness);
        loader.setLoudness(mLoudness);
        loader.setVariety(mVariety);
        loader.setGenre(mGenre);
        return loader;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Playlist> playlistLoader, Playlist playlist) {
        mAdapter.clear();
        customPlaylist = (CustomPlaylist) playlist;
        loaderLayout.setVisibility(View.GONE);

        if (playlist != null) {
            mAdapter.addAll(customPlaylist.getSomeSongs(mDisplayed));
        } else {
            // API error
            Toast toast = Toast.makeText(getActivity(), getString(R.string.error_api),
                    Toast.LENGTH_LONG);
            toast.show();
        }

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Playlist> playlistLoader) {
        mAdapter.clear();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSongClickedListener {
        public void onSongClicked(Song song);
    }


}
