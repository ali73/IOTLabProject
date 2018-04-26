package com.lab.ali.iotlab.Activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lab.ali.iotlab.R;

public class ActivityBluetooth extends AppCompatActivity {

    TextView bluetoothStatus;
    BluetoothAdapter mAdapter;
    Handler stateChecker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        bluetoothStatus = findViewById(R.id.bluetooth);
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mAdapter == null)
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
        stateChecker = new Handler();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.BLUETOOTH},400);
        }
        bluetoothStatus.setText("Bluetooth status");
        startStatusUpdate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==400){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                startStatusUpdate();
            }
        }
    }
    public void startStatusUpdate(){
        bluetoothStatus.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mAdapter.getState()==BluetoothAdapter.STATE_OFF){
                    bluetoothStatus.setText("OFF");
                }
                else if (mAdapter.getState()==BluetoothAdapter.STATE_TURNING_ON){
                    bluetoothStatus.setText("Turning On");
                }
                else if (mAdapter.getState()==BluetoothAdapter.STATE_ON){
                    bluetoothStatus.setText("ON");
                }
                else if (mAdapter.getState()==BluetoothAdapter.STATE_TURNING_OFF){
                    bluetoothStatus.setText("Turning off");
                }
                bluetoothStatus.postDelayed(this,500);
            }
        },500);
    }
}
