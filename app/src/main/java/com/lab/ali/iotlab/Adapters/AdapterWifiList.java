package com.lab.ali.iotlab.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.Utils.WIFIConnectionListener;
import com.lab.ali.iotlab.ViewHolders.WifiItem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.zip.DeflaterInputStream;
import java.util.zip.Inflater;

/**
 * Created by ali on 2/24/18.
 */

public class AdapterWifiList extends RecyclerView.Adapter<WifiItem> {
    List<ScanResult> _data;
    Activity context;

    public AdapterWifiList(Activity context){
        this.context = context;
        this._data = new ArrayList<>();
    }
    @Override
    public WifiItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WifiItem(LayoutInflater.from(context).inflate(R.layout.rc_item_wifi,parent,false));
    }

    @Override
    public void onBindViewHolder(WifiItem holder, final int position) {

        holder.getESSID().setText(_data.get(position).SSID + String.format(" (%s) ", _data.get(position).BSSID));
        holder.getStrength().setText("Strength : "+String.valueOf( WifiManager.calculateSignalLevel(_data.get(position).level, 5)));
        holder.getEncryption().setText(_data.get(position).capabilities.contains("WPA2")?"Encrypted":"Not encrypted");
        holder.getWifi().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_data.get(position).capabilities.contains("WPA2")){
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(LayoutInflater.from(context).inflate(R.layout.wifi_password_dialog,null,false));
                    dialog.show();
                    dialog.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String password = ((TextView)dialog.findViewById(R.id.passwordInput)).getText().toString();
                            connectToWIFI(position,password);
                            dialog.dismiss();
                        }
                    });
                }
                else
                connectToWIFI(position,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }

    public void addResults(List<ScanResult> list){
        this._data.clear();
        this._data.addAll(list);
        notifyDataSetChanged();
    }

    private void connectToWIFI(int position , @Nullable String password){
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + _data.get(position).SSID + "\"";
        if (password!=null){
            conf.preSharedKey = String.format("\"%s\"", password);
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        }
        else {
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            conf.status = WifiConfiguration.Status.ENABLED;
        }
        WIFIConnectionListener         wifiConnectionListener = new WIFIConnectionListener(context);
        context.registerReceiver(
                wifiConnectionListener,
                new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        );
        final WifiManager wifiManager =(WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int id = wifiManager.addNetwork(conf);
        wifiManager.disconnect();
        if(id>0){
            for (WifiConfiguration i:wifiManager.getConfiguredNetworks() ){
                if (i.SSID!=null && i.SSID.equals("\"" + _data.get(position).SSID + "\"")){
                    wifiManager.enableNetwork(id,true);
                    wifiManager.reconnect();
                    Log.d("Connected",wifiManager.getConnectionInfo().toString());
                }
            }
        }
    }
}
