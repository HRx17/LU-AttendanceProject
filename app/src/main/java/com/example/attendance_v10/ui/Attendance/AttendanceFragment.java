package com.example.attendance_v10.ui.Attendance;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.attendance_v10.ModelResponse.FaceRecResponse;
import com.example.attendance_v10.QRcode;
import com.example.attendance_v10.R;
import com.example.attendance_v10.Retrofit.RetrofitClient;
import com.example.attendance_v10.databinding.FragmentAttendanceBinding;
import com.google.firebase.database.FirebaseDatabase;
import com.google.type.DateTime;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class AttendanceFragment extends Fragment {

    private FragmentAttendanceBinding binding;
    ArrayAdapter<String> arrayAdapter;
    private String Subject;
    TextView dat,prof,txt1,txt2;
    Button attend;
    ProgressBar pg;
    Handler mHandler;
    AutoCompleteTextView autoCompleteTextView;
    FirebaseDatabase firebaseDatabase;

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
        firebaseDatabase = FirebaseDatabase.getInstance();

        pg.setVisibility(View.GONE);
        txt1.setVisibility(View.GONE);
        txt2.setVisibility(View.GONE);

        Calendar now = Calendar.getInstance();
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


                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("attended",0);
                String attended = sharedPreferences.getString("attended","null");


                if(attended.equals("done")){
                 Toast.makeText(getContext(), "Attendance Done!", Toast.LENGTH_SHORT).show();
                }
                else if (9 <= now.get(Calendar.HOUR_OF_DAY) && 12 >= now.get(Calendar.HOUR_OF_DAY)) {
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

                            /*
                            String recFace = null;
                            String name = null;

                            Call<FaceRecResponse> call = RetrofitClient.getInstance().getApi().faceResponse(recFace);
                            call.enqueue(new Callback<FaceRecResponse>() {
                                @Override
                                public void onResponse(Call<FaceRecResponse> call, Response<FaceRecResponse> response) {
                                    FaceRecResponse faceRecResponse = response.body();
                                    if(response.isSuccessful()){
                                        Toast.makeText(getContext(), faceRecResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        String tm = faceRecResponse.getMessage();
                                        Intent intent = new Intent(getContext(), QRcode.class);
                                        startActivity(intent);
                                        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("name", 0);
                                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                                        editor1.putString("name", name);
                                        editor1.apply();
                                        //sharedPrefManager.SaveToken(loginResponse.getToken());
                                    }
                                }

                                @Override
                                public void onFailure(Call<FaceRecResponse> call, Throwable t) {
                                    Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                                }
                            });*/
                            Call<FaceRecResponse> call = RetrofitClient.getInstance().getApi().faceResponse("");
                            call.enqueue(new Callback<FaceRecResponse>() {
                                @Override
                                public void onResponse(Call<FaceRecResponse> call, @NonNull Response<FaceRecResponse> response) {
                                    if(response.isSuccessful()){
                                        FaceRecResponse validate1 = response.body();
                                        assert validate1 != null;
                                        if(validate1.getMessage().equals("hi")){
                                            // No need for success message
                                            //Toast.makeText(Signup.this, "Verified!", Toast.LENGTH_SHORT).show();
                                           pg.setVisibility(View.GONE);
                                            Intent intent = new Intent(getContext(), QRcode.class);
                                            intent.putExtra("subject",Subject);
                                            startActivity(intent);
                                            getActivity().finish();
                                        }
                                        else{
                                            Toast.makeText(getContext(), validate1.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else {
                                        String errorMsg = "Server not reachable, please try after sometime!";
                                        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<FaceRecResponse> call, Throwable t) {
                                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }, 100);
                }
                else{
                    Toast.makeText(getContext(), "Sorry You Missed the Lecture Hours!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

}