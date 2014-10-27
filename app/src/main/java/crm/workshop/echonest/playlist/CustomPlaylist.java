package crm.workshop.echonest.playlist;

import com.echonest.api.v4.Playlist;
import com.echonest.api.v4.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mael on 17/10/2014.
 */
public class CustomPlaylist extends Playlist {

    private List<Song> CustomList;

    public CustomPlaylist(List<Song> songs) {
        super(songs);

        CustomList =  this.getSongs();
    }

    public CustomPlaylist(Playlist list) {
        super(list.getSongs());
        CustomList =  this.getSongs();
    }

    public List<Song> getSomeSongs(int count) {
        ArrayList<Song> temp    = new ArrayList<Song>();
        for (int i = 0; i < count; i++) {
            if (!CustomList.isEmpty()) {
                temp.add(CustomList.get(0));
                CustomList.remove(0);
            }
        }
        return (List<Song>) temp;
    }
}
