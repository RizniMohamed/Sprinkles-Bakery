package com.riznicreation.sprinklesbakery.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.rvadapter.OrderRVAdaptor;

public class Order extends Fragment {

    private RecyclerView rvOrder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        initViews(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initOrders();
    }

    private void initOrders() {
        OrderRVAdaptor orvAdaptor = new OrderRVAdaptor(getContext());
        orvAdaptor.setOrders(new DBHelper(getContext()).order().getOrder());

        //set default category list as linear
        rvOrder.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rvOrder.setAdapter(orvAdaptor);
    }

    private void initViews(View view) {
        rvOrder = view.findViewById(R.id.rvOrder);
    }
}