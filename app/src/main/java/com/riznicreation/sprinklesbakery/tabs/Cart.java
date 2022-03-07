package com.riznicreation.sprinklesbakery.tabs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.entity.Product;
import com.riznicreation.sprinklesbakery.helper.Message;
import com.riznicreation.sprinklesbakery.rvadapter.CartRVAdaptor;

import java.util.ArrayList;

public class Cart extends Fragment {

    public static ArrayList<Product> products = new ArrayList<>();
    private RecyclerView rvCart;
    private TextView txtTotal;
    private Button btnOrder;
    private int total = 0;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initViews(view);

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        initCartList(products);
        setTotalPrice();

        btnOrder.setOnClickListener( v -> {
            if(products.size() != 0) {
                DBHelper DB = new DBHelper(getContext());
                if(DB.order().setOrder(String.valueOf((float) total), String.valueOf(DB.user().getUser().getUserID()), new ArrayList<>(products))){
                    Message.success(getContext(),"Order added successfully");

                    products.clear();
                    initCartList(products);
                    setTotalPrice();

                }else{
                    Message.error(getContext(),"Error occurred in add order");
                }
            }else{
                Message.error(getContext(),"Cart is empty");
            }
        });
    }

    private void setTotalPrice() {
        total = 0;
        for (Product p : products) {
            total += calcDiscountPrice(p.getUnit_price(),p.getDiscount(),p.getQuantity());
        }
        txtTotal.setText(String.valueOf((float) total));
    }

    private float calcDiscountPrice(float unit_price, int discount, int quantity) {
        return (unit_price - discount*unit_price/100)*quantity;
    }

    private void initCartList(ArrayList<Product> items) {
        CartRVAdaptor crvAdaptor = new CartRVAdaptor(getContext());
        crvAdaptor.setCart(items);
        crvAdaptor.setTotal(txtTotal);
        crvAdaptor.setPage("Cart");
        //set default category list as linear
        rvCart.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rvCart.setAdapter(crvAdaptor);
    }

    private void initViews(@NonNull View view) {
        rvCart = view.findViewById(R.id.rvCart);
        txtTotal = view.findViewById(R.id.txtTotal);
        btnOrder = view.findViewById(R.id.btnOrder);
    }
}