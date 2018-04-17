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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiButton = findViewById(R.id.wifiBtn);
        GPSButton = findViewById(R.id.gps);
        sensorsButton = findViewById(R.id.sensors);
        accelerometer = findViewById(R.id.accelerometer);
        orientation = findViewById(R.id.orientation);
        gyroscope = findViewById(R.id.gyroscope);
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
    }
}
