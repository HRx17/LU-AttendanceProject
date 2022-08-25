package com.example.attendance_v10.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.attendance_v10.MainActivity;
import com.example.attendance_v10.Models.Usermodels;
import com.example.attendance_v10.R;
import com.example.attendance_v10.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    FirebaseDatabase db;
    CircleImageView photo;
    //TextView name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        photo = root.findViewById(R.id.card_photo);
        db = FirebaseDatabase.getInstance();

        db.getReference().child("Admin").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        Usermodels usermodels = snapshot.getValue(Usermodels.class);
                        if (usermodels ==null) {

                        } else {

                            Glide.with(getActivity()).load(usermodels.getProfile_img()).into(photo);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


        return root;
    }
}