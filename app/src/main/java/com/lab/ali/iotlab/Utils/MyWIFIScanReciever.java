package com.lab.ali.iotlab.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.lab.ali.iotlab.Activities.Activity_Wifi;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.net.wifi.WifiManager.SCAN_RESULTS_AVAILABLE_ACTION;

/**
 * Created by ali on 2/23/18.
 */

public class MyWIFIScanReciever extends BroadcastReceiver {
    Activity_Wifi activity;

    public MyWIFIScanReciever (Activity_Wifi activity){
        super();
        this.activity = activity;
    }
    public MyWIFIScanReciever(){}
    @Override
    public void onReceive(Context context, Intent intent) {
        List<ScanResult> scanResults =  MyWifiManager.getInstance(context).getWifiManager().getScanResults();
        Collections.sort(scanResults, new Comparator<ScanResult>() {
            @Override
            public int compare(ScanResult o1, ScanResult o2) {
                return o2.level-o1.level;
            }
        });
        if (scanResults.size()>4)
            scanResults = scanResults.subList(0,4);
        Log.i("ScanResult",scanResults.toString());
        activity.getAdapter().addResults(scanResults);
        activity.getLoadingDialog().dismiss();
//        activity.getMyWifiManager().getWifiManager()
        activity.unregisterReceiver(this);
    }
}
