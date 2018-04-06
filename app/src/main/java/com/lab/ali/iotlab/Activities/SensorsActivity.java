package com.lab.ali.iotlab.Activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.lab.ali.iotlab.Adapters.AdapterSensorInfoList;
import com.lab.ali.iotlab.R;

public class SensorsActivity extends AppCompatActivity {

    Button button;
    RecyclerView recyclerView;
    SensorManager sensorManager;
    AdapterSensorInfoList adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        button = findViewById(R.id.button1);
        recyclerView = findViewById(R.id.sensorsList);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        adapter = new AdapterSensorInfoList(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSensorsList();
            }
        });
    }



    private void getSensorsList(){
        adapter.clearDataset();
        adapter.addSensors(sensorManager.getSensorList(Sensor.TYPE_ALL));
    }
}
