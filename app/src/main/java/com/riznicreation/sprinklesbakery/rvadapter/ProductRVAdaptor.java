package com.riznicreation.sprinklesbakery.rvadapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.riznicreation.sprinklesbakery.ProductDetails;
import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.entity.Product;

import java.util.ArrayList;

//Recycle adapter class for generate repeater elements with various data
public class ProductRVAdaptor extends RecyclerView.Adapter<ProductRVAdaptor.ViewHolder>{

    private ArrayList<Product> products = new ArrayList<>();
    private final Context context;

    public ProductRVAdaptor(Context context) {
        this.context = context;
    }

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
        final Product product = products.get(position);
        if(product.getDiscount() == 0) holder.textDiscount.setVisibility(View.GONE);

        Glide.with(context)
                .asBitmap()
                .centerCrop()
                .fitCenter()
                .placeholder(R.drawable.ic_cake)
                .load(product.getImage())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_cake)
                .into(holder.imgProduct);


        holder.textDiscount.setText(String.valueOf(product.getDiscount()).concat("%"));
        holder.textName.setText(product.getProduct_name());
        holder.textPrice.setText(context.getString(R.string.LKR).concat(" ").concat(String.valueOf(product.getUnit_price())));

        holder.cvProduct.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetails.class);
            intent.putExtra("productID",product.getProduct_id());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    // Class which holds the elements view details
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //view castings
        private final ImageView imgProduct;
        private final TextView textName,textPrice,textDiscount;
        private final CardView cvProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //View declarations
            imgProduct = itemView.findViewById(R.id.imgProduct);
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);
            textDiscount = itemView.findViewById(R.id.textDiscount);
            cvProduct = itemView.findViewById(R.id.cvProduct);

        }
    }
}
