package com.example.attendance_v10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendance_v10.Adaptor.AttendanceCardAdaptor;
import com.example.attendance_v10.Models.AttendanceCardModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.Value;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    TextView logout;
    ImageView qrCodeIV;
    Button generateQrBtn;
    Bitmap bitmap;

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

                SharedPreferences sharedPreferences = getSharedPreferences("token", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", "");
                editor.apply();
            }
        });
        qrCodeIV = findViewById(R.id.IVQrcode);
        qrCodeIV.setVisibility(View.GONE);
        generateQrBtn = findViewById(R.id.BtnGenerateQR);
        generateQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // String sText = edit.getText().toString().trim();
                UUID randomUUID = UUID.randomUUID();
                String s1 = randomUUID.toString().replaceAll("_", "");

                db.collection("Barcode").document("trWKQNNVi204Ei54T3Fw").update("Id",s1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AdminMain.this, "Ok", Toast.LENGTH_SHORT).show();
                    }
                });
                Date date = new Date();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String dt = formatter.format(date);

                Map<String, Object> userData = new HashMap<>();
                userData.put("Day", dt);
                userData.put("names", "{admin}");

                if(name.equals("20216")) {
                    db.collection("Mobile Computing").document("August").collection("Days")
                            .document(dt).set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(AdminMain.this, "OK", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    db.collection("Machine Learning").document("August").collection("Days")
                            .document(dt).set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(AdminMain.this, "OK", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                MultiFormatWriter writer = new MultiFormatWriter();
                try
                {
                    BitMatrix matrix = writer.encode(s1, BarcodeFormat.QR_CODE,350,350);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    bitmap = encoder.createBitmap(matrix);
                    qrCodeIV.setImageBitmap(bitmap);
                    qrCodeIV.setVisibility(View.VISIBLE);
                }
                catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}