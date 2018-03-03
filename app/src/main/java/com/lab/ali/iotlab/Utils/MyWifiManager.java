package com.lab.ali.iotlab.Utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.List;

/**
 * Created by ali on 2/23/18.
 */

public class MyWifiManager {
    String Tag = "MyWifiManager";
    private static WifiManager wifiManager;
    private static MyWifiManager instance;
    private Context context ;
    private MyWifiManager(Context context){
        this.context = context;
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }
    public static MyWifiManager getInstance(Context context) {
        if (instance==null){
            instance = new MyWifiManager(context);
        }
        return instance;
    }
    public List<Object> getWifiList(){

        try {
            wifiManager.startScan();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

//    public void scanForWifi(){
//        if (wifiManager!=null){
//                wifiManager.startScan();
//        }
//    }

    public WifiManager getWifiManager() {
        return wifiManager;
    }
}
