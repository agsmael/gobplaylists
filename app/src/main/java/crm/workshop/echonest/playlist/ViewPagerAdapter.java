package crm.workshop.echonest.playlist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;

/**
 * Created by lbeltramo on 15/10/2014.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    HashMap<Integer, String> fragmentMap;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentMap = new HashMap<Integer, String>();
        fragmentMap.put(0, "Alec Empire");
        fragmentMap.put(1, "U2");
        fragmentMap.put(2, "Primal Fear");
    }

    @Override
    public Fragment getItem(int i) {
        return null;
        //return PlayListFragment.newInstance(10 ,10, fragmentMap.get(i));
    }

    @Override
    public int getCount() {
        return 3;
    }
}
