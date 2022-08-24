package com.example.attendance_v10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class StudentLogin extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(StudentLogin.this,dummy_splash.class);
        startActivity(intent);
        finish();
    }

    TextView toregister;
    EditText eusern,epass;
    Button elogin;
    FirebaseAuth auth;
    ProgressBar pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_student_login);

        toregister = findViewById(R.id.tosignup);
        pg = findViewById(R.id.prog2);
        pg.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();

        eusern = findViewById(R.id.userIdL);
        epass = findViewById(R.id.userPassword);
        elogin = findViewById(R.id.studentlogIn);

        elogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
                pg.setVisibility(View.VISIBLE);

            }
        });
        toregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentLogin.this,Registeration.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginUser() {
        String inputuser = eusern.getText().toString();
        String inputpass = epass.getText().toString();
        if (inputuser.isEmpty() || inputpass.isEmpty()) {
            Toast.makeText(StudentLogin.this, "Please Enter All The Details!", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(inputuser, inputpass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(StudentLogin.this, "Log-In Successfull!", Toast.LENGTH_SHORT).show();
                                ;
                                pg.setVisibility(View.GONE);
                                Intent logMain = new Intent(StudentLogin.this, MainActivity.class);
                                startActivity(logMain);
                                finish();
                            } else {
                                pg.setVisibility(View.GONE);
                                Toast.makeText(StudentLogin.this, "Error:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

}