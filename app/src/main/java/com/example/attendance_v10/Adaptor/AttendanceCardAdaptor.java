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


public class AttendanceCardAdaptor extends RecyclerView.Adapter<AttendanceCardAdaptor.ViewHolder> {

    private Context context;
    private final List<AttendanceCardModel> attendanceCardModelList;

    public AttendanceCardAdaptor(Context context, List<AttendanceCardModel> attendanceCardModelList) {
        this.context = context;
        this.attendanceCardModelList = attendanceCardModelList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.day_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(attendanceCardModelList.get(position).getDay());
        int n = attendanceCardModelList.get(position).getNames().size();
        holder.attend.setText(String.valueOf(n));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("day",attendanceCardModelList.get(position).getDay());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return attendanceCardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,attend,all;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.card_name);
            attend = itemView.findViewById(R.id.card_a);
            all = itemView.findViewById(R.id.card_all);

        }
    }
}
