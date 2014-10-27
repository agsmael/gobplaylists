package crm.workshop.echonest.playlist;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import crm.workshop.echonest.R;
import crm.workshop.echonest.playlist.data.DataManager;

public class HomeActivity extends FragmentActivity implements
        android.support.v4.app.LoaderManager.LoaderCallbacks<List<String>>,
        AdapterView.OnItemSelectedListener,
        SavedPLFragment.OnFragmentInteractionListener {

    public final static String GENRE = "GENRE";

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private float lastTranslate = 0.0f;
    private RelativeLayout frame;
    private Spinner mAll_genres;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button button_manual  = (Button) findViewById(R.id.goto_search_manual);
        final Activity self         = this;

        final ArrayList<HashMap<String, String>> listPl =  DataManager.getInstance().getSavedPlaylist(this);

        mAll_genres = (Spinner) findViewById(R.id.all_genres);
        mAll_genres.setOnItemSelectedListener(this);

        button_manual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(self, SearchActivity.class);
                startActivity(intent);
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        frame = (RelativeLayout) findViewById(R.id.content_frame);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_gobplaylists_2,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                float moveFactor = (mDrawerList.getWidth() * slideOffset);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                {
                    frame.setTranslationX(moveFactor);
                }
                else
                {
                    TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setDuration(0);
                    anim.setFillAfter(true);
                    frame.startAnimation(anim);

                    lastTranslate = moveFactor;
                }
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
                // CA MARCHE PAS :@
//                if (savedInstanceState == null) {
//                    SavedPLFragment fragment = SavedPLFragment.newInstance(
//                            DataManager.getInstance().getListPL(self));
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.left_drawer, fragment)
//                            .addToBackStack(null)
//                            .commit();
//                }
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<List<String>> onCreateLoader(int i, Bundle bundle) {
        return new GenreLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<String>> listLoader, List<String> genres) {
        GenreAdapter adapter = new GenreAdapter(this, android.R.layout.simple_spinner_item);

        adapter.addAll(genres);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mAll_genres.setAdapter(adapter);
        mAll_genres.setSelected(false);
    }

    @Override
    public void onLoaderReset(Loader<List<String>> listLoader) {

    }

    private Boolean _firstTime = true;
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (_firstTime) {
            _firstTime = false;
            return;
        }

        Intent intent = new Intent(this, PlayListActivity.class);
        intent.putExtra(GENRE, (String) mAll_genres.getSelectedItem());

        intent.putExtra(PlayListActivity.PLAYLIST_TYPE, "genre_radio");

        startActivity(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onFragmentInteraction(HashMap<String, String> id) {

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
}
