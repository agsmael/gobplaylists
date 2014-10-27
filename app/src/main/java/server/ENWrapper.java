package server;

import android.content.Context;
import android.util.Log;

import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.PlaylistParams;

import java.util.List;

import crm.workshop.echonest.R;
import crm.workshop.echonest.playlist.CustomPlaylist;

/**
 * Created by lbeltramo on 14/10/2014.
 */
public class ENWrapper {
    private static ENWrapper sInstance;
    private EchoNestAPI mApi;

    public ENWrapper(String key) {
        mApi = new EchoNestAPI(key);
    }

    public static ENWrapper with(Context context) {
        if (sInstance == null) {
            sInstance = new ENWrapper(context.getString(R.string
                    .echo_nest_api));
        }
        return sInstance;
    }

    public List<String> getAllGenres() {
        List<String> res = null;
        try {
            res = mApi.listGenres();
        } catch (EchoNestException e) {
            e.printStackTrace();
        }
        return res;
    }

    public CustomPlaylist getArtistRadio(int results, String artist, float variety, float adventurousness, float loudness) throws
            EchoNestException {
        PlaylistParams params = new PlaylistParams();
        params.setType(PlaylistParams.PlaylistType.ARTIST_RADIO);
        params.addIDSpace("7digital-US");
        params.includeTracks();
        params.includeAudioSummary();
        params.addArtist(artist);
        params.setVariety(variety);
        params.setAdventurousness(adventurousness);
        params.setMinLoudness(loudness < -50 ? -100 :  loudness - 50);
        params.setMaxLoudness(loudness > 50 ? 100 :  loudness + 50);
        params.setResults(results);
        return new CustomPlaylist(mApi.createStaticPlaylist(params));
    }

    public CustomPlaylist getGenreRadio(String genre) throws
            EchoNestException {
        PlaylistParams params = new PlaylistParams();
        params.setType(PlaylistParams.PlaylistType.GENRE_RADIO);
        params.addIDSpace("7digital-US");
        params.includeTracks();
        params.includeAudioSummary();
        params.addGenre(genre);
        params.setResults(100);
        return new CustomPlaylist(mApi.createStaticPlaylist(params));
    }
}

