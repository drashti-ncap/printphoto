package com.mobile.cover.photo.editor.back.maker.Notification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public final class CommonUtilities {
    public static final String DISPLAY_MESSAGE_ACTION = "com.eFAADAH.pushnotifications.DISPLAY_MESSAGE";
    public static final String EXTRA_MESSAGE = "message";
    public static final String SENDER_ID = "840921078311";
    public static final String TAG = "GCM";
    static final String SERVER_URL = "http://efaadah.com/school_management/push_test/push_testing.php?";

    public static void displayMessage(Context context, String message) {
        Log.e("ccccccccccccccccc", "");
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra("message", message);
        context.sendBroadcast(intent);
    }
}
