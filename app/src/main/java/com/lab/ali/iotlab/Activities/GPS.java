package com.lab.ali.iotlab.Activities;

import android.app.Service;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lab.ali.iotlab.R;

/**
 * Created by ali on 3/3/18.
 */

public class GPS extends AppCompatActivity {
    LocationManager locationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        locationManager = (LocationManager)getSystemService(Service.LOCATION_SERVICE);

    }
}
