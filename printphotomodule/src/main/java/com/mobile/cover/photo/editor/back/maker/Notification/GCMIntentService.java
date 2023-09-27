package com.mobile.cover.photo.editor.back.maker.Notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.NotificationCompat.BigPictureStyle;
import androidx.core.app.NotificationCompat.Builder;

import com.google.android.gcm.GCMBaseIntentService;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.onesignal.shortcutbadger.ShortcutBadger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class GCMIntentService extends GCMBaseIntentService {
    private static final int BIG_PICTURE_STYLE = 2;
    private static final String TAG = "GCMIntentService";
    static Context ctx;
    static String link = "";
    static String msg = null;
    private static NotificationManager mNotificationManager;

    public GCMIntentService() {
        super(CommonUtilities.SENDER_ID);
    }

    @SuppressLint({"NewApi"})
    private static void generateNotification(Context context, String message, String is_file, String image, String badge) {
        if (!Build.BRAND.contains("Sony")) {
            ShortcutBadger.applyCount(context, Integer.valueOf(badge).intValue());
        }
        long when = System.currentTimeMillis();
        ctx = context;
        msg = message;
        Log.e("message", "message" + message);
        try {
            Bitmap myBitmap = BitmapFactory.decodeStream((InputStream) new URL(link).getContent());
        } catch (IOException e) {
        }
        SharedPreferences logoutPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean logout = Boolean.valueOf(logoutPreferences.getBoolean("logout", false));
        Boolean login = Boolean.valueOf(logoutPreferences.getBoolean("Login", true));
        if (!logout.booleanValue() || login.booleanValue()) {
            Log.e("after login push notification", "login");
            mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            try {
                Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.appicon);
                Intent intent = new Intent(context, HomeMainActivity.class);
                intent.putExtra("is_file", is_file);
//                intent.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, image);
                intent.putExtra("msg", message);
                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                Builder b = new Builder(context);
                b.setAutoCancel(true).setDefaults(-1).setWhen(System.currentTimeMillis()).setLargeIcon(bm).setSmallIcon(R.drawable.appicon).setTicker("context.getResources().getString(R.string.app_name)").setContentTitle("context.getResources().getString(R.string.app_name)").setContentText(msg).setDefaults(5).setContentIntent(contentIntent);
                ((NotificationManager) context.getSystemService(NOTIFICATION_SERVICE)).notify(1, b.build());
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        Log.e("after logout", "logout");
    }

    private static Builder Notification(Builder builder) {
        return null;
    }

    public static Notification setBigTextStyleNotification() {
        Bitmap remote_picture = null;
        BigPictureStyle notiStyle = new BigPictureStyle();
        notiStyle.setBigContentTitle("eFAADAH");
        notiStyle.setSummaryText(msg);
        try {
            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(link).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        notiStyle.bigPicture(remote_picture);
        Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.appicon);
        notiStyle.bigPicture(remote_picture);
        return new Builder(ctx).setSmallIcon(R.drawable.appicon).setAutoCancel(true).setLargeIcon(bitmap).setContentTitle("eFAADAH").setContentText(msg).setStyle(notiStyle).build();
    }

    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
    }

    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
    }

    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        String message = intent.getExtras().getString("message");
        String file = intent.getExtras().getString("is_file");
        String image = intent.getExtras().getString("image");
        String badge = intent.getExtras().getString("badge");
        Log.i(TAG, "Received message" + message + "///" + file + "///" + badge);
        CommonUtilities.displayMessage(context, message);
        generateNotification(context, message, file, image, badge);
    }

    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
//        String message = getString(R.string.gcm_deleted) + total;
    }

    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
    }

    protected boolean onRecoverableError(Context context, String errorId) {
        Log.i(TAG, "Received recoverable error: " + errorId);
        return super.onRecoverableError(context, errorId);
    }
}
