package com.lab.ali.iotlab.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.ViewHolders.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<Student>{
    List<com.lab.ali.iotlab.Models.Student> _data;
    Context context;

    public StudentListAdapter(Context context ){
        this. context = context;
        this._data = new ArrayList<>();
    }
    @NonNull
    @Override
    public Student onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Student(LayoutInflater.from(context).inflate(R.layout.rc_item_student,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Student holder, int position) {
        holder.getStudentName().setText(_data.get(position).getName());
        holder.getStudentName().setEnabled(false);
        holder.getStudentNumber().setText(_data.get(position).getStudentNumber());
        holder.getStudentNumber().setEnabled(false);
        holder.getEdit().setOnClickListener(new View.OnClickListener() {
            boolean isEditing =false;
            @Override
            public void onClick(View v) {
                isEditing = ! isEditing;
                if (isEditing){
                    holder.getStudentName().setEnabled(true);
                    holder.getStudentNumber().setEnabled(true);
                    holder.getEdit().setText("SAVE");
                }
                else {
                    holder.getEdit().setText("EDIT");
//                    TODO: save edited item
                }
            }
        });
        holder.getDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: Delete item from list
            }
        });
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }

    public void addItem(com.lab.ali.iotlab.Models.Student student){
        this._data.add(student);
        notifyDataSetChanged();
    }
}
