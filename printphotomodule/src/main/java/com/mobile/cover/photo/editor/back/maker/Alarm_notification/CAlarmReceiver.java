package com.mobile.cover.photo.editor.back.maker.Alarm_notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.SplashScreen;

/**
 * Created by Bansi on 20-02-2018.
 */

public class CAlarmReceiver extends BroadcastReceiver {

    private static int MID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equalsIgnoreCase(getString(R.string.cancel))) {
//            Log.e(getString(R.string.cancel), "onReceive: =======>Cancel");
//        } else {
        // TODO Auto-generated method stub

        SharedPreferences preferences = context.getSharedPreferences("call_detail", Context.MODE_PRIVATE);
        MID = preferences.getInt("noti_count", 0);

        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mNotifyBuilder;
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        Intent notificationIntent = new Intent(context, SplashScreen.class);
        HomeMainActivity.selected = 2;
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent;

        pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        String message = "The item which are there in your cart will look good in your hand!!" + "\nGo and Buy your product with amazing offers";
        RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.notification_layout);

        mNotifyBuilder = new NotificationCompat.Builder(context, "context.getResources().getString(R.string.app_name)" + " ChannelId")
                .setSmallIcon(R.drawable.appicon)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setWhen(when)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(" ChannelId",
                    "context.getResources().getString(R.string.app_name)" + " Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(MID, mNotifyBuilder.build());
        MID++;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("noti_count", MID);
        editor.commit();
    }
//    }
}