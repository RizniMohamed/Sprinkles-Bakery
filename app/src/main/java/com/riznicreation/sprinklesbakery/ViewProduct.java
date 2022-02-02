package com.riznicreation.sprinklesbakery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.riznicreation.sprinklesbakery.entity.Product;
import com.riznicreation.sprinklesbakery.rvadapter.ProductRVAdaptor;

import java.util.ArrayList;

public class ViewProduct extends AppCompatActivity {

    private RecyclerView rvProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        initVIews();

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

    private void initVIews() {
        rvProductList = findViewById(R.id.rvProductList);
    }
}