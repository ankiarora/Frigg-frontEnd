package com.aseproject.frigg.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aseproject.frigg.R;
import com.aseproject.frigg.common.AppSessionManager;

public class ProfileFragment extends Fragment {
    private TextView name;
    private TextView email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);

        prepareView();
    }

    private void prepareView() {
        name.setText(AppSessionManager.getInstance().getName());
        email.setText(AppSessionManager.getInstance().getEmail());
    }
}