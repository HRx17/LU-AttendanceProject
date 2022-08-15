package com.example.attendance_v10.ui.Tour;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.attendance_v10.R;
import com.example.attendance_v10.databinding.FragmentHomeBinding;
import com.example.attendance_v10.databinding.FragmentTourBinding;

public class TourFragment extends Fragment {

    private FragmentTourBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTourBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}