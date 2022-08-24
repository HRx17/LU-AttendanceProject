package com.example.attendance_v10.ui.Attendance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.example.attendance_v10.MainActivity;
import com.example.attendance_v10.ModelResponse.FaceResponse;
import com.example.attendance_v10.Models.Usermodels;
import com.example.attendance_v10.QRcode;
import com.example.attendance_v10.R;
import com.example.attendance_v10.Retrofit.RetrofitClient;
import com.example.attendance_v10.databinding.FragmentAttendanceBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    String name,link;

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

                //SharedPreferences sharedPreferences = getActivity().getSharedPreferences("attended",0);
                //String attended = sharedPreferences.getString("attended","null");


                //if(attended.equals("done")){
                 //Toast.makeText(getContext(), "Attendance Done!", Toast.LENGTH_SHORT).show();
                //}
                if (9 <= now.get(Calendar.HOUR_OF_DAY) && 12 >= now.get(Calendar.HOUR_OF_DAY)) {
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


                            firebaseDatabase.getReference().child("Admin").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            Usermodels usermodels = snapshot.getValue(Usermodels.class);
                                            if (usermodels ==null) {

                                            } else {
                                                link = usermodels.getProfile_img();
                                                name = usermodels.getName();
                                                String[] names = name.split(" ");
                                                name = names[0].toUpperCase();
                                                //Toast.makeText(getContext(), name+""+link, Toast.LENGTH_SHORT).show();
                                                Toast.makeText(getContext(), link, Toast.LENGTH_SHORT).show();

                                                Call<FaceResponse> studCall = RetrofitClient.getInstance().getApi().faceResponse(link,name);
                                                studCall.enqueue(new Callback<FaceResponse>() {
                                                    @Override
                                                    public void onResponse(Call<FaceResponse> call, Response<FaceResponse> response) {
                                                        if(response.isSuccessful()) {
                                                            if (response.body().getMessage().equals("match")) {
                                                                // No need for success message
                                                                Toast.makeText(getContext(), "Verified!", Toast.LENGTH_SHORT).show();
                                                                pg.setVisibility(View.GONE);
                                                                Intent intent = new Intent(getContext(), QRcode.class);
                                                                intent.putExtra("subject", Subject);
                                                                startActivity(intent);
                                                                getActivity().finish();
                                                            } else {
                                                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                        else{
                                                            String errorMsg = "Server not reachable, please try after sometime!";
                                                            //Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                                                            Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<FaceResponse> call, Throwable t) {
                                                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                                        System.out.print(t.getMessage());
                                                    }
                                                });

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

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