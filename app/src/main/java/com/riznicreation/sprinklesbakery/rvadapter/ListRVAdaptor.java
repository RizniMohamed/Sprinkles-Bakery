package com.riznicreation.sprinklesbakery.rvadapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.entity.Order;

import java.util.ArrayList;

//Recycle adapter class for generate repeater elements with various data
public class ListRVAdaptor extends RecyclerView.Adapter<ListRVAdaptor.ViewHolder>{

    private ArrayList<Order> orders = new ArrayList<>();
    private boolean btnRemove = false;
    private final Context context;

    public ListRVAdaptor(Context context) {
        this.context = context;
    }

    public void setOrders(ArrayList<Order> Order) {
        this.orders = Order;
    }
    public void enableBtnRemove(boolean btnRemove) {
        this.btnRemove = btnRemove;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Order order = orders.get(position);
        if(!btnRemove) holder.RLBtnRemove.setVisibility(View.GONE);
        holder.btnRemove.setOnClickListener(v -> Toast.makeText(v.getContext(), "Deleted", Toast.LENGTH_SHORT).show());
        //set dynamic values for each elements
//        holder.btnIcon.setBackgroundResource(category.getIconID());
//        holder.name.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    // Class which holds the elements view details
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //view castings
        private final ImageView imgProduct;
        private final TextView textName,textStatus;
        private final RelativeLayout RLBtnRemove;
        private final ImageView btnRemove;
        private final ConstraintLayout CLItem;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //View declarations
            imgProduct = itemView.findViewById(R.id.imgProduct);
            textName = itemView.findViewById(R.id.textName);
            textStatus = itemView.findViewById(R.id.textStatus);
            RLBtnRemove = itemView.findViewById(R.id.RLBtnRemove);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            CLItem = itemView.findViewById(R.id.CLItem);


        }
    }
}
