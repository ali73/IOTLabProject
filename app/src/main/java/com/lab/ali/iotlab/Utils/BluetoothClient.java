package com.lab.ali.iotlab.Utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class BluetoothClient extends Thread {

    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    BluetoothAdapter adapter;
    public BluetoothClient(BluetoothDevice device,BluetoothAdapter adapter){
        this.adapter = adapter;
        BluetoothSocket socket = null;
        mmDevice =device;
        try {
            socket = device.createRfcommSocketToServiceRecord(UUID.randomUUID());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mmSocket = socket;
    }

    public void  run(){
        this.adapter.cancelDiscovery();
        try {
            mmSocket.connect();
        } catch (IOException e) {
            try {
                mmSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        transferData(mmSocket);
    }
    public void transferData(BluetoothSocket socket){
        Log.d("BLUETOOTH","Connected");
    }
}
