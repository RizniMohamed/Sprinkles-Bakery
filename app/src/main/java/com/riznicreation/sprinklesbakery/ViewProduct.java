package com.riznicreation.sprinklesbakery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.riznicreation.sprinklesbakery.entity.Product;
import com.riznicreation.sprinklesbakery.helper.Message;
import com.riznicreation.sprinklesbakery.rvadapter.ProductRVAdaptor;

import java.util.ArrayList;

public class ViewProduct extends AppCompatActivity {

    private RecyclerView rvProductList;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        initView();
        initProducts();

        //Direct to home page
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this,Home.class);
            startActivity(intent);
            finish();
        });

        Bundle extras = getIntent().getExtras();
        if(!extras.isEmpty()){
            int id = extras.getInt("categoryID");
            Message.info(this,String.valueOf(id));
        }



    }

    private void initProducts() {

        ProductRVAdaptor prvAdaptor = new ProductRVAdaptor(this);
        ArrayList<Product> products = new ArrayList<>();

        products.add(new Product(0,"",0,"",1,0));
        for (int i = 0; i < 10; i++)
            products.add(new Product(0,"",0,"",0,0));
        products.add(new Product(0,"",0,"",1,0));


        prvAdaptor.setProducts(products);
        rvProductList.setAdapter(prvAdaptor);

        //set default category list as linear
        rvProductList.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
    }

    private void initView() {
        rvProductList = findViewById(R.id.rvProductList);
        btnBack = findViewById(R.id.btnBack);

    }
}