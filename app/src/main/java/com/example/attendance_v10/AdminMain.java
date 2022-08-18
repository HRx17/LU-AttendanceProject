package com.example.attendance_v10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendance_v10.Adaptor.AttendanceCardAdaptor;
import com.example.attendance_v10.Models.AttendanceCardModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminMain extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    TextView profname;
    RecyclerView rec;
    List<AttendanceCardModel> attendanceCardModel;
    AttendanceCardAdaptor attendanceCardAdaptor;
    FirebaseFirestore db;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_main);

        SharedPreferences sharedPreferences = getSharedPreferences("token",0);
        String name = sharedPreferences.getString("token",null);
        profname = findViewById(R.id.prof_name);
        logout = findViewById(R.id.admin_out);
        rec = findViewById(R.id.rec_month);
        db = FirebaseFirestore.getInstance();

        rec.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        attendanceCardModel = new ArrayList<>();
        attendanceCardAdaptor = new AttendanceCardAdaptor(this,attendanceCardModel);
        rec.setAdapter(attendanceCardAdaptor);

        if(name.equals("20216")){
            profname.setText("Dr. Grewal");
            db.collection("Mobile Computing").document("August").collection("Days")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    AttendanceCardModel popularModel = document.toObject(AttendanceCardModel.class);
                                    attendanceCardModel.add(popularModel);
                                    attendanceCardAdaptor.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(AdminMain.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else{
            profname.setText("Dr. Passi");
            db.collection("Machine Learning").document("August").collection("Days")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    AttendanceCardModel popularModel = document.toObject(AttendanceCardModel.class);
                                    attendanceCardModel.add(popularModel);
                                    attendanceCardAdaptor.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(AdminMain.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AdminMain.this, dummy_splash.class);
                startActivity(intent);
                finish();
            }
        });

    }
}