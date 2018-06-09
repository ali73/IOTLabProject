package com.lab.ali.iotlab.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lab.ali.iotlab.R;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by ali on 3/3/18.
 */

public class GPS extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    TextView gpsLocation, networkLocation;
    Button showLocation;
    Location gpsLoc;
    Location netLoc;
    LocationListener networkLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)){
                if (netLoc == null){
                    netLoc = location;
                }
                else if (netLoc.getTime()<location.getTime()){
                    if (netLoc.getAccuracy()>location.getAccuracy()){
                        netLoc = location;
                        networkLocation.setText(String.valueOf(String.format(" NETWORK: Latitude:%f ,Longitude%f, Altitude:%f\nSpeed: %f",netLoc.getLatitude(),netLoc.getLongitude(),netLoc.getAltitude(),netLoc.getSpeed())));
                    }
                }
            }
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
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        gpsLocation = findViewById(R.id.GPSlocation);
        networkLocation = findViewById(R.id.networkLocation);
        showLocation = findViewById(R.id.showLocation);
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("You need to enable your gps in order to use this feature of application.");
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                    finish();
                }
            });
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.show();

        }

        showLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: get location and show on textviews
                if (ActivityCompat.checkSelfPermission(GPS.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GPS.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(GPS.this,Manifest.permission.INTERNET)!=PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(GPS.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.INTERNET,Manifest.permission.ACCESS_COARSE_LOCATION},10);
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                netLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                gpsLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (netLoc!=null)
                    networkLocation.setText(String.valueOf(String.format(" NETWORK: Latitude:%f ,Longitude%f, Altitude:%f",netLoc.getLatitude(),netLoc.getLongitude(),netLoc.getAltitude())));
                if (gpsLoc!=null)
                    gpsLocation.setText(String.valueOf(String.format(" GPS: Latitude:%f ,Longitude%f, Altitude:%f",gpsLoc.getLatitude(),gpsLoc.getLongitude(),gpsLoc.getAltitude())));
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,GPS.this);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,networkLocationListener);
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)){
            if (gpsLoc==null){
                gpsLoc = location;
            }
            else if (gpsLoc.getTime()<location.getTime()){
                if (gpsLoc.getAccuracy()>location.getAccuracy()){
                    gpsLoc = location;
                    gpsLocation.setText(String.valueOf(String.format(" GPS: Latitude:%f ,Longitude%f, Altitude:%f\nSpeed: %f"
                            ,gpsLoc.getLatitude(),gpsLoc.getLongitude(),gpsLoc.getAltitude(),gpsLoc.getSpeed())));
                }
            }
        }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==10 && grantResults[0]==PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED){
            netLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            gpsLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (netLoc!=null)
                networkLocation.setText(String.valueOf(String.format(" NETWORK: %f ,%f",netLoc.getAltitude(),netLoc.getLatitude())));
            if (gpsLoc!=null)
                gpsLocation.setText(String.valueOf(String.format("GPS: %f , %f",gpsLoc.getAltitude(),gpsLoc.getAltitude())));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,GPS.this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,GPS.this);
        }
    }
}
