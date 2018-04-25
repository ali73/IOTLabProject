package com.lab.ali.iotlab.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.MutableInt;
import android.widget.TextView;

import com.lab.ali.iotlab.R;

public class ActivityMagnetometer extends AppCompatActivity implements SensorEventListener, LocationListener {
    private SensorManager mSensorManager;
    private Sensor magnetometer;
    private TextView axisX, axisY, axisZ, magHeading,north;
    double heading;
    GeomagneticField geomagneticField;
    LocationManager locationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetometer);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        axisX = findViewById(R.id.axisX);
        axisY = findViewById(R.id.axisY);
        axisZ = findViewById(R.id.axisZ);
        magHeading = findViewById(R.id.heading);
        north = findViewById(R.id.north);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},300);
            }
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 10, this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        axisX.setText(String.valueOf(sensorEvent.values[0]));
        axisY.setText(String.valueOf(sensorEvent.values[1]));
        axisZ.setText(String.valueOf(sensorEvent.values[2]));
        if (sensorEvent.values[1]> 0 ){
            heading = 90 - Math.atan2(sensorEvent.values[1],sensorEvent.values[0])*180/Math.PI;
        }
        else if (sensorEvent.values[1]<0){
            heading = 270 - Math.atan2(sensorEvent.values[1],sensorEvent.values[0])*180/Math.PI;
        }
        else if (sensorEvent.values[0]<0){
            heading = 180.0;
        }
        else if (sensorEvent.values[0]>0){
            heading = 0.0;
        }
//        heading = Math.atan2(sensorEvent.values[1], sensorEvent.values[0]) * (180 / Math.PI);
        magHeading.setText("Heading :\t" + String.valueOf(heading));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        geomagneticField = new GeomagneticField(Double.valueOf(location.getLatitude()).floatValue(),
                Double.valueOf(location.getLongitude()).floatValue(),Double.valueOf(location.getAltitude()).floatValue(),System.currentTimeMillis());
        north.setText(String.valueOf(-geomagneticField.getDeclination()+heading));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
