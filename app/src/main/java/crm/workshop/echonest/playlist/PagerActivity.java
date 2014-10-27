package crm.workshop.echonest.playlist;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.echonest.api.v4.Song;

import crm.workshop.echonest.R;

public class PagerActivity extends FragmentActivity implements
        PlayListFragment.OnSongClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        setContentView(viewPager);

        SharedPreferences prefs = getSharedPreferences("SONG_CLICKED", 0);
        String song_clicked = prefs.getString("song_clicked", "no song");

        Log.d("APP", song_clicked);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pager, menu);
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
        Log.d("APP", song.getTitle());

        // Shared preferences
        SharedPreferences prefs = getSharedPreferences("SONG_CLICKED", 0);
        prefs.edit().putString("song_clicked", song.getTitle()).commit();

        // Notifications
        Intent resultIntent = new Intent(this, PlayListActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(this,
                        0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder notifBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_dialog_alert)
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true)
                        .setContentTitle("A notification!")
                        .setContentText("Notification content");

        int mNotificationId = 12;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, notifBuilder.build());
    }
}
