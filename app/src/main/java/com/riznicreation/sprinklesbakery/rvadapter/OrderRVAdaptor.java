package com.riznicreation.sprinklesbakery.rvadapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.entity.Order;
import com.riznicreation.sprinklesbakery.helper.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

//Recycle adapter class for generate repeater elements with various data
public class OrderRVAdaptor extends RecyclerView.Adapter<OrderRVAdaptor.ViewHolder>{

    private ArrayList<Order> orders = new ArrayList<>();
    private final Context context;

    public OrderRVAdaptor(Context context) {
        this.context = context;
    }

    public void setOrders(ArrayList<Order> Order) {
        this.orders = Order;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Order order = orders.get(position);

        holder.orderID.setText(String.valueOf(order.getId()));
        holder.totPrice.setText(String.valueOf(order.getTotPrice()));
        holder.date.setText(order.getOrderedDate());

        switch (order.getStatus()){
            case 0: holder.status.setText("Process");break;
            case 1: holder.status.setText("Hold");break;
            case 2: holder.status.setText("Delivered");break;
        }

        holder.btnDown.setOnClickListener( v -> {
            if(holder.orderMoreDetails.getVisibility() == View.VISIBLE){
                holder.orderMoreDetails.setVisibility(View.GONE);
                holder.btnDown.setRotation(360);
            }else{
                holder.orderMoreDetails.setVisibility(View.VISIBLE);
                holder.btnDown.setRotation(180);
            }
        });

        //Generate items of product
        CartRVAdaptor crvAdaptor = new CartRVAdaptor(context);
        crvAdaptor.setPage("Order");
        crvAdaptor.setCart(new DBHelper(context).order().getOrderItems(order.getId()));
        //set default category list as linear
        holder.rv_order_product.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        holder.rv_order_product.setAdapter(crvAdaptor);


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    // Class which holds the elements view details
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //view castings
        private final TextView orderID,status,date,totPrice;
        private final ImageButton btnDown;
        private final RecyclerView rv_order_product;
        private final LinearLayout orderMoreDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //View declarations
            orderID = itemView.findViewById(R.id.orderID);
            btnDown = itemView.findViewById(R.id.btnDown);
            rv_order_product = itemView.findViewById(R.id.rv_order_product);
            orderMoreDetails = itemView.findViewById(R.id.orderMoreDetails);
            status = itemView.findViewById(R.id.status);
            date = itemView.findViewById(R.id.date);
            totPrice = itemView.findViewById(R.id.totPrice);
        }
    }
}
