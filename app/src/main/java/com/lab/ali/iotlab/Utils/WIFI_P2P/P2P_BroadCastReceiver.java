package com.lab.ali.iotlab.Utils.WIFI_P2P;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;

import com.lab.ali.iotlab.Utils.CallBack;

public class P2P_BroadCastReceiver  extends BroadcastReceiver{
    String tag = "P2P_BroadCastReceiver";
    private CallBack p2pStateChangedCallback;
    private CallBack p2pPeerChangedCallback;
    private CallBack p2pConnectionChagnedCallback;
    private CallBack p2pDeviceChangedCallback;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)){
            if (p2pStateChangedCallback!=null){
                p2pStateChangedCallback.callBack(intent);
            }
            else
                Log.w(tag, String.format("No Callback for %s specified", WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION));
        }
        else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){
            if (p2pPeerChangedCallback!=null)
                p2pPeerChangedCallback.callBack(intent);
            else
                Log.w(tag, String.format("No Callback for %s specified", WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION));
        }
        else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)){
            if (p2pConnectionChagnedCallback!=null)
                p2pConnectionChagnedCallback.callBack(null);
            else
                Log.w(tag,"No Callback for Connection changed state specified");
        }
        else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)){
            if (p2pDeviceChangedCallback!=null)
                p2pDeviceChangedCallback.callBack(null);
            else
                Log.w(tag,"No Callback for wifi device changed action specified");
        }
    }


    public void setP2pPeerChangedCallback(CallBack p2pPeerChangedCallback) {
        this.p2pPeerChangedCallback = p2pPeerChangedCallback;
    }

    public void setP2pConnectionChagnedCallback(CallBack p2pConnectionChagnedCallback) {
        this.p2pConnectionChagnedCallback = p2pConnectionChagnedCallback;
    }

    public void setP2pDeviceChangedCallback(CallBack p2pDeviceChangedCallback) {
        this.p2pDeviceChangedCallback = p2pDeviceChangedCallback;
    }

    public CallBack getP2pStateChangedCallback() {
        return p2pStateChangedCallback;
    }

    public void setP2pStateChangedCallback(CallBack p2pStateChangedCallback) {
        this.p2pStateChangedCallback = p2pStateChangedCallback;
    }


}

