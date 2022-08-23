package com.example.attendance_v10;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRcode extends AppCompatActivity {
    Button btnScn;
    String subject;
    String code;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_qrcode);
        btnScn = findViewById(R.id.btnScn);
        Intent intent = getIntent();
        db = FirebaseFirestore.getInstance();
        db.collection("Barcode").document("trWKQNNVi204Ei54T3Fw").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                code = String.valueOf(documentSnapshot.get("Id"));
            }
        });
        subject = intent.getStringExtra("subject");

        btnScn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        QRcode.this
                );
                intentIntegrator.setPrompt("For flash use voulme up button");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(CaptureAct.class);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        if(intentResult.getContents() != null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    QRcode.this
            );
            builder.setTitle("Result");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    if(code.equals(intentResult.getContents())){
                        Intent intent = new Intent(QRcode.this,AttendanceGranted.class);
                        intent.putExtra("subject",subject);
                        startActivity(intent);
                        finish();
                    }
                }
            });
            builder.show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Did'nt scan anything ",Toast.LENGTH_SHORT)
                    .show();
        }
    }
}