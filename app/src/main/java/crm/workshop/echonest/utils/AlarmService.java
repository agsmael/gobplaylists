package crm.workshop.echonest.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import crm.workshop.echonest.playlist.PlayListActivity;

/**
 * Created by lbeltramo on 15/10/2014.
 */
public class AlarmService extends Service {
    private static final int NOTIFICATION_ID = 1;
    private static final String TAG = "ALARM";
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Context context = this.getApplicationContext();

        // Notifications
        Intent resultIntent = new Intent(context, PlayListActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(context,
                        0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder notifBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(android.R.drawable.ic_dialog_alert)
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true)
                        .setContentTitle("A notification!")
                        .setContentText("Notifcation content");

        int mNotificationId = 12;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, notifBuilder.build());

        stopSelf();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
