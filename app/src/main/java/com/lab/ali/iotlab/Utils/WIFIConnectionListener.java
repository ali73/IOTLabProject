package com.lab.ali.iotlab.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by ali on 3/2/18.
 */

public class WIFIConnectionListener extends BroadcastReceiver {

    Activity context;
    public WIFIConnectionListener(Activity context){
        this.context = context;
    }
    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
            Log.d("CONNECTED",intent.getExtras().toString());
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            boolean connected = info.isConnected();
            int ip  = MyWifiManager.getInstance(context).getWifiManager().getConnectionInfo().getIpAddress();
            String ESSID = MyWifiManager.getInstance(context).getWifiManager().getConnectionInfo().getSSID();
            String ipString = String.format("%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff));
            if (connected){
                Log.d("IPADDRESS",ipString);
                AlertDialog dialog = new AlertDialog.Builder(context).create();
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                context.unregisterReceiver(WIFIConnectionListener.this);
                dialog.setMessage(ipString);
                dialog.setTitle(ESSID);
                dialog.show();
            }

        }

    }
}
