package com.riznicreation.sprinklesbakery.tabs;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.riznicreation.sprinklesbakery.Login;
import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.helper.Message;

public class Profile extends Fragment {

    private TextView btnPassword,btnLogout,btnName,btnContact,textName;
    private DBHelper db;
    private ImageView btnPic;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews(view);
        initPic(view);

        btnLogout.setOnClickListener(v -> {
            if(!db.auth().logout())
                Message.error(view.getContext(), "Error on logout");
            else
                startActivity(new Intent(getContext(), Login.class));
        });

        btnName.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            final EditText edittext = new EditText(view.getContext());


            builder.setCancelable(false);

            LayoutInflater factory = LayoutInflater.from(getContext());
            final View deleteDialogView = factory.inflate(R.layout.fragment_profile, null);

            builder.setView(deleteDialogView);

            builder.setPositiveButton("Save", (dialog, whichButton) -> Toast.makeText(getContext(), edittext.getText().toString(), Toast.LENGTH_SHORT).show());

            builder.setNegativeButton("Cancel", (dialog, whichButton) -> dialog.dismiss());

            builder.show();

        });

        return view;
    }

    private void initPic(View view) {

        Glide.with(view.getContext())
                .asBitmap()
                .centerCrop()
                .fitCenter()
                .load("https://images.pexels.com/photos/1308885/pexels-photo-1308885.jpeg?cs=srgb&dl=pexels-tu%E1%BA%A5n-ki%E1%BB%87t-jr-1308885.jpg&fm=jpg")
                .apply(RequestOptions.circleCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(btnPic);
    }

    private void initViews(View view) {
        btnPassword = view.findViewById(R.id.btnPassword);
        btnPic = view.findViewById(R.id.btnPic);
        btnLogout = view.findViewById(R.id.btnLogout);
        textName = view.findViewById(R.id.textName);
        btnName = view.findViewById(R.id.btnName);
        btnContact = view.findViewById(R.id.btnContact);
        db = new DBHelper(view.getContext());
    }
}