package com.lab.ali.iotlab.Adapters;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.view.View;

import com.lab.ali.iotlab.Utils.MyBluetoothGattCallBack;
import com.lab.ali.iotlab.ViewHolders.BluetoothDeviceItem;

public class BLEListAdapter extends BluetoothListAdapter {
    BluetoothGatt mBlueGatt;
    MyBluetoothGattCallBack gattCallBack;
    public BLEListAdapter(Context context, BluetoothAdapter adapter) {
        super(context, adapter);
        gattCallBack = new MyBluetoothGattCallBack();
    }

    @Override
    public void onBindViewHolder(BluetoothDeviceItem holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.getLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBlueGatt = _data.get(position).connectGatt(context,true,gattCallBack);

            }
        });
    }
}
