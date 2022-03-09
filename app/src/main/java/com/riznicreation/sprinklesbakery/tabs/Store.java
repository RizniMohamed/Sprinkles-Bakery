package com.riznicreation.sprinklesbakery.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.entity.Category;
import com.riznicreation.sprinklesbakery.rvadapter.CategoryRVAdaptor;
import com.riznicreation.sprinklesbakery.rvadapter.ProductRVAdaptor;

import java.util.ArrayList;
import java.util.Objects;

public class Store extends Fragment {

    private RecyclerView categoryRV,rvMostPopular,rvSuperDeals;
    private TextView textAllCategories,textMPViewAll,textSPViewAll;
    private DBHelper db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate the layout of store page
        View view =  inflater.inflate(R.layout.fragment_store, container, false);

        initViews(view);
        initAllCategories();
        initMostPopular();
        initSuperDeals();

        //Set toggling expansion view for all categories
        setToggle(textAllCategories,categoryRV,4);
        //Set toggling expansion view for most popular products
        setToggle(textMPViewAll,rvMostPopular,3);
        //Set toggling expansion view for Special deal products
        setToggle(textSPViewAll,rvSuperDeals,3);

        return view;
    }

    private void setToggle(@NonNull TextView textView, @NonNull RecyclerView recyclerView, int spanCount) {

        //toggle the expansion view  when click the text
        textView.setOnClickListener(v -> {

            if(Objects.requireNonNull(recyclerView.getLayoutManager()).canScrollHorizontally()) {
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
                if(textView.getText().toString().contains("View")) textView.setText(R.string.view_less);
            }else {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                if(textView.getText().toString().contains("View")) textView.setText(R.string.view_all);
            }

            //fix disappearing discount tag while toggling the view
            initMostPopular();
            initSuperDeals();
        });

        //set default category list as linear
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    }

    private void initSuperDeals() {
        ProductRVAdaptor prvAdaptor = new ProductRVAdaptor(getContext());
        prvAdaptor.setProducts(db.product().getDiscountProducts());
        rvSuperDeals.setAdapter(prvAdaptor);
    }

    private void initMostPopular() {
        ProductRVAdaptor prvAdaptor = new ProductRVAdaptor(getContext());
        prvAdaptor.setProducts(db.product().getAllProducts());
        rvMostPopular.setAdapter(prvAdaptor);
    }

    private void initAllCategories() {
        CategoryRVAdaptor crvAdaptor = new CategoryRVAdaptor(getContext());
        ArrayList<Category> categories = db.category().getAll();
        crvAdaptor.setCategories(categories);
        categoryRV.setAdapter(crvAdaptor);
    }

    private void initViews(@NonNull View view) {
        categoryRV = view.findViewById(R.id.rvCategories);
        textAllCategories = view.findViewById(R.id.textAllCategories);
        textMPViewAll = view.findViewById(R.id.textMPViewAll);
        textSPViewAll = view.findViewById(R.id.textSPViewAll);
        rvMostPopular = view.findViewById(R.id.rvMostPopular);
        rvSuperDeals = view.findViewById(R.id.rvSuperDeals);
        db  = new DBHelper(view.getContext());
    }
}