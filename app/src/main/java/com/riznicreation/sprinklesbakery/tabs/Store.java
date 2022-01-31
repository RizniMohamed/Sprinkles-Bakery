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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.entity.Category;
import com.riznicreation.sprinklesbakery.store.CategoryRVAdaptor;

import java.util.ArrayList;
import java.util.Objects;

public class Store extends Fragment {

    private RecyclerView categoryRV;
    private TextView textAllCategories;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_store, container, false);

        initViews(view);

        initAllCategories();

        textAllCategories.setOnClickListener(v -> {
            if(Objects.requireNonNull(categoryRV.getLayoutManager()).canScrollHorizontally())
                categoryRV.setLayoutManager(new GridLayoutManager(getContext(),4));
            else
                categoryRV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        });


        categoryRV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        return view;
    }

    private void initAllCategories() {

        CategoryRVAdaptor crvAdaptor = new CategoryRVAdaptor();
        ArrayList<Category> categories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            categories.add(new Category(i,"Birthday"+i,R.drawable.ic_cart));
        }
//        categories.add(new Category(1,"Birthday",R.drawable.ic_cart));
//        categories.add(new Category(2,"Wedding",R.drawable.ic_favorite));
//        categories.add(new Category(3,"Teacher",R.drawable.ic_menu));
//        categories.add(new Category(4,"Farewell",R.drawable.ic_shopping_basket));
        crvAdaptor.setCategories(categories);

        categoryRV.setAdapter(crvAdaptor);
    }

    private void initViews(View view) {
        categoryRV = view.findViewById(R.id.rvCategories);
        textAllCategories = view.findViewById(R.id.textAllCategories);
    }
}