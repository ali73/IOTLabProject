package com.lab.ali.iotlab.Activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lab.ali.iotlab.R;

public class ActivityAngle extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor mSensor;
    Button start , end;
    float angle,temp,bias;
    boolean set = false;
    double timeStamp;
    TextView textView ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angle);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);
        textView = findViewById(R.id.textview);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                angle    = temp;
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                angle = temp - angle;
                textView.setText(String.valueOf(angle));
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x , y , z;
        float dt = (float) ((sensorEvent.timestamp - timeStamp) / 1000000000);
        x = sensorEvent.values[0];
        y = sensorEvent.values[1];
        z = sensorEvent.values[2];
        timeStamp = sensorEvent.timestamp;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
