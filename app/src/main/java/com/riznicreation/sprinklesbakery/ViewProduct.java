package com.riznicreation.sprinklesbakery;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.entity.Product;
import com.riznicreation.sprinklesbakery.rvadapter.ProductRVAdaptor;

import java.util.ArrayList;
import java.util.Locale;

public class ViewProduct extends AppCompatActivity {

    private RecyclerView rvProductList;
    private ImageButton btnBack;
    private final ArrayList<Product> viewProducts = new ArrayList<>();
    private DBHelper db;
    private EditText txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        initView();
        initCategoryProducts();

        //Direct to home page
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this,Home.class);
            startActivity(intent);
            finish();
        });


        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { initSearch(txtSearch.getText().toString()); }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    private void initSearch(String key) {
        ArrayList<Product> searchedProducts = new ArrayList<>();
        if(viewProducts.isEmpty()){
            for (Product p: com.riznicreation.sprinklesbakery.db.Product.products) {
                if(p.getProduct_name().toLowerCase(Locale.ROOT).contains(key))
                    searchedProducts.add(p);
            }
        }
        else {
            for (Product p: viewProducts) {
                if(p.getProduct_name().toLowerCase(Locale.ROOT).contains(key))
                    searchedProducts.add(p);
            }
        }


        initProducts(searchedProducts);
    }

    private void initCategoryProducts() {
        //get category's id and filter the all products by id and displaying
        Bundle extras = getIntent().getExtras();
        if( (extras != null) && (!extras.isEmpty()) ){
            int id = extras.getInt("categoryID");
            db.product();
            for(Product p : com.riznicreation.sprinklesbakery.db.Product.products){
                if(p.getCategory_id() == id)
                    viewProducts.add(p);
            }
            initProducts(viewProducts);
        }
    }

    private void initProducts(ArrayList<Product> products) {

        ProductRVAdaptor prvAdaptor = new ProductRVAdaptor(this);

        prvAdaptor.setProducts(products);
        rvProductList.setAdapter(prvAdaptor);

        //set default category list as linear
        rvProductList.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
    }

    private void initView() {
        rvProductList = findViewById(R.id.rvProductList);
        btnBack = findViewById(R.id.btnBack);
        txtSearch = findViewById(R.id.txtSearch);
        db = new DBHelper(this);
    }
}