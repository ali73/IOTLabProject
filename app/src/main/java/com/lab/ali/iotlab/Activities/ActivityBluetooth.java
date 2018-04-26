package com.lab.ali.iotlab.Activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lab.ali.iotlab.R;

public class ActivityBluetooth extends AppCompatActivity {

    TextView bluetoothStatus;
    BluetoothAdapter mAdapter;
    Handler stateChecker;
    Button bSwitch;
    View.OnClickListener bSwitchOnclk;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        bluetoothStatus = findViewById(R.id.bluetooth);
        bSwitch = findViewById(R.id.bswitch);
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        initBswitchOnClick();
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
        bluetoothStatus.setText("Bluetooth status");
        startStatusUpdate();
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

    public void initBswitchOnClick(){
        if (mAdapter.isEnabled()){
            bSwitch.setText("Turn off");
        }
        else
            bSwitch.setText("Turn on");
        bSwitchOnclk = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mAdapter.isEnabled()){
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent,340);
                }
                else {
                    mAdapter.disable();
                    bSwitch.setText("Turn on");
                }
            }
        };
        bSwitch.setOnClickListener(bSwitchOnclk);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==340)
            if (resultCode == RESULT_OK){
            bSwitch.setText("Turn off");
            }
    }
}

