package com.riznicreation.sprinklesbakery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.rvadapter.ManageCategoryRVAdaptor;

public class Manage extends AppCompatActivity {

    private RecyclerView rv_manage_category;
    private ImageButton btnBack, btnAdd;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        
        initViews();
        initCategories();
        
        btnBack.setOnClickListener( v -> onBackPressed());
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
    }
}