package com.riznicreation.sprinklesbakery.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.rvadapter.ListRVAdaptor;

import java.util.ArrayList;
import java.util.Date;

public class Order extends Fragment {

    private RecyclerView rvOrder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        initViews(view);
        initOrders();

        return view;
    }

    private void initOrders() {
        ListRVAdaptor orvAdaptor = new ListRVAdaptor(getContext());
        orvAdaptor.setPage("Order");
        ArrayList<com.riznicreation.sprinklesbakery.entity.Order> orders = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            orders.add(new com.riznicreation.sprinklesbakery.entity.Order(0,0,new Date(),0,0));
        }
        orvAdaptor.setOrders(orders);
        //set default category list as linear
        rvOrder.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rvOrder.setAdapter(orvAdaptor);
    }

    private void initViews(View view) {
        rvOrder = view.findViewById(R.id.rvOrder);
    }
}