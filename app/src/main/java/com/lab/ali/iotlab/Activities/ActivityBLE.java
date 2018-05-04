package com.lab.ali.iotlab.Activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lab.ali.iotlab.Adapters.BluetoothListAdapter;
import com.lab.ali.iotlab.R;

public class ActivityBLE extends AppCompatActivity implements BluetoothAdapter.LeScanCallback{
    BluetoothAdapter adapter ;
    BluetoothManager bManager;
    Button scanBtn;
    Handler scanHandler;
    RecyclerView recyclerView;
    BluetoothListAdapter rcAdapter;
    private static final int BLUETOOTH_ENABLE_RC = 671;
    private final int SCAN_DURATION = 10000;

    private void initBluetooth(){
        if (adapter!=null && !adapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,BLUETOOTH_ENABLE_RC);
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);
        scanBtn = findViewById(R.id.scan);
        recyclerView = findViewById(R.id.ble_list);
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
        {
            final AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
            alertDialog.setTitle("Warning");
            alertDialog.setMessage("No bluetooth is available on your device.");
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }

        bManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        adapter = bManager.getAdapter();
        initBluetooth();
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanBtn.setEnabled(false);
                adapter.startLeScan(ActivityBLE.this);
            }
        });
        scanHandler = new Handler();
        scanHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    adapter.stopLeScan(ActivityBLE.this);
            }
        },SCAN_DURATION);
        rcAdapter = new BluetoothListAdapter(getApplicationContext(),adapter);
        recyclerView.setAdapter(rcAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
        Log.d("BLE","Scan completed!");
        rcAdapter.addDevice(bluetoothDevice);
//        scanBtn.setEnabled(true);
    }
}
