package com.lab.ali.iotlab.Utils;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import static android.bluetooth.BluetoothGatt.GATT_SUCCESS;

public class MyBluetoothGattCallBack extends BluetoothGattCallback{
    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        Log.d("GATTCALLBACK","Connection state changed");
        Log.d("GATTCALLBACK",String.valueOf(newState));
        if (newState==BluetoothGatt.STATE_CONNECTED && status ==GATT_SUCCESS){
            Log.d("GATT","Connected to device ");
        }
        else if (newState == BluetoothGatt.STATE_DISCONNECTED){
             Log.d("GATT","disconnected from device");
        }
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);

    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
    }
}
