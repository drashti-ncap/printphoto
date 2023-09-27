package com.mobile.cover.photo.editor.back.maker.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.SplashScreen;

import org.json.JSONObject;

import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.SplashScreenKt.ARG_IS_OFFER;

/**
 * Created by vishalvasoya on 7/26/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int notification_id;
    private RemoteViews remoteViews;
    private Context context;


    private String Title, Message, Category, Data;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //It is optional

        Log.e("TAG", "From: " + remoteMessage.getNotification());
        Log.e("TAG", "data :===============>" + remoteMessage.getData());
        Log.e("TAG", "data :===============>" + remoteMessage.getData().get("custom"));


        Log.e(TAG, "onMessageReceived: " + remoteMessage.getData().get("category_id"));
        Log.e(TAG, "onMessageReceived: " + remoteMessage.getData().get("category_name"));

        if (remoteMessage.getData().get("custom") == null) {
            context = this;


            Log.d(TAG, "From: " + remoteMessage.getFrom());
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getData());
            try {
                JSONObject js = new JSONObject(remoteMessage.getData());
                Title = js.optString("title");
                Message = js.optString("user_name");


//            Log.e(TAG, "onMessageReceived: ====>"+js.optString("category_id"));
//            Log.e(TAG, "onMessageReceived: ====>"+js.optString("category_name"));

//            Share.notification_category_id=js.optString("category_id");
//            Share.notification_category_name=js.optString("category_name");
                // Category = js.optString("category");
                //Data = js.optString("data");
                //Type = js.optString("type");
                Log.e("notification msdg+", Message);


            } catch (Exception e) {
                e.printStackTrace();
            }


            Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(ARG_IS_OFFER, false);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

//        String channel_id = getString(R.string.defult_notification_channel_id);

            String channel_id = "FCMchenal";
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel_id)
                    .setSmallIcon(R.drawable.appicon)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.appicon))
                    .setContentTitle(Title)
                    .setContentText(Message)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);


            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = notificationBuilder.build();
            notification.defaults |= Notification.DEFAULT_VIBRATE;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(channel_id, "FCMchannel", importance);
                manager.createNotificationChannel(channel);
                Log.e("ChennalFCM", "____oreo__");
                Log.e("ChennalFCM", "____oreo__" + channel_id);

            }
//
            notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            manager.notify(0, notificationBuilder.build());
        }


//    }
    }
}