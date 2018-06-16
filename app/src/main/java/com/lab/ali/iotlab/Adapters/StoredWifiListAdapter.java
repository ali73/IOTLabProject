package com.lab.ali.iotlab.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lab.ali.iotlab.Models.MyWifiInfo;
import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.ViewHolders.StoredWifiItem;
import com.lab.ali.iotlab.ViewHolders.WifiItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StoredWifiListAdapter extends RecyclerView.Adapter<StoredWifiItem>{
    Context context;
    List<MyWifiInfo> _data;


    public StoredWifiListAdapter(Context context){
        this.context = context;
        this._data = new ArrayList<>();
    }
    @NonNull
    @Override
    public StoredWifiItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoredWifiItem(LayoutInflater.from(context).inflate(R.layout.rc_item_stored_wifi,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoredWifiItem holder, int position) {
        holder.getSsid().setText(_data.get(position).getSSID());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM DD");
        holder.getDate().setText(dateFormat.format(_data.get(position).getDate()));
        holder.getLatitude().setText(String.valueOf(_data.get(position).getLatitude()));
        holder.getLongtitude().setText(String.valueOf(_data.get(position).getLongtitude()));
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }

    public void add(MyWifiInfo wifiInfo){
        this._data.add(wifiInfo);
        notifyDataSetChanged();
    }
}
