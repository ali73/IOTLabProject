package com.lab.ali.iotlab.Adapters;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.lab.ali.iotlab.Utils.MyBluetoothGattCallBack;
import com.lab.ali.iotlab.ViewHolders.BluetoothDeviceItem;

public class BLEListAdapter extends BluetoothListAdapter {
    private BluetoothGatt mBlueGatt;
    private MyBluetoothGattCallBack gattCallBack;
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
                Log.d("BLE","Connect");
            }
        });
    }

    public BluetoothGatt getmBlueGatt() {
        return mBlueGatt;
    }

    public void setmBlueGatt(BluetoothGatt mBlueGatt) {
        this.mBlueGatt = mBlueGatt;
    }

    public MyBluetoothGattCallBack getGattCallBack() {
        return gattCallBack;
    }
}
