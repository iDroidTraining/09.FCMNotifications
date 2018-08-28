package ws.idroid.pushnotifications.vendors.fcm;

import android.app.*;
import android.content.*;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.google.firebase.messaging.*;

import ws.idroid.pushnotifications.ui.MainActivity;

public class MessageService extends FirebaseMessagingService {
    private NotificationChannel mChannel;
    private NotificationManager notifManager;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        String myEmail = "m.abualzait@gmail.com";
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            showPushNotification("title", remoteMessage.getNotification().getBody());
        }
    }

    private void showPushNotification(String title, String description) {
        if (notifManager == null) {
            notifManager = (NotificationManager) getSystemService
                    (Context.NOTIFICATION_SERVICE);
        }
        NotificationCompat.Builder builder;
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (mChannel == null) {
                mChannel = new NotificationChannel
                        ("0", "Offers", NotificationManager.IMPORTANCE_HIGH);
                mChannel.setDescription("Disable / Enable Offers Notifications");
                mChannel.enableVibration(true);
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, "0");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 1251, intent, PendingIntent
                    .FLAG_ONE_SHOT);
            builder.setContentTitle(title)
                    .setSmallIcon(getNotificationIcon()) // required
                    .setContentText(description)  // required
                    .setDefaults(Notification.DEFAULT_ALL)

                    .setLargeIcon(BitmapFactory.decodeResource
                            (getResources(), ws.idroid.pushnotifications.R.mipmap.ic_launcher))
                    .setBadgeIconType(ws.idroid.pushnotifications.R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri
                            (RingtoneManager.TYPE_NOTIFICATION));
            Notification notification = builder.build();
            notifManager.notify(1231231231, notification);
        } else {
            intent = new Intent(this, MainActivity.class);
            intent.putExtra("message", description);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(this, 1251, intent, PendingIntent
                    .FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(getBaseContext(), ws.idroid
                            .pushnotifications.R.color.colorPrimary))
                    .setSound(defaultSoundUri)
                    .setSmallIcon(getNotificationIcon())
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{1000, 1000})
                    .setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle(title)
                            .bigText(description));
            NotificationManager notificationManager = (NotificationManager) getSystemService
                    (Context.NOTIFICATION_SERVICE);
            notificationManager.notify(9999999, notificationBuilder.build());
        }
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? ws.idroid.pushnotifications.R.mipmap.ic_launcher : ws.idroid
                .pushnotifications.R.mipmap.ic_launcher;
    }
}
