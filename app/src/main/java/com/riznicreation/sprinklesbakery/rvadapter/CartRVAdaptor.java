package com.riznicreation.sprinklesbakery.rvadapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.entity.Product;
import com.riznicreation.sprinklesbakery.helper.Message;
import com.riznicreation.sprinklesbakery.tabs.Cart;

import java.util.ArrayList;

//Recycle adapter class for generate repeater elements with various data
public class CartRVAdaptor extends RecyclerView.Adapter<CartRVAdaptor.ViewHolder>{

    private ArrayList<Product> products = new ArrayList<>();
    private final Context context;
    private TextView txtTotal;
    private String page;

    public void setPage(String page) {this.page = page;}

    public CartRVAdaptor(Context context) {
        this.context = context;
    }

    public void setCart(ArrayList<Product> product) {
        this.products = product;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Product product = products.get(position);


        holder.textName.setText(product.getProduct_name());
        String text = (product.getUnit_price() - product.getDiscount() * product.getUnit_price() / 100) +
                " * " + product.getQuantity() +
                " = " +  calcDiscountPrice(product.getUnit_price(),product.getDiscount(),product.getQuantity()) ;
        holder.textStatus.setText(text);

        Glide.with(context)
                .asBitmap()
                .centerCrop()
                .fitCenter()
                .placeholder(R.drawable.ic_cake)
                .load(product.getImage())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(3)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_cake)
                .into(holder.imgProduct);


        if(page.contains("Order")){
            holder.rlDelete.setVisibility(View.GONE);
        }else{
            holder.btnRemove.setOnClickListener(v ->{
                Cart.products.remove(product);
                products.remove(product);
                notifyItemRemoved(position);
                setTotalPrice();
                Message.success(context,"Deleted successfully");
            });
        }
    }
    public void setTotalPrice() {
        int total = 0;
        for (Product p : products) {
            total += calcDiscountPriceF(p.getUnit_price(),p.getDiscount(),p.getQuantity());
        }
        txtTotal.setText(String.valueOf((float) total));

    }

    @NonNull
    private String calcDiscountPrice(float unit_price, int discount, int quanitiy) {
        return String.valueOf((unit_price - discount*unit_price/100)*quanitiy);
    }

    private float calcDiscountPriceF(float unit_price, int discount, int quanitiy) {
        return (unit_price - discount*unit_price/100)*quanitiy;
    }

    @Override
    public int getItemCount() {
            return products.size();
    }

    public void setTotal(TextView txtTotal) {
        this.txtTotal = txtTotal;
    }

    // Class which holds the elements view details
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //view castings
        private final ImageView imgProduct;
        private final TextView textName,textStatus;
        private final ImageView btnRemove;
        private final RelativeLayout rlDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //View declarations
            imgProduct = itemView.findViewById(R.id.imgProduct);
            textName = itemView.findViewById(R.id.textName);
            textStatus = itemView.findViewById(R.id.textStatus);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            rlDelete = itemView.findViewById(R.id.rlDelete);
        }
    }
}
