package com.lab.ali.iotlab.Adapters;

import android.content.Context;
import android.hardware.Sensor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.ViewHolders.SensorInfoItem;

import java.util.ArrayList;
import java.util.List;

public class AdapterSensorInfoList extends RecyclerView.Adapter<SensorInfoItem>{
    Context context;
    private List<Sensor> _data;

    public AdapterSensorInfoList(Context context){
        this.context = context;
        this._data = new ArrayList<>();
    }
    @Override
    public SensorInfoItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new SensorInfoItem(LayoutInflater.from(context).inflate(R.layout.rc_item_sensor_info,parent,false));
    }

    @Override
    public void onBindViewHolder(SensorInfoItem holder, int position) {
        holder.getName().setText(_data.get(position).getName());
        holder.getVendor().setText(_data.get(position).getVendor());
        holder.getVersion().setText(String.valueOf(_data.get(position).getVersion()));
        holder.getMaxRange().setText(String.valueOf(_data.get(position).getMaximumRange()));
        holder.getMinDelay().setText(String.valueOf(_data.get(position).getMinDelay()));
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }

    public void clearDataset(){
        _data.clear();
        notifyDataSetChanged();
    }

    public void addSensors(List<Sensor> list){
        this._data.addAll(list);
        notifyDataSetChanged();
    }
}
