package com.lab.ali.iotlab.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lab.ali.iotlab.R;

public class MainActivity extends AppCompatActivity {

    Button wifiButton;
    Button GPSButton;
    Button sensorsButton;
    Button accelerometer;
    Button orientation;
    Button gyroscope;
    Button angleDetector;
    Button magnetometer;
    Button bluetooth;
    Button ble;
    Button wifi_p2p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsFromID();
        setButtonOnClicks();
    }

    public void findViewsFromID(){
        wifiButton = findViewById(R.id.wifiBtn);
        GPSButton = findViewById(R.id.gps);
        sensorsButton = findViewById(R.id.sensors);
        accelerometer = findViewById(R.id.accelerometer);
        orientation = findViewById(R.id.orientation);
        gyroscope = findViewById(R.id.gyroscope);
        magnetometer = findViewById(R.id.magnetometer);
        angleDetector = findViewById(R.id.angle);
        bluetooth  = findViewById(R.id.bluetooth);
        ble = findViewById(R.id.ble);
        wifi_p2p = findViewById(R.id.wifip2p);
    }

    public void setButtonOnClicks(){
        wifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Activity_Wifi.class);
                startActivity(intent);
            }

        });
        GPSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,GPS.class);
                startActivity(intent);
            }
        });

        sensorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SensorsActivity.class);
                startActivity(intent);
            }
        });

        accelerometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ActivityAccelerometer.class);
                startActivity(intent);
            }
        });
        orientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ActivityOrientation.class);
                startActivity(intent);
            }
        });
        gyroscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ActivityGyroscope.class);
                startActivity(intent);
            }
        });
        magnetometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ActivityMagnetometer.class);
                startActivity(intent);
            }
        });
        angleDetector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ActivityAngle.class);
                startActivity(intent);
            }
        });
        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent =  new Intent(MainActivity.this,ActivityBluetooth.class);
            startActivity(intent);
            }
        });
        ble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ActivityBLE.class);
                startActivity(intent);
            }
        });
        wifi_p2p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Activity_wifiP2P.class);
                startActivity(intent);
            }
        });
    }
}
