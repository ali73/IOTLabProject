package com.lab.ali.iotlab.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class WifiScannerReceiver extends BroadcastReceiver {
    private CallBack callBack ;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("WifiBroadcastReceiver","Received broadcast");
        callBack.callBack(intent);
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
