package com.mobile.cover.photo.editor.back.maker.Paymentintegration;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.mobile.cover.photo.editor.back.maker.R;

public class ActionDialog extends DialogFragment {

    Communicator communicator;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        communicator = (Communicator) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Option");
        builder.setItems(R.array.selectAction, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    communicator.actionSelected("ResendOTP");
                } else if (which == 1) {
                    communicator.actionSelected("EnterOTPManually");
                }

            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                communicator.actionSelected(getString(R.string.cancel));
            }
        });

        /*builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/
        Dialog dialog = builder.create();
        return dialog;
    }
}
