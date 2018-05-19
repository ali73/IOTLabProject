package com.lab.ali.iotlab.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lab.ali.iotlab.R;

public class GATTServiceItem  extends RecyclerView.ViewHolder{
    TextView textView;
    public GATTServiceItem(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textview);
    }

    public TextView getTextView() {
        return textView;
    }
}
