package com.example.attendance_v10.ui.Attendance;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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
import android.widget.ImageView;
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
import com.example.attendance_v10.Registeration;
import com.example.attendance_v10.Retrofit.RetrofitClient;
import com.example.attendance_v10.databinding.FragmentAttendanceBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
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
    FirebaseStorage firestore;

    String name,link;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView IVCapture;
    int i=0;
    Bitmap bitmap;
    Uri profileuri;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;

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
        firestore = FirebaseStorage.getInstance();

        pg.setVisibility(View.GONE);
        txt1.setVisibility(View.GONE);
        txt2.setVisibility(View.GONE);

        Calendar now = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dt = formatter.format(date);
        dat.setText(dt);

        IVCapture = root.findViewById(R.id.IVCapture);

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

                        }
                    }, 100);
                    SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("attended", 0);
                    if (sharedPreferences1 != null) {
                        if (sharedPreferences1.getString("attended", "").equals("done")) {
                            Toast.makeText(getContext(), "Attendance Done!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                                } else {
                                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                                }
                            }
                        }
                    } else {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("attended", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("attended", "1");
                        editor.apply();
                        if (sharedPreferences1.getString("attended", "").equals("done")) {
                            Toast.makeText(getContext(), "Attendance Done!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                                } else {
                                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                                }
                            }
                        }
                    }
                }
                else{
                    Toast.makeText(getContext(), "Sorry You Missed the Lecture Hours!", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences("attended", 0);
                    SharedPreferences.Editor editor1 = sharedPreferences2.edit();
                    editor1.putString("attended", "");
                    editor1.apply();
                }
                }
            });

        return root;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            IVCapture.setImageBitmap(photo);
            profileuri = data.getData();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataa = baos.toByteArray();

            final StorageReference reference = firestore.getReference().child("profile_img")
                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));
            reference.putBytes(dataa).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Uploaded!", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            firebaseDatabase.getReference().child("Admin").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                    .child("click_img").setValue(uri.toString());
                            i=1;
                            firebaseDatabase.getReference().child("Admin").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            Usermodels usermodels = snapshot.getValue(Usermodels.class);
                                            if (usermodels ==null) {

                                            } else {

                                                link = usermodels.getClick_img();
                                                String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
                                                String urlEncoded = Uri.encode(link, ALLOWED_URI_CHARS);
                                                link = urlEncoded;
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });
                            firebaseDatabase.getReference().child("Admin").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            Usermodels usermodels = snapshot.getValue(Usermodels.class);
                                            if (usermodels ==null) {

                                            }
                                            else {
                                                name = usermodels.getName();
                                                String[] names = name.split(" ");
                                                name = names[0].toUpperCase();

                                                if(link != null){
                                                    Call<FaceResponse> studCall = RetrofitClient.getInstance().getApi().faceResponse(link,name);
                                                    studCall.enqueue(new Callback<FaceResponse>() {
                                                        @Override
                                                        public void onResponse(Call<FaceResponse> call, Response<FaceResponse> response) {
                                                            if(response.isSuccessful()) {
                                                                if (response.body().getResponse().equals("match")) {
                                                                    // No need for success message
                                                                    Toast.makeText(getContext(), "Verified!", Toast.LENGTH_SHORT).show();
                                                                    pg.setVisibility(View.GONE);
                                                                    Intent intent = new Intent(getContext(), QRcode.class);
                                                                    intent.putExtra("subject", Subject);
                                                                    startActivity(intent);
                                                                    getActivity().finish();
                                                                } else {
                                                                    Toast.makeText(getContext(), response.body().getResponse(), Toast.LENGTH_SHORT).show();
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
                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });

                        }
                    });
                }
            });
        }
    }

}