package com.bitbucket.lonelydeveloper97.wifiproxysettingslibrary.proxy_change_realisation.wifi_network.wifi_proxy_changing_realisations;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.bitbucket.lonelydeveloper97.wifiproxysettingslibrary.proxy_change_realisation.wifi_network.exceptions.NullWifiConfigurationException;

import java.util.List;

public class BaseWifiConfiguration {

    protected WifiConfiguration wifiConfiguration;


    protected BaseWifiConfiguration(WifiConfiguration wifiConfiguration)
            throws NullWifiConfigurationException {
        if (wifiConfiguration == null)
            throw new NullWifiConfigurationException();
        this.wifiConfiguration = wifiConfiguration;
    }

    protected BaseWifiConfiguration(Context context)
            throws NullWifiConfigurationException {
        this(getCurrentWifiConfigurationFromContext(context));
    }

    private static WifiConfiguration getCurrentWifiConfigurationFromContext(Context context) {


        final WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        manager.startScan();

        List<ScanResult> wifiList = manager.getScanResults();

        for (ScanResult scanResult : wifiList) {
            Log.e("TAG", "Name: " + scanResult.SSID + " - " + scanResult.capabilities);
        }


        if (manager == null) {
            Log.e("TAG", "null");
            return null;
        }


        List<WifiConfiguration> wifiConfigurationList = manager.getConfiguredNetworks();


        if (!manager.isWifiEnabled() || wifiConfigurationList == null || wifiConfigurationList.isEmpty())
            return null;
        return findWifiConfigurationByNetworkId(wifiConfigurationList, manager.getConnectionInfo().getNetworkId());

    }

    private static WifiConfiguration findWifiConfigurationByNetworkId(List<WifiConfiguration> wifiConfigurationList, int networkId) {
        for (WifiConfiguration wifiConf : wifiConfigurationList) {
            if (wifiConf.networkId == networkId)
                return wifiConf;
        }
        return null;
    }

    public WifiConfiguration getWifiConfiguration() {
        return wifiConfiguration;
    }

}
