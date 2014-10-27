package crm.workshop.echonest.playlist;

import android.content.Context;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Playlist;

import server.ENWrapper;

/**
 * Created by lbeltramo on 14/10/2014.
 */
//louis@beltramo.me
public class PlayListLoader extends android.support.v4.content
        .AsyncTaskLoader<Playlist> {

    private String      mType;
    private int         mResults;
    private String      mArtist;
    private String      mGenre;
    private Playlist    mPlaylist;
    private float       mLoudness;
    private float       mVariety;
    private float       mAdventurousness;

    public PlayListLoader(Context context) {
        super(context);
    }

    @Override
    public Playlist loadInBackground() {
        try {
            if (mType.equals("genre_radio")) {
                mPlaylist = ENWrapper.with(getContext()).getGenreRadio(
                        mGenre
                );
            } else if (mType.equals("artist_radio")) {
                mPlaylist = ENWrapper.with(getContext()).getArtistRadio(
                        mResults,
                        mArtist,
                        mVariety,
                        mAdventurousness,
                        mLoudness
                );
            }
        } catch (EchoNestException e) {
            mPlaylist = null;
            e.printStackTrace();
        }
        return mPlaylist;
    }

    @Override
    protected void onStartLoading() {
        if (mPlaylist != null) {
            deliverResult(mPlaylist);
        } else {
            forceLoad();
        }
    }

    public void setArtist(String artist) {
        this.mArtist = artist;
    }

    public void setResults(int results) {
        this.mResults = results;
    }

    public void setLoudness(float loudness) {
        this.mLoudness = loudness;
    }

    public void setVariety(float variety) {
        this.mVariety = variety;
    }

    public void setAdventurousness(float adventurousness) { this.mAdventurousness = adventurousness; }

    public void setType(String type) {
        this.mType = type;
    }

    public void setGenre(String genre) {
        this.mGenre = genre;
    }
}
