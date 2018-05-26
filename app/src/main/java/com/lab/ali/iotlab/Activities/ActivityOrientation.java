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

public class ActivityOrientation extends AppCompatActivity  implements SensorEventListener{
    TextView textView;
    private SensorManager sensorManager;
    private Sensor mSensor;
    enum Orientation{
        ONTHETABLE,
        DEFAULT,
        UPSIDEDOWN,
        RIGHT,
        LEFT,
        INVALID
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);
        textView = findViewById(R.id.serviceUUID);


        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        textView.setText(getOrientation(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]).name());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private Orientation getOrientation(float x, float y, float z){
        if ((int)z>0 && ((int) y ==0 ||(int) y == -1)&&( (int) x ==0 || (int)x ==-1))
            return Orientation.ONTHETABLE;
        else if ((int)y>0&&((int)x==0 || (int)x==-1) && ((int) z ==0 || (int)z==-1))
            return Orientation.DEFAULT;
        else if ((int) y <-5 && ((int)x==0 || (int)x==-1) && ((int) z ==0 || (int)z==-1))
            return Orientation.DEFAULT.UPSIDEDOWN;
        else if ((int) x > 0 && ((int) y ==0||(int)y==-1) && ((int) z ==0 || (int)z ==-1))
            return Orientation.LEFT;
        else if ((int) x <-5 && ((int) y ==0||(int)y==-1) && ((int) z ==0 || (int)z ==-1))
            return Orientation.RIGHT;
        else
            return Orientation.INVALID;
    }
}
