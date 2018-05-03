package com.lab.ali.iotlab.Utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class BluetoothServer extends Thread {
    private BluetoothServerSocket mSocket;

    public BluetoothServer(BluetoothAdapter adapter){
        BluetoothServerSocket temp = null;
        UUID uuid = UUID.fromString("56e8a14a-80b3-11e5-8bcf-feff819cdc9f");
        try {
            temp = adapter.listenUsingRfcommWithServiceRecord("Myname",uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mSocket = temp;
    }
    public void run(){
        BluetoothSocket socket = null;
        while (true){
            try {
                socket = mSocket.accept();
                if (socket!=null){
                    initDataTransfer(socket);
                    mSocket.close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

        }
    }

    public void initDataTransfer(BluetoothSocket socket){
        Log.d("BLUTOOTH","SERVER found some client!");
    }
    public void cancel(){
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
