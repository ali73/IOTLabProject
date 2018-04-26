package com.lab.ali.iotlab.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lab.ali.iotlab.R;

public class BluetoothDeviceItem extends RecyclerView.ViewHolder{
    private RelativeLayout layout;
    private TextView mac,name;
    public BluetoothDeviceItem(View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.layout);
        mac = itemView.findViewById(R.id.device_mac);
        name = itemView.findViewById(R.id.device_name);
    }

    public RelativeLayout getLayout() {
        return layout;
    }
    public void setName(String name){
        this.name.setText(name);
    }
    public void setMac(String mac){
        this.mac.setText(mac);
    }
}
