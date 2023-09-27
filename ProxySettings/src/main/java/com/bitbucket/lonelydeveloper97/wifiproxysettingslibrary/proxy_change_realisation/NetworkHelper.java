package com.bitbucket.lonelydeveloper97.wifiproxysettingslibrary.proxy_change_realisation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;


public abstract class NetworkHelper {

    public static WifiManager getWifiManager(Context context) {
        return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public static NetworkInfo getWifiNetworkInfo(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    }

    public static NetworkInfo getMobileNetworkInfo(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    }

    public static boolean isWifiConnected(Context context) {
        return NetworkHelper.getWifiNetworkInfo(context).isConnected();
    }


    /**
     * ToDo.. Return true if internet or wi-fi connection is working fine
     * <p>
     * Required permission
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     * <uses-permission android:name="android.permission.INTERNET"/>
     *
     * @param mContext The context
     * @return true if you have the internet connection, or false if not.
     */
    public static boolean isOnline(Context mContext) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                if (activeNetwork.isConnected())
                    haveConnectedWifi = true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                if (activeNetwork.isConnected())
                    haveConnectedMobile = true;
            }
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    /**
     * ToDo.. Check vpn status
     *
     * @return true if vpn is activated otherwise false
     */
    public static boolean isVpnRunning() {
        String iface = "";
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.isUp())
                    iface = networkInterface.getName();
                Log.d("DEBUG", "IFACE NAME: " + iface);
                if (iface.contains("tun") || iface.contains("ppp") || iface.contains("pptp")) {
                    return true;
                }
            }
        } catch (SocketException e1) {
            e1.printStackTrace();
        }

        return false;
    }


}
