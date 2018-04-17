package com.lab.ali.iotlab.Activities;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lab.ali.iotlab.R;

public class ActivityGyroscope  extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private TextView axisX,axisY,axisZ;
    private double rotx = 0.0, rotY = 0.0 , rotZ = 0.0, timestamp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);

        axisX = findViewById(R.id.axisX);
        axisY = findViewById(R.id.axisY);
        axisZ = findViewById(R.id.axisZ);

        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this,gyroscopeSensor,SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//        rotx = (sensorEvent.timestamp-timestamp )/1000000000 * sensorEvent.values[0];
//        rotY = (sensorEvent.timestamp-timestamp )/1000000000 * sensorEvent.values[1];
//        rotZ = (sensorEvent.timestamp-timestamp )/1000000000 * sensorEvent.values[2];
        timestamp = sensorEvent.timestamp;
        rotx = sensorEvent.values[0];
        rotY = sensorEvent.values[1];
        rotZ = sensorEvent.values[2];
        axisX.setText("X : "+ String.valueOf(rotx));
        axisY.setText("Y : "+ String.valueOf(rotY));
        axisZ.setText("Z : "+ String.valueOf(rotZ));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
