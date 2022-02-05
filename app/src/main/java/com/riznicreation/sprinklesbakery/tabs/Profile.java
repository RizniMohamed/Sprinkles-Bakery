package com.riznicreation.sprinklesbakery.tabs;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.riznicreation.sprinklesbakery.Login;
import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.helper.Message;

public class Profile extends Fragment {

    private TextView btnChangePassword,btnChangeProfilePic,btnLogout;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews(view);
        btnLogout.setOnClickListener(v -> {
            DBHelper db = new DBHelper(view.getContext());
            if(!db.auth().logout()) Message.error(view.getContext(),"Error on logout");
            else startActivity(new Intent(getContext(), Login.class));
        });

        return view;
    }

    private void initViews(View view) {
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        btnChangeProfilePic = view.findViewById(R.id.btnChangeProfilePic);
        btnLogout = view.findViewById(R.id.btnLogout);
    }
}