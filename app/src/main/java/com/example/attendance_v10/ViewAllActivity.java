package com.example.attendance_v10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.attendance_v10.Adaptor.AttendanceCardAdaptor;
import com.example.attendance_v10.Adaptor.StudentsAdaptor;
import com.example.attendance_v10.Models.AttendanceCardModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    RecyclerView rec;
    List<AttendanceCardModel> attendanceCardModel;
    StudentsAdaptor studentsAdaptor;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_all);

        rec = findViewById(R.id.rec_students);

        db = FirebaseFirestore.getInstance();
        rec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        attendanceCardModel = new ArrayList<>();
        studentsAdaptor = new StudentsAdaptor(this,attendanceCardModel);
        rec.setAdapter(studentsAdaptor);

        db.collection("Mobile Computing").document("August").collection("Days")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AttendanceCardModel popularModel = document.toObject(AttendanceCardModel.class);
                                attendanceCardModel.add(popularModel);
                                studentsAdaptor.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(ViewAllActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}