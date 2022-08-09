package com.example.attendance_v10.ui.Tour;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.attendance_v10.R;

public class TourFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tour,container,false);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}