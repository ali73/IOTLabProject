package com.lab.ali.iotlab.Utils;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.telecom.Call;
import android.util.Log;

import com.lab.ali.iotlab.Activities.ActivityBLE;
import com.lab.ali.iotlab.Fragments.Fragment_BLE_Details;

import java.util.ArrayList;
import java.util.List;

import static android.bluetooth.BluetoothGatt.GATT_SUCCESS;

public class MyBluetoothGattCallBack extends BluetoothGattCallback{
    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        Log.d("GATTCALLBACK","Connection state changed");
        Log.d("GATTCALLBACK",String.valueOf(newState));
        if (newState==BluetoothGatt.STATE_CONNECTED && status ==GATT_SUCCESS){
            Log.d("GATT","Connected to device ");
            gatt.discoverServices();
        }
        else if (newState == BluetoothGatt.STATE_DISCONNECTED){
             Log.d("GATT","disconnected from device");
        }
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        Log.d("GATTCALLBACK","Service discovered");
//        ActivityBLE.serviceList.addAll(gatt.getServices());
//        Fragment_BLE_Details.notifyDatasetChanged();
        Fragment_BLE_Details.adapter.addAll(gatt.getServices());
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
    }


}
