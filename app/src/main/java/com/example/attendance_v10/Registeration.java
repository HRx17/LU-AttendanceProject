package com.example.attendance_v10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendance_v10.Models.Usermodels;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registeration extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Registeration.this,dummy_splash.class);
        startActivity(intent);
        finish();
    }

    private Handler mHandler = new Handler();
    FirebaseAuth auth;
    FirebaseDatabase database;
    Uri profileUri;
    FirebaseStorage firestore;
    ProgressBar pg;
    EditText email,name,phone,address,password;
    CircleImageView img;
    Button register;
    TextView jumptologin;
    int tmp = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registeration);

        firestore = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        img = findViewById(R.id.profile_img);
        email = findViewById(R.id.studentemail);
        name = findViewById(R.id.studentname);
        phone = findViewById(R.id.studentnumber);
        address = findViewById(R.id.student_address);
        password = findViewById(R.id.studentPassword);
        register = findViewById(R.id.studentregister);
        jumptologin = findViewById(R.id.tologin);
        pg = findViewById(R.id.prog1);
        pg.setVisibility(View.GONE);

        jumptologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registeration.this,StudentLogin.class);
                startActivity(intent);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty() || name.getText().toString().isEmpty() || phone.getText().toString().isEmpty() || address.getText().toString().isEmpty() ||password.getText().toString().isEmpty()){
                    Toast.makeText(Registeration.this, "Please Enter All Details !", Toast.LENGTH_SHORT).show();
                }
                else if(tmp!=0){
                    Toast.makeText(Registeration.this, "Please Add Profile Picture", Toast.LENGTH_SHORT).show();
                }
                else{
                    signupUser();
                    pg.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        if (data.getData() != null) {
            profileUri = data.getData();
            img.setImageURI(profileUri);
            tmp=0;
        }
    }

    private void signupUser() {
        String newUserp = name.getText().toString();
        String newemail = email.getText().toString();
        String newpass = password.getText().toString();
        String phonen = phone.getText().toString();
        String addresss = address.getText().toString();

        auth.createUserWithEmailAndPassword(newemail, newpass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Usermodels userModel = new Usermodels(newUserp, newemail, newpass,phonen,addresss);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Admin").child(id).setValue(userModel);

                            final StorageReference reference = firestore.getReference().child("profile_picture")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));
                            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(Registeration.this, "Uploaded!", Toast.LENGTH_SHORT).show();
                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            database.getReference().child("Admin").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                                    .child("profile_img").setValue(uri.toString());
                                            Toast.makeText(Registeration.this, "Profile Picture Updated!", Toast.LENGTH_SHORT).show();
                                            pg.setVisibility(View.GONE);
                                            Toast.makeText(Registeration.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                            Intent backtostart = new Intent(Registeration.this, MainActivity.class);
                                            startActivity(backtostart);
                                        }
                                    });
                                }
                            });

                        } else {
                            pg.setVisibility(View.GONE);
                            Toast.makeText(Registeration.this, "Error:" + task.getException(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}