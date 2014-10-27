package crm.workshop.echonest.playlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Date;
import java.util.HashMap;

import crm.workshop.echonest.R;
import crm.workshop.echonest.playlist.data.DataManager;


public class SearchActivity extends FragmentActivity  {

    public final static String ARTISTE_NAME     = "ARTISTE_NAME";
    public final static String RESULT_COUNT     = "RESULT_COUNT";
    public final static String VARIETY_FACTOR   = "VARIETY_FACTOR";
    public final static String ADVENTUROUSNESS  = "ADVENTUROUSNESS";
    public final static String LOUDNESS         = "LOUDNESS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final EditText input_artiste    = (EditText) findViewById(R.id.input_artiste);
        final SeekBar result_count      = (SeekBar) findViewById(R.id.result_count);
        final SeekBar variety           = (SeekBar) findViewById(R.id.variety_bar);
        final SeekBar adventurousness   = (SeekBar) findViewById(R.id.adventurousness_bar);
        final SeekBar loudness          = (SeekBar) findViewById(R.id.loudness);
        final Button button             = (Button) findViewById(R.id.btn_valider);
        final Activity self             = this;

        result_count.setMax(100);
        variety.setMax(100);
        adventurousness.setMax(100);
        loudness.setMax(200);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(self, PlayListActivity.class);

                intent.putExtra(ARTISTE_NAME, input_artiste.getText().toString());
                intent.putExtra(RESULT_COUNT, result_count.getProgress());
                intent.putExtra(VARIETY_FACTOR, (float)  variety.getProgress() / 100);
                intent.putExtra(ADVENTUROUSNESS, (float)  adventurousness.getProgress()/100);
                intent.putExtra(LOUDNESS, (float)  loudness.getProgress()-100);

                intent.putExtra(PlayListActivity.PLAYLIST_TYPE, "artist_radio");

                HashMap params = new HashMap();
                params.put("name", "auto_save");
                params.put("date", new Date().toString());
                params.put("artiste", input_artiste.getText().toString());
                params.put("max_results", result_count.getProgress());
                params.put("variety", (float)  variety.getProgress()/100);
                params.put("adventurousness", (float) adventurousness.getProgress()/100);
                DataManager.getInstance().savePlaylist(self, params);

                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
}
