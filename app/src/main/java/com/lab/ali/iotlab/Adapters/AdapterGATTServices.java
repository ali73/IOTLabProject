package com.lab.ali.iotlab.Adapters;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.ViewHolders.GATTServiceItem;

import java.util.ArrayList;
import java.util.List;

public class AdapterGATTServices extends RecyclerView.Adapter<GATTServiceItem> {
    Context context;
    List<BluetoothGattService> _data;
    public AdapterGATTServices(Context context){
        this.context = context;
        _data = new ArrayList<>();
    }

    public void  add(List<BluetoothGattService> list){
        this._data.addAll(list);
        notifyDataSetChanged();
    }

    public void  add(BluetoothGattService service){
        this._data.add(service);
        notifyDataSetChanged();
    }
    @Override
    public GATTServiceItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GATTServiceItem(LayoutInflater.from(context).inflate(R.layout.rc_item_gatt_service,parent,false));
    }

    @Override
    public void onBindViewHolder(GATTServiceItem holder, int position) {
        holder.getTextView().setText(_data.get(position).getUuid().toString());
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }
}
