package com.bitbucket.lonelydeveloper97.wifiproxysettingslibrary.proxy_change_realisation.wifi_network;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ProxyInfo;
import android.os.Build;

import com.bitbucket.lonelydeveloper97.wifiproxysettingslibrary.proxy_change_realisation.wifi_network.exceptions.NullWifiConfigurationException;
import com.bitbucket.lonelydeveloper97.wifiproxysettingslibrary.proxy_change_realisation.wifi_network.exceptions.ApiNotSupportedException;
import com.bitbucket.lonelydeveloper97.wifiproxysettingslibrary.proxy_change_realisation.wifi_network.exceptions.WifiProxyNotSettedException;
import com.bitbucket.lonelydeveloper97.wifiproxysettingslibrary.proxy_change_realisation.wifi_network.reflection_realisation.ReflectionHelper;
import com.bitbucket.lonelydeveloper97.wifiproxysettingslibrary.proxy_change_realisation.wifi_network.wifi_proxy_changing_realisations.BaseWifiConfiguration;
import com.bitbucket.lonelydeveloper97.wifiproxysettingslibrary.proxy_change_realisation.wifi_network.wifi_proxy_changing_realisations.ProxyChanger;
import com.bitbucket.lonelydeveloper97.wifiproxysettingslibrary.proxy_change_realisation.wifi_network.wifi_proxy_changing_realisations.ProxySettings;

import java.lang.reflect.InvocationTargetException;


public class WifiConfiguration extends BaseWifiConfiguration implements ProxyChanger {

    public WifiConfiguration(Context context) throws NullWifiConfigurationException {
        super(context);
    }

    public static WifiConfiguration createFromCurrentContext(Context context)
            throws NullWifiConfigurationException {
        return new WifiConfiguration(context);
    }

    @Override
    public ProxySettings getProxySettings()
            throws NoSuchFieldException, IllegalAccessException {
        return ProxySettings.valueOf(String.valueOf(ReflectionHelper.getDeclaredField(getIpConfigurationObject(), "proxySettings")));
    }

    @Override
    public void setProxySettings(ProxySettings proxySettings)
            throws NoSuchFieldException, IllegalAccessException {
        ReflectionHelper.setEnumField(getIpConfigurationObject(), proxySettings.getValue(), "proxySettings");
    }

    @Override
    public void setProxyHostAndPort(String host, int port)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchFieldException {
        setProxyInfo(ProxyInfoConstructor.proxyInfo(host, port));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public String getProxyHost()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            ApiNotSupportedException {
        ProxyInfo info = getProxyInfo();
        if (info == null)
            throw new WifiProxyNotSettedException();
        return info.getHost();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int getProxyPort()
            throws ApiNotSupportedException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        ProxyInfo info = getProxyInfo();
        if (info == null)
            throw new WifiProxyNotSettedException();
        return info.getPort();
    }

    @Override
    public boolean isProxySetted()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            ApiNotSupportedException, NoSuchFieldException {
        return !(getProxyInfo() == null);
    }

    private Object getIpConfigurationObject()
            throws NoSuchFieldException, IllegalAccessException {
        return ReflectionHelper.getDeclaredField(wifiConfiguration, "mIpConfiguration");
    }

    private ProxyInfo getProxyInfo()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (ProxyInfo) ReflectionHelper.getMethodAndInvokeIt(wifiConfiguration, "getHttpProxy");
    }

    private void setProxyInfo(ProxyInfo proxyInfo)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException,
            NoSuchFieldException {
        ReflectionHelper.getMethodAndInvokeIt(wifiConfiguration, "setHttpProxy", proxyInfo);
    }

}
