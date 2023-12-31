package com.mobile.cover.photo.editor.back.maker.Commen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import com.mobile.cover.photo.editor.back.maker.R;

public class NetworkManager {
    public static AlertDialog alertDialog;

    @SuppressWarnings("deprecation")
    public static boolean isInternetConnected(final Activity context) {

        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netinfo = cm.getActiveNetworkInfo();

            if (netinfo != null && netinfo.isConnectedOrConnecting()) {

                return true;
            } else {
                try {
                    if (alertDialog != null && alertDialog.isShowing())
                        alertDialog.cancel();

                    alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle(context.getResources().getString(R.string.internet_connection));
                    alertDialog.setMessage(context.getResources().getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setCanceledOnTouchOutside(false);
                    // Setting OK Button
                    alertDialog.setButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
							/*if(isInternetConnected(context))
							{
								context.recreate();
							}*/
                        }
                    });
                    // Showing Alert Message
                    alertDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // PublicMethods.showToast(context,
                // context.getString(R.string.internet_message));
                return false;
            }
        }
        return true;
    }

    public static boolean isInternetConnectedDailog(final Context context) {


        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {

            return true;
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(context.getResources().getString(R.string.internet_connection));
            alertDialog.setMessage(context.getResources().getString(R.string.slow_connect));
//	        // Setting OK Button
            alertDialog.setButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
//	            	((Activity) context).finish();
                    dialog.dismiss();
                }
            });
//	        // Showing Alert Message
            alertDialog.show();
            // PublicMethods.showToast(context,
            // context.getString(R.string.internet_message));

//			Intent nointernetIntent = new Intent(context, NoInternet.class);
//			context.startActivity(nointernetIntent);

            return false;
        }

    }


    public static boolean hasInternetConnected(final Activity context) {

        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netinfo = cm.getActiveNetworkInfo();

            return netinfo != null && netinfo.isConnectedOrConnecting();
        }
        return true;
    }

    public static boolean isWiFiConnected(Context context) {
        // Create object for ConnectivityManager class which returns network
        // related info
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // If connectivity object is not null
        if (connectivity != null) {
            // Get network info - WIFI internet access
            NetworkInfo info = connectivity
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                // Look for whether device is currently connected to WIFI
                // network
                return info.isConnected();
            }
        }
        return false;
    }

    public static boolean isGPSConnected(Context context) {
        LocationManager service = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean status = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return status;
    }

    public static void showGPSSettingsAlert(final Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS Settings");

        // Setting Dialog Message
        alertDialog
                .setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton(context.getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }

    /**
     * Display a dialog that user has no internet connection lauch Settings
     * Options
     */
    public static void showInternetSettingsAlert(final Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("Internet Settings");

        // Setting Dialog Message
        alertDialog
                .setMessage("Internet is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_WIRELESS_SETTINGS);
                        context.startActivity(intent);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton(context.getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }

}
