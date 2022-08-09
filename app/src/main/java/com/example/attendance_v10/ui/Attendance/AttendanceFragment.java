package com.example.attendance_v10.ui.Attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.attendance_v10.R;

public class AttendanceFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_attendance,container,false);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}