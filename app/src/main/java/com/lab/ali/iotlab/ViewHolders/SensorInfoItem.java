package com.lab.ali.iotlab.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lab.ali.iotlab.R;

public class SensorInfoItem  extends RecyclerView.ViewHolder{
    private TextView name;
    private TextView vendor;
    private TextView version;
    private TextView maxRange;
    private TextView minDelay;

    public SensorInfoItem(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        vendor = itemView.findViewById(R.id.Vendor);
        version = itemView.findViewById(R.id.version);
        maxRange = itemView.findViewById(R.id.maxRange);
        minDelay = itemView.findViewById(R.id.minDelay);
    }

    public TextView getName() {
        return name;
    }

    public TextView getVendor() {
        return vendor;
    }

    public TextView getVersion() {
        return version;
    }

    public TextView getMaxRange() {
        return maxRange;
    }


    public TextView getMinDelay() {
        return minDelay;
    }
}
