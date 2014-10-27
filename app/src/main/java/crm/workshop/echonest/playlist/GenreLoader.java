package crm.workshop.echonest.playlist;

import android.content.Context;

import java.util.List;

import server.ENWrapper;

public class GenreLoader extends android.support.v4.content
        .AsyncTaskLoader<List<String> > {

    private List<String> mAllGenre;

    public GenreLoader(Context context) {
        super(context);
    }

    @Override
    public List<String> loadInBackground() {
        mAllGenre = ENWrapper.with(getContext()).getAllGenres();
        return mAllGenre;
    }

    @Override
    protected void onStartLoading() {
        if (mAllGenre != null) {
            deliverResult(mAllGenre);
        } else {
            forceLoad();
        }
    }
}
