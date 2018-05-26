package com.lab.ali.iotlab.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lab.ali.iotlab.R;

public class GATTServiceItem  extends RecyclerView.ViewHolder{
    private TextView textView;
    private TextView Type;
    private TextView characteristics;
    public GATTServiceItem(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.serviceUUID);
        Type = itemView.findViewById(R.id.serviceType);
        characteristics = itemView.findViewById(R.id.characteristic);
    }

    public TextView getTextView() {
        return textView;
    }

    public TextView getType() {
        return Type;
    }

    public TextView getCharacteristics() {
        return characteristics;
    }
}
