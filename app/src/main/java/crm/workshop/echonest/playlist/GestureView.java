package crm.workshop.echonest.playlist;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import crm.workshop.echonest.R;

public class GestureView extends LinearLayout {

    GestureDetector mgDetect;



    public GestureView(Context context) {
        super(context);

        inflate(context, R.layout.fragment_song, this);

        mgDetect = new GestureDetector(context, new GestureManager(this));
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mgDetect.onTouchEvent(event);

        return super.onTouchEvent(event);
    }
}
