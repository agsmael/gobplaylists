package crm.workshop.echonest.playlist;

import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.echonest.api.v4.Song;

import java.util.Calendar;

import crm.workshop.echonest.R;
import crm.workshop.echonest.utils.AlarmReceiver;

public class PlayListActivity extends FragmentActivity implements PlayListFragment
        .OnSongClickedListener {

    public static final String PLAYLIST_TYPE = "PL_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.app_container);

        Intent  intent              = getIntent();

        String type = intent.getStringExtra(PlayListActivity.PLAYLIST_TYPE);

        if (type.equals("genre_radio")) {
            String  genre  = intent.getStringExtra(HomeActivity.GENRE);

            if (savedInstanceState == null) {
                changeLeftFragment(PlayListFragment.newInstance(
                        "genre_radio",
                        genre
                ));
            }
        } else if (type.equals("artist_radio")) {
            String  artist              = intent.getStringExtra(SearchActivity.ARTISTE_NAME);
            int     res_count           = (int) intent.getIntExtra(SearchActivity.RESULT_COUNT, 15);
            float   variety             = (float) intent.getFloatExtra(SearchActivity.VARIETY_FACTOR, 0.5f);
            float   adventurousness     = (float) intent.getFloatExtra(SearchActivity.ADVENTUROUSNESS, 0.2f);
            float   loudness            = (float) intent.getFloatExtra(SearchActivity.LOUDNESS, 0.0f);


            if (savedInstanceState == null) {
                changeLeftFragment(PlayListFragment.newInstance(
                        "artist_radio",
                        res_count,
                        100,
                        artist,
                        variety,
                        adventurousness,
                        loudness
                ));
            }
        }


    }

    private void setAlarm() {
        Intent alarmIntent;
        PendingIntent pendingIntent;
        AlarmManager alarmManager = (AlarmManager) getSystemService
                (ALARM_SERVICE);

        alarmIntent = new Intent(PlayListActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(PlayListActivity.this, 0, alarmIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 30);

        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(),pendingIntent);
//        calendar.set(Calendar.HOUR_OF_DAY, 10);
//        calendar.set(Calendar.MINUTE, 00);
//        calendar.set(Calendar.SECOND, 0);
    }

    protected void changeLeftFragment(Fragment fragment) {
        View container = findViewById(R.id.container);
        if (container != null) {
            // handset
            changeFragment(fragment, R.id.container);
        } else {
            // tablet
            changeFragment(fragment, R.id.container_left);
        }
    }

    protected void changeRightFragment(Fragment fragment) {
        View container = findViewById(R.id.container);
        if (container != null) {
            // handset
            changeFragment(fragment, R.id.container);
        } else {
            // tablet
            changeFragment(fragment, R.id.container_right);
        }
    }

    private void changeFragment(Fragment fragment, int id) {
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(id, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.play_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSongClicked(Song song) {
        Log.d("APP", "song clicked: " + song.getTitle());
        SongFragment songFragment;

        songFragment = new SongFragment();
        songFragment.setArtistName(song.getArtistName());
        songFragment.setTitle(song.getTitle());

        changeRightFragment(songFragment);
    }
}
