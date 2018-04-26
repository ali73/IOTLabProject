package com.lab.ali.iotlab.Adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.ViewHolders.BluetoothDeviceItem;

import java.util.ArrayList;
import java.util.List;

public class BluetoothListAdapter extends RecyclerView.Adapter<BluetoothDeviceItem>{
    List<BluetoothDevice> _data;
    Context context;

    public BluetoothListAdapter(Context context){
        this.context=  context;
        this._data = new ArrayList<>();
    }
    @Override
    public BluetoothDeviceItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BluetoothDeviceItem(LayoutInflater.from(context).inflate(R.layout.rc_item_bluetooth_device,parent,false));
    }

    @Override
    public void onBindViewHolder(BluetoothDeviceItem holder, int position) {
        holder.setMac(_data.get(position).getAddress());
        holder.setName(_data.get(position).getName());
        holder.getLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO: Connect to device
            }
        });
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }
    public void  addAll(List<BluetoothDevice> list){
        this._data.addAll(list);
        this.notifyDataSetChanged();
    }
    public void addDevice(BluetoothDevice bluetooth){
        _data.add(bluetooth);
        notifyDataSetChanged();
    }
    public void clear(){
        this._data.clear();
        notifyDataSetChanged();
    }
}
