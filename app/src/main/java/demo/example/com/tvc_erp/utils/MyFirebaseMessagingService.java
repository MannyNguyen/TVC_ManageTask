package demo.example.com.tvc_erp.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import demo.example.com.tvc_erp.R;
import demo.example.com.tvc_erp.ui.activity.BaseActivity;
import demo.example.com.tvc_erp.ui.activity.MainActivity;
import demo.example.com.tvc_erp.ui.activity.SplashScreenActivity;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by Quysunam on 11/2/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i("fuck u","CCCCCCCCCCCC");
        if (remoteMessage.getNotification() != null) {
            showNotification(remoteMessage.getNotification().getBody());
        }
        showNotification(remoteMessage.getData().get("body"), remoteMessage.getData().get("title"));
    }

    private void showNotification(String body, String title) {
        //Create number of notification on icon app
        if (!BaseActivity.isInApp) {
            Intent intent = new Intent(this, SplashScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.icon_logo)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(sound)
                    .setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            int num = SystemUtils.getNumsOfNotify(this) + 1;
            manager.notify(num, builder.build());
            SystemUtils.saveNumsOfNotify(num, this);
            ShortcutBadger.applyCount(this, num);
        }
    }

    private void showNotification(String body) {
        showNotification(body, "google");
    }
}
