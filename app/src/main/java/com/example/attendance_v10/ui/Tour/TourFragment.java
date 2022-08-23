package com.example.attendance_v10.ui.Tour;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.attendance_v10.BookTour;
import com.example.attendance_v10.R;
import com.example.attendance_v10.databinding.FragmentHomeBinding;
import com.example.attendance_v10.databinding.FragmentTourBinding;

public class TourFragment extends Fragment {

    private FragmentTourBinding binding;
    Button button;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTourBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        button = root.findViewById(R.id.book_tour_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BookTour.class);
                startActivity(intent);

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}