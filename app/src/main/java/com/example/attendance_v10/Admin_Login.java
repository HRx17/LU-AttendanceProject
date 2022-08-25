package com.example.attendance_v10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class Admin_Login extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Admin_Login.this,dummy_splash.class);
        startActivity(intent);
        finish();
    }

    EditText user,pass,token;
    Button login;
    FirebaseAuth auth;
    public String tokn = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_login);

        auth = FirebaseAuth.getInstance();
        user = findViewById(R.id.adminuser);
        pass = findViewById(R.id.adminPassword);
        token = findViewById(R.id.token);
        login = findViewById(R.id.adminlogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputuser = user.getText().toString();
                String inputpass = pass.getText().toString();
                tokn = token.getText().toString();
                if (inputuser.isEmpty() || inputpass.isEmpty()|| tokn.isEmpty()) {
                    Toast.makeText(Admin_Login.this, "Please Enter All The Details!", Toast.LENGTH_SHORT).show();
                }
                else if(tokn.equals("20216")){

                    auth.signInWithEmailAndPassword(inputuser,inputpass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        SharedPreferences sharedPreferences = getSharedPreferences("attended", 0);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("attended", "1");
                                        editor.apply();
                                        Toast.makeText(Admin_Login.this,"Log-In Successfull!",Toast.LENGTH_SHORT).show();;
                                        Intent logMain = new Intent(Admin_Login.this, AdminMain.class);
                                        startActivity(logMain);
                                        finish();
                                        SharedPreferences sharedPreferences1 = getSharedPreferences("token", 0);
                                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                                        editor1.putString("token", token.getText().toString());
                                        editor1.apply();
                                    }
                                    else{
                                        Toast.makeText(Admin_Login.this,"Error:"+task.isSuccessful(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(Admin_Login.this, "Invalid Token!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}