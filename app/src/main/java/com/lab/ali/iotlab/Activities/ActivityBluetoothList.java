package com.lab.ali.iotlab.Activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.lab.ali.iotlab.Adapters.BluetoothListAdapter;
import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.Utils.LoadingDialog;

public class ActivityBluetoothList extends AppCompatActivity{

    RecyclerView recyclerView;
    BluetoothListAdapter adapter;
    BluetoothAdapter bADapter;
    BroadcastReceiver scanReceiver;
    Button scan;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_list);
        adapter = new BluetoothListAdapter(getApplicationContext());
        recyclerView = findViewById(R.id.device_list);
        scan = findViewById(R.id.scan);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        loadingDialog = new LoadingDialog(this);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();
                bADapter.startDiscovery();
                loadingDialog.show();
            }
        });
        scanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())){
                    BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    adapter.addDevice(bluetoothDevice);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(scanReceiver,intentFilter);

        bADapter = BluetoothAdapter.getDefaultAdapter() ;
        final Handler handler  = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!bADapter.isDiscovering())
                    loadingDialog.dismiss();
                handler.postDelayed(this,1000);
            }
        },1000);
    }
}
