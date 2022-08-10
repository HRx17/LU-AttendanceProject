package com.example.attendance_v10.ui.Attendance;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
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
    TextView dat,prof,txt1,txt2;
    Button attend;
    ProgressBar pg;
    Handler mHandler;
    AutoCompleteTextView autoCompleteTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAttendanceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mHandler = new Handler();
        attend = root.findViewById(R.id.attendance);
        txt1 = root.findViewById(R.id.attendtxt);
        txt2 = root.findViewById(R.id.attendtxtt);
        pg = root.findViewById(R.id.attendance_pg);
        dat = root.findViewById(R.id.date);
        prof = root.findViewById(R.id.professor_name);

        pg.setVisibility(View.GONE);
        txt1.setVisibility(View.GONE);
        txt2.setVisibility(View.GONE);

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
                if(Subject.equals("Mobile Computing")){
                    prof.setText("-Dr. Grewal");
                }
                else if(Subject.equals("Machine Learning")){
                    prof.setText("-Dr. Passi");
                }
            }
        });


        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Animation animFadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fadefast);
                        animFadeIn.reset();
                        pg.setVisibility(View.VISIBLE);
                        pg.clearAnimation();
                        pg.startAnimation(animFadeIn);
                        txt1.setVisibility(View.VISIBLE);
                        txt1.clearAnimation();
                        txt1.startAnimation(animFadeIn);
                        txt2.setVisibility(View.VISIBLE);
                        txt2.clearAnimation();
                        txt2.startAnimation(animFadeIn);
                    }
                },100);
            }
        });

        return root;
    }

}