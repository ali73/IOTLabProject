package com.lab.ali.iotlab.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lab.ali.iotlab.R;

public class MainActivity extends AppCompatActivity {

    Button wifiButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiButton = findViewById(R.id.wifiBtn);


        wifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Activity_Wifi.class);
                startActivity(intent);
            }
        });
    }
}
