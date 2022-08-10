package com.example.attendance_v10.ui.Attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.attendance_v10.R;
import com.example.attendance_v10.databinding.FragmentAttendanceBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AttendanceFragment extends Fragment {

    private FragmentAttendanceBinding binding;
    ArrayAdapter<String> arrayAdapter;
    private String Subject;
    TextView dat;
    AutoCompleteTextView autoCompleteTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAttendanceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dat = root.findViewById(R.id.date);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dt = formatter.format(date);
        dat.setText(dt);

        autoCompleteTextView = root.findViewById(R.id.auto_txt);
        String[] languages = getResources().getStringArray(R.array.subjects);

        arrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.dropdown_item,languages);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Subject = adapterView.getItemAtPosition(i).toString();
            }
        });

        return root;
    }

}