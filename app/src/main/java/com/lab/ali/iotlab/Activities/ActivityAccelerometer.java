package com.lab.ali.iotlab.Activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lab.ali.iotlab.R;

public class ActivityAccelerometer extends AppCompatActivity implements SensorEventListener{
    TextView X,Y,Z;
    TextView gX,gY,gZ;
    double gx=0,gy=0,gz=0;
    double alpha = 0.8;
    private SensorManager sensorManager;
    private Sensor mSensor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        X = findViewById(R.id.Xaccelration);
        Y = findViewById(R.id.Yaccelration);
        Z = findViewById(R.id.Zaccelration);
        gX = findViewById(R.id.gravityX);
        gY = findViewById(R.id.gravityY);
        gZ = findViewById(R.id.gravityZ);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        gx = alpha * gx +(1-alpha)* sensorEvent.values[0];
        gy = alpha * gy +(1-alpha)* sensorEvent.values[1];
        gz = alpha * gz +(1-alpha)* sensorEvent.values[2];
        X.setText("X : "+String.valueOf(sensorEvent.values[0]-gx));
        Y.setText("Y : "+String.valueOf(sensorEvent.values[1]-gy));
        Z.setText("Z : "+String.valueOf(sensorEvent.values[2]-gz));
        gX.setText("X : "+String.valueOf(sensorEvent.values[0]));
        gY.setText("Y : "+String.valueOf(sensorEvent.values[1]));
        gZ.setText("Z : "+String.valueOf(sensorEvent.values[2]));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

}
