package com.lab.ali.iotlab.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lab.ali.iotlab.R;

public class WifiP2PDeviceItem  extends RecyclerView.ViewHolder{
    private RelativeLayout layout;
    private TextView title,mac;
    public WifiP2PDeviceItem(View itemView) {
        super(itemView);
        this.layout = itemView.findViewById(R.id.layout);
        this.title = itemView.findViewById(R.id.deviceName);
        this.mac = itemView.findViewById(R.id.deviceMac);
    }

    public RelativeLayout getLayout() {
        return layout;
    }

    public TextView getMac() {
        return mac;
    }

    public TextView getTitle() {
        return title;
    }
}
