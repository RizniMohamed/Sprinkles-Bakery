package com.riznicreation.sprinklesbakery.store;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.entity.Product;

import java.util.ArrayList;

//Recycle adapter class for generate repeater elements with various data
public class ProductRVAdaptor extends RecyclerView.Adapter<ProductRVAdaptor.ViewHolder>{

    private ArrayList<Product> products = new ArrayList<>();

    public void setProducts(ArrayList<Product> Product) {
        this.products = Product;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product category = products.get(position);
        //set dynamic values for each elements
//        holder.btnIcon.setBackgroundResource(category.getIconID());
//        holder.name.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    // Class which holds the elements view details
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //view castings
        private final ImageView imgProduct;
        private final TextView textWeight,textName,textPrice,textDiscount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //View declarations
            imgProduct = itemView.findViewById(R.id.imgProduct);
            textWeight = itemView.findViewById(R.id.textWeight);
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);
            textDiscount = itemView.findViewById(R.id.textDiscount);

        }
    }
}
