package com.lab.ali.iotlab.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.lab.ali.iotlab.Adapters.StudentListAdapter;
import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.Utils.NewStudentDialog;

public class ActivitySql extends AppCompatActivity{

    RecyclerView recyclerView ;
    Button button;
    StudentListAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);

        recyclerView = findViewById(R.id.fields);
        button = findViewById(R.id.create);
        adapter = new StudentListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        TODO: get items from database

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewStudentDialog dialog = new NewStudentDialog(ActivitySql.this);
                dialog.show();
            }
        });

    }
}
