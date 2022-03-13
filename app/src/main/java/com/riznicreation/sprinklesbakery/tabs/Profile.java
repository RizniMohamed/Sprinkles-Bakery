package com.riznicreation.sprinklesbakery.tabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.riznicreation.sprinklesbakery.Login;
import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.helper.Message;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Profile extends Fragment {

    private TextView btnPassword,btnLogout,btnName,btnContact,btnAddress,textName;
    private DBHelper db;
    private ImageView btnPic;
    private ActivityResultLauncher<Intent> resultLauncher;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews(view);
        initPic(view);
        initName();

        btnPic.setOnClickListener(v -> chooseImage());

        btnLogout.setOnClickListener(v -> {
            if(db.auth().logout())
                startActivity(new Intent(getContext(), Login.class));
            else
                Message.error(view.getContext(), "Error on logout");
        });

        btnName.setOnClickListener(v -> textDialog("Name"));
        btnContact.setOnClickListener(v -> textDialog("Contact"));
        btnPassword.setOnClickListener(v -> textDialog("Password"));
        btnAddress.setOnClickListener(v -> textDialog("Address"));

        return view;
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLauncher.launch(intent);
    }

    private void imageActivityResultLauncher() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        assert data != null;
                        storePicInDB(data.getData());

                    }
                });
    }

    private void storePicInDB(Uri data) {
        InputStream iStream = null;
        try {
            iStream = requireContext().getContentResolver().openInputStream(data);
        } catch (FileNotFoundException e) {
            Message.info(getContext(),e.getLocalizedMessage());
        }
        assert iStream != null;
        byte[] inputData = getBytes(iStream);
        if(db.user().storeImage(inputData)){
            Message.success(getContext(),"Image updated successfully");
            initPic(requireView());
        }else{
            Message.error(getContext(),"Error on updating image");
        }
    }
    @NonNull
    private byte[] getBytes(@NonNull InputStream inputStream)  {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while (true) {
            try {
                if ((len = inputStream.read(buffer)) == -1) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void textDialog(@NonNull String set){
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View model = factory.inflate(R.layout.text_model, null);

        EditText txt = model.findViewById(R.id.txt);
        switch (set) {
            case "Name":
                txt.setHint("Name");
                break;
            case "Contact":
                txt.setInputType(InputType.TYPE_CLASS_NUMBER);
                txt.setHint("07XXXXXXXX");
                break;
            case "Password":
                txt.setHint("Password");
            case "Address":
                txt.setHint("Address");
                break;
        }

        Button btnApply = model.findViewById(R.id.btnApply);
        Button btnCancel = model.findViewById(R.id.btnCancel);

        btnApply.setOnClickListener(v1 -> {
            if(!txt.getText().toString().isEmpty()){
                switch (set) {
                    case "Name":
                        setNameInDB(txt.getText().toString());
                        initName();
                        break;
                    case "Contact":
                        setContactInDB(txt.getText().toString());
                        break;
                    case "Address":
                        setAddressInDB(txt.getText().toString());
                        break;
                    case "Password":
                        //TODO 2nd verification needed
                        setPasswordInDB(txt.getText().toString());
                        break;
                }
                dialog.dismiss();
            }else
                Message.error(getContext(),"Field cannot be empty");

        });
        btnCancel.setOnClickListener(v1 -> dialog.dismiss());

        dialog.setCancelable(false);
        dialog.setView(model);
        dialog.show();

    }

    private void setAddressInDB(String address) {
        if(db.user().setAddress(address)) {
            Message.success(getContext(), "Address updated successfully");
            return;
        }
        Message.error(getContext(),"Error on name updating");
    }

    private void setNameInDB(String name) {
        if(db.user().setName(name)) {
            Message.success(getContext(), "Name updated successfully");
            return;
        }
        Message.error(getContext(),"Error on name updating");
    }

    private void setContactInDB(String contact) {
        if(db.user().setContact(contact)) {
            Message.success(getContext(), "Name contact successfully");
            return;
        }
        Message.error(getContext(),"Error on contact updating");
    }

    private void setPasswordInDB(String password) {
        if(passwordCharValidation(password)){
            if(db.auth().setPassword(password))
                Message.success(getContext(),"password updated successfully");
            else
                Message.error(getContext(),"Error on password updating");
        }else
            Message.error(getContext(),"Password must contain at least 8 characters, 1 lower case, 1 upper case and 1 special character");

    }

    public boolean passwordCharValidation(@NonNull String password) {
        /*
            ^                 # start-of-string
            (?=.*[0-9])       # a digit must occur at least once
            (?=.*[a-z])       # a lower case letter must occur at least once
            (?=.*[A-Z])       # an upper case letter must occur at least once
            (?=.*[@#$%^&+=.]) # a special character must occur at least once
            (?=\S+$)          # no whitespace allowed in the entire string
            .{8,}             # anything, at least eight places though
            $                 # end-of-string
         */
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.])(?=\\S+$).{8,}$";
        return (password.matches(pattern));
    }

    private void initPic(@NonNull View view) {

        Glide.with(view.getContext())
                .asBitmap()
                .centerCrop()
                .fitCenter()
                .placeholder(R.mipmap.default_image)
                .load(db.user().getUser().getPicture())
                .apply(RequestOptions.circleCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.default_image)
                .into(btnPic);
    }

    private void initName(){
        textName.setText(db.user().getUser().getName());
    }

    private void initViews(@NonNull View view) {
        btnPassword = view.findViewById(R.id.btnPassword);
        btnPic = view.findViewById(R.id.btnPic);
        btnLogout = view.findViewById(R.id.btnLogout);
        textName = view.findViewById(R.id.textName);
        btnName = view.findViewById(R.id.btnName);
        btnContact = view.findViewById(R.id.btnContact);
        btnAddress = view.findViewById(R.id.btnAddress);
        db = new DBHelper(view.getContext());
        imageActivityResultLauncher();
    }
}