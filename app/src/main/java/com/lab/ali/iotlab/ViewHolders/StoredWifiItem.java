package com.lab.ali.iotlab.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lab.ali.iotlab.R;

public class StoredWifiItem  extends RecyclerView.ViewHolder{

    private TextView ssid,date,latitude,longtitude;

    public StoredWifiItem(View itemView) {
        super(itemView);
        ssid = itemView.findViewById(R.id.ssid);
        date = itemView.findViewById(R.id.date);
        latitude = itemView.findViewById(R.id.latitude);
        longtitude = itemView.findViewById(R.id.longtitde);
    }

    public TextView getSsid() {
        return ssid;
    }

    public TextView getDate() {
        return date;
    }

    public TextView getLatitude() {
        return latitude;
    }

    public TextView getLongtitude() {
        return longtitude;
    }
}
