package crm.workshop.echonest.playlist.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import crm.workshop.echonest.R;

public class DataManager extends Activity {

    private static final String ARGS_PLAYLIST   = "args_playlist";
    private static final String SAVED_PL        = "saved_playlist";

    private static DataManager _instance;

    private int                 saved_count = 0;
    private SharedPreferences   sharedPreferences;

    private DataManager() {

    }

    public static DataManager getInstance()
    {
        if (_instance == null)
        {
            _instance = new DataManager();

            /*Boolean test    = true;
            int     num     = 0;
            while (test) {
                String res = _instance.sharedPreferences.getString(SAVED_PL + num, "");
                if (res.isEmpty()) {
                    test = false;
                    _instance.saved_count = num;
                } else {
                    num++;
                }
            }*/
        }
        return _instance;
    }

    public String getListPL(Context ctx) {
        if(sharedPreferences == null)
            sharedPreferences = ctx.getSharedPreferences(ARGS_PLAYLIST, Context.MODE_PRIVATE);

        return sharedPreferences.getString(SAVED_PL, "no saved playlist");
    }

    public ArrayList<HashMap<String, String>> getSavedPlaylist(String res) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        String[] temp = res.split("@pl_");

        if (!res.equals("no saved playlist")) {
            for (String aTemp : temp) {
                if (!aTemp.equals("")) {
                    String[] pl = aTemp.split("::");

                    HashMap<String, String> hmPl = new HashMap<String, String>();

                    hmPl.put("id", pl[0]);
                    hmPl.put("name", pl[1]);
                    hmPl.put("date", pl[2]);
                    hmPl.put("artist", pl[3]);
                    hmPl.put("variety", pl[4]);
                    hmPl.put("adventurousness", pl[5]);
                    hmPl.put("loudness", pl[6]);
                    hmPl.put("genre", pl[7]);
                    hmPl.put("max_results", pl[8]);

                    list.add(hmPl);
                }
            }
        }

        return list;
    }

    public ArrayList<HashMap<String, String>> getSavedPlaylist(Context ctx) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        if(sharedPreferences == null)
            sharedPreferences = ctx.getSharedPreferences(ARGS_PLAYLIST, Context.MODE_PRIVATE);

        String res = sharedPreferences.getString(SAVED_PL, "no saved playlist");

        Toast toast = Toast.makeText(ctx, res,
                Toast.LENGTH_LONG);
        toast.show();

        String[] temp = res.split("@pl_");

        // DEBUG
        //sharedPreferences.edit().clear().apply();

        if (!res.equals("no saved playlist")) {
            for (String aTemp : temp) {
                if (!aTemp.equals("")) {
                    String[] pl = aTemp.split("::");

                    HashMap<String, String> hmPl = new HashMap<String, String>();

                    hmPl.put("id", pl[0]);
                    hmPl.put("name", pl[1]);
                    hmPl.put("date", pl[2]);
                    hmPl.put("artist", pl[3]);
                    hmPl.put("variety", pl[4]);
                    hmPl.put("adventurousness", pl[5]);
                    hmPl.put("loudness", pl[6]);
                    hmPl.put("genre", pl[7]);
                    hmPl.put("max_results", pl[8]);

                    list.add(hmPl);
                }
            }
        }

        return list;
    }

    public void savePlaylist(Context ctx, HashMap params) {
        if(sharedPreferences == null)
            sharedPreferences = ctx.getSharedPreferences(ARGS_PLAYLIST, Context.MODE_PRIVATE);

        saved_count++;

        String res = "@pl_";
        res += String.valueOf(saved_count);
        res += "::";
        res += params.get("name") == null ? "_" : params.get("name");
        res += "::";
        res += new Date().toString();
        res += "::";
        res += params.get("artist") == null ? "_" : params.get("artist");
        res += "::";
        res += params.get("variety")  == null ? "_" : params.get("variety");
        res += "::";
        res += params.get("adventurousness")  == null ? "_" : params.get("adventurousness");
        res += "::";
        res += params.get("loudness")  == null ? "_" : params.get("loudness");
        res += "::";
        res += params.get("genre")  == null ? "_" : params.get("genre");
        res += "::";
        res += params.get("max_results")  == null ? "_" : params.get("max_results");

        Toast toast = Toast.makeText(ctx, res,
                Toast.LENGTH_LONG);
        toast.show();

        sharedPreferences.edit().clear();
        sharedPreferences.edit().putString(SAVED_PL, res).apply();
    }
}
