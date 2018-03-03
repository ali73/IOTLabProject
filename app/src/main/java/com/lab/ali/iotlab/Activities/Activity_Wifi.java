package com.lab.ali.iotlab.Activities;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lab.ali.iotlab.Adapters.AdapterWifiList;
import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.Utils.LoadingDialog;
import com.lab.ali.iotlab.Utils.MyWIFIScanReciever;
import com.lab.ali.iotlab.Utils.MyWifiManager;
import com.lab.ali.iotlab.Utils.WIFIConnectionListener;

public class Activity_Wifi extends AppCompatActivity {

    int WIFI_PERMISION = 10;
    private MyWifiManager myWifiManager;
    MyWIFIScanReciever wifiScanReciever;
    WIFIConnectionListener wifiConnectionListener;
    RecyclerView recyclerView;
    private AdapterWifiList adapter;
    Button scan;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__wifi);
        scan = findViewById(R.id.wifiSearch);
        myWifiManager = MyWifiManager.getInstance(getApplicationContext());
        wifiScanReciever =new MyWIFIScanReciever(this);
        wifiConnectionListener = new WIFIConnectionListener(this);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerReceiver(
                        wifiScanReciever,
                        new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
                );
                if (!myWifiManager.getWifiManager().isWifiEnabled()){
                    myWifiManager.getWifiManager().setWifiEnabled(true);
                }
                if (ContextCompat.checkSelfPermission(Activity_Wifi.this, Manifest.permission.ACCESS_WIFI_STATE)== PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(Activity_Wifi.this, Manifest.permission.CHANGE_WIFI_STATE)== PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(Activity_Wifi.this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    Log.i("WIFIACTIVITY","Permission granted");
                    loadingDialog.show();
                    myWifiManager.getWifiManager().startScan();
                }
                else {
                    ActivityCompat.requestPermissions(Activity_Wifi.this,
                            new String[]{Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.CHANGE_WIFI_STATE,Manifest.permission.ACCESS_FINE_LOCATION},
                            WIFI_PERMISION);
                }
            }
        });
        adapter = new AdapterWifiList(this);
        recyclerView = findViewById(R.id.wifiList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        loadingDialog = new LoadingDialog(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(wifiScanReciever);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                myWifiManager.getWifiManager().startScan();
            }
        }
    }
    public AdapterWifiList getAdapter(){
        return adapter;
    }

    public LoadingDialog getLoadingDialog() {
        return loadingDialog;
    }

    public void setLoadingDialog(LoadingDialog loadingDialog) {
        this.loadingDialog = loadingDialog;
    }

    public MyWifiManager getMyWifiManager() {
        return myWifiManager;
    }
}
