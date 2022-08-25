package com.example.attendance_v10.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance_v10.Models.AttendanceCardModel;
import com.example.attendance_v10.R;
import com.example.attendance_v10.ViewAllActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class StudentsAdaptor extends RecyclerView.Adapter<StudentsAdaptor.ViewHolder> {

    private Context context;
    private final List<AttendanceCardModel> attendanceCardModelList;

    public StudentsAdaptor(Context context, List<AttendanceCardModel> attendanceCardModelList) {
        this.context = context;
        this.attendanceCardModelList = attendanceCardModelList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.student_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText(attendanceCardModelList.get(position).getNames().indexOf(position));

    }

    @Override
    public int getItemCount() {
        return attendanceCardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.attended_name);

        }
    }
}
