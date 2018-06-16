package com.lab.ali.iotlab.Activities;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lab.ali.iotlab.Adapters.StoredWifiListAdapter;
import com.lab.ali.iotlab.Models.MyWifiInfo;
import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.Utils.CallBack;
import com.lab.ali.iotlab.Utils.LoadingDialog;
import com.lab.ali.iotlab.Utils.MyWifiManager;
import com.lab.ali.iotlab.Utils.WIFIConnectionListener;
import com.lab.ali.iotlab.Utils.WifiScannerReceiver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityDataStorage extends AppCompatActivity implements LocationListener{
    EditText input;
    TextView stored, externalStorageState;
    Button save;
    SharedPreferences sharedPreferences;
    WIFIConnectionListener wifiConnectionListener;
    WifiScannerReceiver wifiScannerReceiver;
    MyWifiManager wifiManager;
    RecyclerView recyclerView;
    Location currentLocation;
    LocationManager locationManager;
    StoredWifiListAdapter adapter;
    boolean writing = false;
    List<ScanResult> scanResults;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_storage);
        input = findViewById(R.id.sharedPrefInupt);
        stored = findViewById(R.id.sharedPrefData);
        save = findViewById(R.id.save);
        externalStorageState = findViewById(R.id.externalStorage);
        recyclerView = findViewById(R.id.wifilist);
        wifiManager = MyWifiManager.getInstance(getApplicationContext());
        wifiConnectionListener = new WIFIConnectionListener(this);
        if (ActivityCompat.checkSelfPermission(ActivityDataStorage.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(ActivityDataStorage.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ActivityDataStorage.this,Manifest.permission.INTERNET)!=PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(ActivityDataStorage.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.INTERNET,Manifest.permission.ACCESS_COARSE_LOCATION},10);
        if  (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED||
                ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},10);
        wifiScannerReceiver = new WifiScannerReceiver();
        wifiScannerReceiver.setCallBack(new CallBack() {
            @Override
            public void callBack(@Nullable Intent intent) {
                if (currentLocation!=null && !writing)
                    writeWifiListToFile(scanResults);
            }
        });
        sharedPreferences = getSharedPreferences("IOTLAB", 0);
        getSharedPrefData();
        stored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSharedPrefData();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIntoSharedPref();
            }
        });
        if (isExternalStorageWritable()) {
            long megAvailable = getTotalExternalMemorySize() / (1024 * 1024 * 1024);
            externalStorageState.setText(String.valueOf(megAvailable));
        } else
            externalStorageState.setText("No external device");

        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        adapter = new StoredWifiListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

//   TODO: start for wifi scanning
//   TODO: read stored data in file
        wifiManager.getWifiList();
        readFromFile();
        registerReceiver(wifiScannerReceiver,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }



    public void readFromFile()  {
        File sdcard = getSDCard();
        List<MyWifiInfo> infoList = new ArrayList<>();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sdcard.getAbsolutePath() + "/wifilist.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
            MyWifiInfo myWifiInfo = gson.fromJson(line,MyWifiInfo.class);
            adapter.add(myWifiInfo);
            while (line!=null){
                line = bufferedReader.readLine();
                myWifiInfo = gson.fromJson(line,MyWifiInfo.class);
                adapter.add(myWifiInfo);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getSharedPrefData() {
        String string = sharedPreferences.getString("MyData", null);
        if (string != null) {
            stored.setText(string);
        } else
            stored.setError("No data stored");
    }

    public void saveIntoSharedPref() {
        sharedPreferences.edit().putString("MyData", this.input.getText().toString()).commit();
        getSharedPrefData();
    }


    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static long getTotalExternalMemorySize() {
        File file = new File("/storage");
        File external = null;
        File[] dirs = file.listFiles();

        for (File f : dirs) {
            if (Environment.isExternalStorageRemovable(f)) {
                external = f;
                break;
            }
        }

        StatFs statFs = new StatFs(external.getPath());
        long blockSize = statFs.getBlockSize();
        long totalSize = statFs.getBlockCount() * blockSize;
        long availableSize = statFs.getAvailableBlocks() * blockSize;
        long freeSize = statFs.getFreeBlocks() * blockSize;
        return totalSize;
    }

    public void writeWifiListToFile(List<ScanResult> scanResults) {
        writing = true;
        File sdcard = getSDCard();
        if  (ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
            if (sdcard != null) {
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(sdcard.getAbsolutePath() + "/wifilist.txt"));
                    for (int i = 0 ; i<5; i++) {
                        MyWifiInfo wifiInfo = new MyWifiInfo();
//                        wifiInfo.setDate(new Date());
                        wifiInfo.setDate(new Date());
//                        wifiInfo.setSSID(scanResult.SSID);
                        wifiInfo.setSSID("Some SSID");
//                        wifiInfo.setRssi(scanResult.level);
                        wifiInfo.setLatitude(currentLocation.getLatitude());
//                        wifiInfo.setLatitude(getLatitude());
                        wifiInfo.setLongtitude(currentLocation.getLongitude());
                        outputStreamWriter.write(String.format("%s\n", wifiInfo.toString()));
                    }
                    outputStreamWriter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
        }
    }

    public File getSDCard() {
//                File file = new File("/storage");
//        File[] dirs = file.listFiles();
//
//        for (File f : dirs) {
//            if (Environment.isExternalStorageRemovable(f)) {
//                return f;
//            }
//        }
        return Environment.getExternalStorageDirectory();
    }



    @Override
    public void onLocationChanged(Location location) {
        this.currentLocation = location;
        locationManager.removeUpdates(this);
        if ( !writing)
            writeWifiListToFile(scanResults);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
