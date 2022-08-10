package com.example.attendance_v10.ui.Attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.attendance_v10.R;
import com.example.attendance_v10.databinding.FragmentAttendanceBinding;
import com.example.attendance_v10.databinding.FragmentHomeBinding;

public class AttendanceFragment extends Fragment {

    private FragmentAttendanceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAttendanceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

}