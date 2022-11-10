package com.aseproject.frigg.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aseproject.frigg.R;
import com.aseproject.frigg.activity.FamilyMemberActivity;


public class MoreFragment extends Fragment {
    private TextView tvSetPreference;
    private TextView tvAddFamilyMembers;
    private TextView tvSettings;
    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvSetPreference = view.findViewById(R.id.tvSetPreference);
        tvAddFamilyMembers = view.findViewById(R.id.tvAddFamilyMembers);
        tvSettings = view.findViewById(R.id.tvSettings);

        setOnClickListeners();
    }

    private void setOnClickListeners() {
        tvSetPreference.setOnClickListener(view -> {

        });

        tvSettings.setOnClickListener(view ->  {

        });

        tvAddFamilyMembers.setOnClickListener(view -> {
            Intent intent = new Intent(context, FamilyMemberActivity.class);
            startActivity(intent);

        });
    }
}