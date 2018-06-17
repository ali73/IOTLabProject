package com.lab.ali.iotlab.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lab.ali.iotlab.R;

public class Student extends RecyclerView.ViewHolder {
    private EditText studentName;
    private EditText studentNumber;
    private Button edit;
    private Button delete
            ;
    public Student(View itemView) {
        super(itemView);
        studentName = itemView.findViewById(R.id.studentName);
        studentName = itemView.findViewById(R.id.studentNumber);
        edit = itemView.findViewById(R.id.edit);
        delete = itemView.findViewById(R.id.delete);
    }

    public EditText getStudentName() {
        return studentName;
    }

    public EditText getStudentNumber() {
        return studentNumber;
    }

    public Button getEdit() {
        return edit;
    }

    public Button getDelete() {
        return delete;
    }
}
