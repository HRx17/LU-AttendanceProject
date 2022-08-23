package com.example.attendance_v10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.attendance_v10.Models.AttendanceCardModel;
import com.example.attendance_v10.Models.Usermodels;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AttendanceGranted extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AttendanceGranted.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    FirebaseDatabase database;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_attendance_granted);

        //SharedPreferences sharedPreferences = getSharedPreferences("attended",0);
        //sharedPreferences.edit().putString("done","");

        database = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();

        database.getReference().child("Admin").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        Usermodels usermodels = snapshot.getValue(Usermodels.class);
                        if (usermodels ==null) {

                        } else {
                            String name = String.valueOf(usermodels.getName());
                            Date date = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                            String dt = formatter.format(date);
                            Intent intent = getIntent();
                            String subject = intent.getStringExtra("subject");
                            DocumentReference rr = db.collection(subject).document("August").collection("Days").document(dt);
                            if(rr == null) {
                                Map<String, Object> userData = new HashMap<>();
                                userData.put("Day", dt);
                                userData.put("names", "{}");

                                db.collection(subject).document("August").collection("Days").document(dt).set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        DocumentReference Ref = db.collection(subject).document("August").collection("Days").document(dt);
                                        Ref.update("Day", dt);
                                        Ref.update("names", FieldValue.arrayUnion(name)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(AttendanceGranted.this, "OK.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }
                            else{
                            DocumentReference Ref = db.collection(subject).document("August").collection("Days").document(dt);
                            Ref.update("Day",dt);
                            Ref.update("names", FieldValue.arrayUnion(name)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(AttendanceGranted.this, "OK.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


    }
}