package com.lab.ali.iotlab.Adapters;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.ViewHolders.GATTServiceItem;

import java.util.ArrayList;
import java.util.List;

public class BLEServicesListAdapter extends RecyclerView.Adapter<GATTServiceItem>{
    public static List<BluetoothGattService> _data;
    Context context;

    public BLEServicesListAdapter(Context context){
        this.context = context;
        _data = new ArrayList<>();
    }
    public void add(BluetoothGattService service){
        _data.add(service);
        BluetoothGattCharacteristic characteristic ;
    }
    public void addAll(List<BluetoothGattService> list){
        this._data .addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public GATTServiceItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GATTServiceItem(LayoutInflater.from(context).inflate(R.layout.rc_item_gatt_service,parent,false));
    }

    @Override
    public void onBindViewHolder(GATTServiceItem holder, int position) {
        holder.getTextView().setText(_data.get(position).getUuid().toString());
        if (_data.get(position).getType()==BluetoothGattService.SERVICE_TYPE_PRIMARY)
            holder.getType().setText("PRIMARY");
        else
            holder.getType().setText("SECONDARY");
        int size = _data.get(position).getCharacteristics().size();
        holder.getCharacteristics().setText(_data.get(position).getCharacteristics().get(size-1).toString());
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }
}
