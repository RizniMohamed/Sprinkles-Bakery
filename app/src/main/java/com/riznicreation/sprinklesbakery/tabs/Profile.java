package com.riznicreation.sprinklesbakery.tabs;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

        textName.setText(db.auth().getUser().getName());
        btnName.setOnClickListener(v -> textDialog("Name"));
        btnContact.setOnClickListener(v -> textDialog("Contact"));
        btnPassword.setOnClickListener(v -> textDialog("Password"));

        return view;
    }

    private void textDialog(String set){
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View model = factory.inflate(R.layout.text_model, null);

        EditText txt = model.findViewById(R.id.txt);
        switch (set) {
            case "Name":
                txt.setHint("Name");
                break;
            case "Contact":
                txt.setHint("Contact");
                break;
            case "Password":
                //TODO 2nd verification needed
                txt.setHint("Password");
                break;
        }

        Button btnApply = model.findViewById(R.id.btnApply);
        Button btnCancel = model.findViewById(R.id.btnCancel);

        btnApply.setOnClickListener(v1 -> {
            switch (set) {
                case "Name":
                    setNameInDB(txt.getText().toString());
                    break;
                case "Contact":
                    setContactInDB(txt.getText().toString());
                    break;
                case "Password":
                    setPasswordInDB(txt.getText().toString());
                    break;
            }
            dialog.dismiss();
        });
        btnCancel.setOnClickListener(v1 -> dialog.dismiss());

        dialog.setCancelable(false);
        dialog.setView(model);
        dialog.show();

    }

    private void setNameInDB(String name) {
        Message.info(getContext(),name);
    }

    private void setContactInDB(String name) {
        Message.info(getContext(),name);
    }

    private void setPasswordInDB(String name) {
        Message.info(getContext(),name);
    }

    private void initPic(View view) {

        Glide.with(view.getContext())
                .asBitmap()
                .centerCrop()
                .fitCenter()
                .placeholder(R.drawable.ic_photo)
                .load("https://images.pexels.com/photos/1308885/pexels-photo-1308885.jpeg?cs=srgb&dl=pexels-tu%E1%BA%A5n-ki%E1%BB%87t-jr-1308885.jpg&fm=jpg")
                .apply(RequestOptions.circleCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_photo)
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