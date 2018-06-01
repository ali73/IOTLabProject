package com.lab.ali.iotlab.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lab.ali.iotlab.R;

import java.io.File;

public class ActivityDataStorage extends AppCompatActivity {
    EditText input;
    TextView stored,externalStorageState;
    Button save;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_storage);
        input = findViewById(R.id.sharedPrefInupt);
        stored = findViewById(R.id.sharedPrefData);
        save = findViewById(R.id.save);
        externalStorageState = findViewById(R.id.externalStorage);

        sharedPreferences = getSharedPreferences("IOTLAB",0);
        getSharedPrefData();
        stored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSharedPrefData();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIntoSharedPref();
            }
        });
        if (isExternalStorageWritable()){
            long megAvailable = getTotalExternalMemorySize() / (1024 * 1024*1024);
            externalStorageState.setText(String .valueOf(megAvailable));
        }
        else
            externalStorageState.setText("No external device");
    }


    public void getSharedPrefData(){
        String string = sharedPreferences.getString("MyData",null);
        if (string!=null){
            stored.setText(string);
        }
        else
            stored.setError("No data stored");
    }
    public void saveIntoSharedPref(){
        sharedPreferences.edit().putString("MyData",this.input.getText().toString()).commit();
        getSharedPrefData();
    }


    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static long getTotalExternalMemorySize() {
        File file = new File("/storage");
        File external = null;
        File [] dirs = file.listFiles();

        for (File f:dirs){
            if (Environment.isExternalStorageRemovable(f)){
                external = f;
                break;
            }
        }

        StatFs statFs = new StatFs(external.getPath());
        long blockSize = statFs.getBlockSize();
        long totalSize = statFs.getBlockCount()*blockSize;
        long availableSize = statFs.getAvailableBlocks()*blockSize;
        long freeSize = statFs.getFreeBlocks()*blockSize;
        return totalSize;
    }

}
