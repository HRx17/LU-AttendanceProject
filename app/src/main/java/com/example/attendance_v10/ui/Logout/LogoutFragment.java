package com.example.attendance_v10.ui.Logout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.attendance_v10.MainActivity;
import com.example.attendance_v10.R;
import com.example.attendance_v10.dummy_splash;
import com.google.firebase.auth.FirebaseAuth;

public class LogoutFragment extends Fragment {


    public LogoutFragment() {
    }

    Button button;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_logout, container, false);
        context = getActivity();
        button = root.findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(context, dummy_splash.class);
                startActivity(intent);
            }
        });

        return root;
    }
}