package com.example.attendance_v10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendance_v10.ui.Tour.TourFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yoti.mobile.android.commons.util.L;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.security.auth.Subject;

public class BookTour extends AppCompatActivity {

    String people,attend,residence;

    ArrayAdapter<String> arrayAdapter1;
    ArrayAdapter<String> arrayAdapter2;
    ArrayAdapter<String> arrayAdapter3;
    Button book,cancel;
    EditText firstname,lastname,email,number,editText;
    FirebaseFirestore db;

    final Calendar myCalendar= Calendar.getInstance();

    AutoCompleteTextView autoCompleteTextView1,autoCompleteTextView2,autoCompleteTextView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_book_tour);

        db = FirebaseFirestore.getInstance();
        autoCompleteTextView1 = findViewById(R.id.people);
        autoCompleteTextView2 = findViewById(R.id.appoint);
        autoCompleteTextView3 = findViewById(R.id.residence);

        book = findViewById(R.id.book_tour);
        cancel = findViewById(R.id.cancel_tour);
        firstname = findViewById(R.id.tour_firstname);
        email = findViewById(R.id.tour_email);
        number = findViewById(R.id.tour_number);
        lastname = findViewById(R.id.tour_lastname);
        editText= findViewById(R.id.tour_date);

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(BookTour.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        String[] peoples = getResources().getStringArray(R.array.people);

        arrayAdapter1 = new ArrayAdapter<String>(this,R.layout.dropdown_item,peoples);
        autoCompleteTextView1.setAdapter(arrayAdapter1);
        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                people = adapterView.getItemAtPosition(i).toString();
            }
        });

        String[] yesno = getResources().getStringArray(R.array.yesno);

        arrayAdapter2 = new ArrayAdapter<String>(this,R.layout.dropdown_item,yesno);
        autoCompleteTextView2.setAdapter(arrayAdapter2);
        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                attend = adapterView.getItemAtPosition(i).toString();
            }
        });

        arrayAdapter3 = new ArrayAdapter<String>(this,R.layout.dropdown_item,yesno);
        autoCompleteTextView3.setAdapter(arrayAdapter3);
        autoCompleteTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                residence = adapterView.getItemAtPosition(i).toString();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookTour.this, TourFragment.class);
                startActivity(intent);
                finish();
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstname.getText().toString().isEmpty() || lastname.getText().toString().isEmpty() || number.getText().toString().isEmpty() || email.getText().toString().isEmpty() || editText.getText().toString().isEmpty() || people.isEmpty() || attend.isEmpty() || residence.isEmpty() ){
                    Toast.makeText(BookTour.this, "Please Fill All Details!", Toast.LENGTH_SHORT).show();
                }
                else{
                    String Firstname,Lastname,Email,Number;
                    Firstname = firstname.getText().toString();
                    Lastname = lastname.getText().toString();
                    Email = email.getText().toString();
                    Number = number.getText().toString();

                    Map<String, Object> userData = new HashMap<>();
                    userData.put("Name", Firstname+" "+Lastname);
                    userData.put("Email",Email);
                    userData.put("Phone Number",Number);
                    userData.put("People",people);
                    userData.put("Faculty",attend);
                    userData.put("Residence",residence);

                    db.collection("Tour").document(Firstname).set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent intent = new Intent(BookTour.this,Booked.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });
    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(dateFormat.format(myCalendar.getTime()));
    }

}