package com.mobile.cover.photo.editor.back.maker.rateandfeedback;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.mobile.cover.photo.editor.back.maker.R;

public class RateDialog {


    //implementation 'com.github.sujithkanna:smileyrating:1.6.8'

    public static String DEVELOPER_NAME = "Background+Changer,+Eraser+%26+Booth+Photo+Editor";

    public static int RATING = -1;


    public static void ratingDialog(final Context mContext, final onRateListener listener) {


        try {

            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_finish_alert); //get layout from ExitDialog folder
            dialog.setCancelable(false);
            SmileRating smileRating = dialog.findViewById(R.id.smile_rating);
            Button btn_yes = dialog.findViewById(R.id.btn_yes);
            Button btn_no = dialog.findViewById(R.id.btn_no);

            btn_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });

            btn_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();

                    try {
                        moreApp(mContext, DEVELOPER_NAME);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                    listener.onRate(RATING);
                }
            });

            smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
                @Override
                public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                    dialog.dismiss();
                    switch (smiley) {
                        case SmileRating.BAD:
                        case SmileRating.OKAY:
                        case SmileRating.TERRIBLE:
                            RATING = 0;
                            break;
                        case SmileRating.GOOD:
                        case SmileRating.GREAT:
                            RATING = 1;
                            break;
                        default:
                            RATING = -1;
                    }
                }
            });
            dialog.show();
        } catch (Exception ignored) {
            Log.e("ignored", "ratingDialog: " + ignored);
        }
    }

    public static void rate_app(Context mContext) {
        try {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + mContext.getPackageName())));
        } catch (ActivityNotFoundException anfe) {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google" + ".com/store/apps/details?id=" + mContext.getPackageName())));
        }
    }

    /**
     * ToDo. Open our more apps on playstore
     *
     * @param mContext The context
     */
    public static void moreApp(Context mContext, String Developer) {
        try {
            String query = "pub:" + Developer; // change
            // query here
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://search?q=" + query));
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            mContext.startActivity(intent);

        } catch (Exception e) {
        }
    }

    public interface onRateListener {
        void onRate(int rate);
    }


}
