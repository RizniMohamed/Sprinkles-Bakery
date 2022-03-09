package com.riznicreation.sprinklesbakery;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.helper.Message;
import com.riznicreation.sprinklesbakery.rvadapter.ManageCategoryRVAdaptor;

public class Manage extends AppCompatActivity {

    private RecyclerView rv_manage_category;
    private ImageButton btnBack, btnAdd;
    private DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        initViews();
        initCategories();

        btnBack.setOnClickListener( v -> onBackPressed());

        btnAdd.setOnClickListener( v -> textDialog());

    }

    private void textDialog(){
        AlertDialog dialog = new AlertDialog.Builder(this).create();

        LayoutInflater factory = LayoutInflater.from(this);
        final View model = factory.inflate(R.layout.text_model, null);

        EditText txt = model.findViewById(R.id.txt);
        EditText txtNum = model.findViewById(R.id.txtNum);

        txt.setHint("Category");
        txtNum.setHint("Discount");

        txtNum.setVisibility(View.VISIBLE);
        txtNum.setText("0");

        Button btnApply = model.findViewById(R.id.btnApply);
        Button btnCancel = model.findViewById(R.id.btnCancel);

        btnApply.setOnClickListener(v1 -> {
            if(!txt.getText().toString().isEmpty() && !txtNum.getText().toString().isEmpty()){

                int status = DB.category().insertCategory(txt.getText().toString(), Integer.parseInt(txtNum.getText().toString()));
                if(status == 1){
                    Message.success(this,"New category added successfully");
                    initCategories();
                }else if (status == 2){
                    Message.error(this,"Category already exits");
                } else{
                    Message.error(this,"Error on creating new category");
                }

                dialog.dismiss();
            }else{
                if(txt.getText().toString().isEmpty()){
                     Message.error(this,"Category name cannot be empty");
                }
                if(txtNum.getText().toString().isEmpty()){
                    Message.error(this,"Discount cannot be empty");
                }

            }

        });
        btnCancel.setOnClickListener(v1 -> dialog.dismiss());

        dialog.setCancelable(false);
        dialog.setView(model);
        dialog.show();

    }

    private void initCategories() {
        ManageCategoryRVAdaptor mcrvAdapter = new ManageCategoryRVAdaptor(this);
        mcrvAdapter.setCategories(new DBHelper(this).category().getAll());

        //set default category list as linear
        rv_manage_category.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rv_manage_category.setAdapter(mcrvAdapter);
    }

    private void initViews() {
        rv_manage_category = findViewById(R.id.rv_manage_category);
        btnBack = findViewById(R.id.btnBack);
        btnAdd = findViewById(R.id.btnAdd);
        DB = new DBHelper(this);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,Home.class));
        finish();
    }
}