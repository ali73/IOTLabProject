package com.lab.ali.iotlab.Adapters;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.Utils.BluetoothClient;
import com.lab.ali.iotlab.ViewHolders.BluetoothDeviceItem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothListAdapter extends RecyclerView.Adapter<BluetoothDeviceItem>{
    List<BluetoothDevice> _data;
    Context context;
    BluetoothAdapter adapter;
    public BluetoothListAdapter(Context context,BluetoothAdapter adapter){
        this.context=  context;
        this._data = new ArrayList<>();
        this.adapter = adapter;
    }
    @Override
    public BluetoothDeviceItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BluetoothDeviceItem(LayoutInflater.from(context).inflate(R.layout.rc_item_bluetooth_device,parent,false));
    }

    @Override
    public void onBindViewHolder(BluetoothDeviceItem holder, final int position) {
        holder.setMac(_data.get(position).getAddress());
        holder.setName(_data.get(position).getName());
        holder.getLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<BluetoothDevice> paired = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
                if (!paired.contains(_data.get(position))){
                    try {
                        Method method = _data.get(position).getClass().getMethod("createBond", (Class[]) null);
                        method.invoke(_data.get(position), (Object[]) null);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
//                Set<BluetoothDevice> paired = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
//                if (paired.contains(_data.get(position))){
//                    BluetoothClient bluetoothClient = new BluetoothClient(_data.get(position),adapter);
//                    bluetoothClient.start();
//                }
//                else {
//                    final AlertDialog dialog = new AlertDialog.Builder(context).create();
//                    dialog.setMessage("You must pair two devices!");
//                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//                }

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
