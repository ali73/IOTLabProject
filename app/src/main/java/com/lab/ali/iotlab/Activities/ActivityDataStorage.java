package com.lab.ali.iotlab.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lab.ali.iotlab.Models.MyWifiInfo;
import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.Utils.CallBack;
import com.lab.ali.iotlab.Utils.MyWifiManager;
import com.lab.ali.iotlab.Utils.WIFIConnectionListener;
import com.lab.ali.iotlab.Utils.WifiScannerReceiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;

public class ActivityDataStorage extends AppCompatActivity {
    EditText input;
    TextView stored, externalStorageState;
    Button save;
    SharedPreferences sharedPreferences;
    WIFIConnectionListener wifiConnectionListener;
    WifiScannerReceiver wifiScannerReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_storage);
        input = findViewById(R.id.sharedPrefInupt);
        stored = findViewById(R.id.sharedPrefData);
        save = findViewById(R.id.save);
        externalStorageState = findViewById(R.id.externalStorage);


        wifiConnectionListener = new WIFIConnectionListener(this);
        wifiScannerReceiver = new WifiScannerReceiver();
        wifiScannerReceiver.setCallBack(new CallBack() {
            @Override
            public void callBack(@Nullable Intent intent) {
                List<ScanResult> scanResults = MyWifiManager.getInstance(getApplicationContext()).getWifiManager().getScanResults();
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

//   TODO: start for wifi scanning
//   TODO: read stored data in file
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
        File sdcard = getSDCard();
        if (sdcard != null) {
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("wifilist.txt", Context.MODE_PRIVATE));
                for (ScanResult scanResult : scanResults) {
                    MyWifiInfo wifiInfo = new MyWifiInfo();
                    wifiInfo.setDate(new Date());
                    wifiInfo.setSSID(scanResult.SSID);
                    wifiInfo.setRssi(scanResult.level);
                    wifiInfo.setLatitude(getLatitude());
                    wifiInfo.setLongtitude(getLongtitude());
                    outputStreamWriter.write(String.format("%s\n",wifiInfo.toString()));
                }
                    outputStreamWriter.close();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getSDCard() {
        File file = new File("/storage");
        File[] dirs = file.listFiles();

        for (File f : dirs) {
            if (Environment.isExternalStorageRemovable(f)) {
                return f;
            }
        }
        return null;
    }

    public double getLongtitude() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return location.getLongitude();

        }
        return Double.NaN;
    }
    public double getLatitude(){
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return location.getLatitude();

        }
        return Double.NaN;
    }
}
