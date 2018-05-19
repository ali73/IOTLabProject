package com.lab.ali.iotlab.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.Utils.CallBack;
import com.lab.ali.iotlab.ViewHolders.WifiP2PDeviceItem;

import java.util.ArrayList;
import java.util.List;

public class WifiP2PDeviceAdapter extends RecyclerView.Adapter<WifiP2PDeviceItem>  {
    List<WifiP2pDevice> _data;
    Context context;
    private CallBack connectionCallback;
    public WifiP2PDeviceAdapter(Context context){
        this._data = new ArrayList<>();
        this.context = context;
    }

    @Override
    public WifiP2PDeviceItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WifiP2PDeviceItem(LayoutInflater.from(context).inflate(R.layout.rc_item_wifi_p2p,parent,false));
    }

    @Override
    public void onBindViewHolder(WifiP2PDeviceItem holder, final int position) {
        holder.getMac().setText(_data.get(position).deviceAddress);
        holder.getTitle().setText(_data.get(position).deviceName);
        holder.getLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiP2pConfig config  = new WifiP2pConfig();
                config.deviceAddress = _data.get(position).deviceAddress;
                config.wps.setup = WpsInfo.PBC;
                Intent intent = new Intent();
                intent.putExtra("config",config);
                connectionCallback.callBack(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }
    public void add(WifiP2pDevice device){
        this._data.add(device);
        notifyDataSetChanged();
    }
    public void addAll(List<WifiP2pDevice> list){
        this._data.addAll(list);
        notifyDataSetChanged();
    }
    public void clear(){
        this._data.clear();
        notifyDataSetChanged();
    }

    public void setConnectionCallback(CallBack connectionCallback) {
        this.connectionCallback = connectionCallback;
    }
}
