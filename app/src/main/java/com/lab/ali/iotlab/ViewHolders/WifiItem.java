package com.lab.ali.iotlab.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lab.ali.iotlab.R;

/**
 * Created by ali on 2/24/18.
 */

public class WifiItem extends RecyclerView.ViewHolder {
    private TextView BSSID;
    private TextView ESSID;
    private TextView strength;
    private TextView encryption;
    private RelativeLayout wifi;
    public WifiItem(View itemView) {
        super(itemView);
        this.BSSID = itemView.findViewById(R.id.BSSID);
        this.ESSID = itemView.findViewById(R.id.ESSID);
        this.strength = itemView.findViewById(R.id.signalStrength);
        this.encryption = itemView.findViewById(R.id.encryption);
        this.wifi = itemView.findViewById(R.id.signal_item);
    }

    public TextView getEncryption() {
        return encryption;
    }

    public void setEncryption(TextView encryption) {
        this.encryption = encryption;
    }

    public TextView getStrength() {
        return strength;
    }

    public void setStrength(TextView strength) {
        this.strength = strength;
    }

    public TextView getESSID() {
        return ESSID;
    }

    public void setESSID(TextView ESSID) {
        this.ESSID = ESSID;
    }

    public TextView getBSSID() {
        return BSSID;
    }

    public void setBSSID(TextView BSSID) {
        this.BSSID = BSSID;
    }

    public RelativeLayout getWifi() {
        return wifi;
    }

    public void setWifi(RelativeLayout wifi) {
        this.wifi = wifi;
    }
}
