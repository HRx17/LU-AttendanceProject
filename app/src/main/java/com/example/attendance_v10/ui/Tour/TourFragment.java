package com.example.attendance_v10.ui.Tour;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.attendance_v10.BookTour;
import com.example.attendance_v10.R;
import com.example.attendance_v10.databinding.FragmentHomeBinding;
import com.example.attendance_v10.databinding.FragmentTourBinding;

public class TourFragment extends Fragment {

    private FragmentTourBinding binding;
    Button button,map;
    TextView link;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTourBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        button = root.findViewById(R.id.book_tour_button);
        link = root.findViewById(R.id.virtual_link);
        map = root.findViewById(R.id.download_map);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://virtualtour.laurentian.ca/laurentian/main-campus-downtown-satellite/?utm_source=LU_en_tour&utm_medium=website&utm_campaign=LU_en_tour");
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(uri);
                startActivity(intent);

            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://laurentian.ca/assets/files/Liaison/Campus-Maps.pdf");
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(uri);
                startActivity(intent);
            }
        });

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