package com.lab.ali.iotlab.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lab.ali.iotlab.R;

public class ActivityBluetoothChat extends AppCompatActivity {
    EditText outbox;
    TextView inbox;
    Button send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        outbox = findViewById(R.id.outbox);
        inbox = findViewById(R.id.inbox);
        send = findViewById(R.id.send);


    }
}
