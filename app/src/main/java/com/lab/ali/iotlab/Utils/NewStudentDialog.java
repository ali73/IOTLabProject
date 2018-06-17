package com.lab.ali.iotlab.Utils;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lab.ali.iotlab.Activities.ActivitySql;
import com.lab.ali.iotlab.R;

public class NewStudentDialog  {
    ActivitySql activity;
    Dialog dialog;
    Button save,cancel;
    EditText name, number;

    public NewStudentDialog(ActivitySql activity){
        this.activity = activity;
        this.dialog = new Dialog(activity);
        this.dialog.setContentView(R.layout.sql_new_row_dialog);
        save = dialog.findViewById(R.id.saveStudent);
        cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        name = dialog.findViewById(R.id.studentName);
        number = dialog.findViewById(R.id.studentNumber);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stName = name.getText().toString();
                String stNumber = number.getText().toString();
//                TODO: save
            }
        });
    }
    public void dismiss(){
        dialog.dismiss();
    }
    public void show(){
        dialog.show();
    }
}
