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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.entity.Order;
import com.riznicreation.sprinklesbakery.entity.Product;
import com.riznicreation.sprinklesbakery.helper.Message;
import com.riznicreation.sprinklesbakery.tabs.Cart;

import java.util.ArrayList;
import java.util.HashSet;

//Recycle adapter class for generate repeater elements with various data
public class ListRVAdaptor extends RecyclerView.Adapter<ListRVAdaptor.ViewHolder>{

    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private boolean btnRemove = false;
    private final Context context;
    private String page = "Cart";
    private TextView txtTotal;

    public ListRVAdaptor(Context context) {
        this.context = context;
    }

    public void setOrders(ArrayList<Order> Order) {
        this.orders = Order;
    }

    public void setCart(ArrayList<Product> product) {
        this.products = product;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setBtnRemove(boolean btnRemove) {
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

        if(page.equals("Order")){
            final Order order = orders.get(position);
        }

        if(page.equals("Cart")){
            final Product product = products.get(position);

            if(btnRemove) holder.RLBtnRemove.setVisibility(View.VISIBLE);

            holder.textName.setText(product.getProduct_name());
            holder.textStatus.setText(calcDiscountPrice(product.getUnit_price(),product.getDiscount(),product.getQuantity()));

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
    @NonNull
    private float calcDiscountPriceF(float unit_price, int discount, int quanitiy) {
        return (unit_price - discount*unit_price/100)*quanitiy;
    }

    @Override
    public int getItemCount() {
        if(page.equals("Order"))
            return orders.size();
        if(page.equals("Cart"))
            return products.size();
        return 0;
    }

    public void setTotal(TextView txtTotal) {
        this.txtTotal = txtTotal;
    }

    // Class which holds the elements view details
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //view castings
        private final ImageView imgProduct;
        private final TextView textName,textStatus;
        private final RelativeLayout RLBtnRemove;
        private final ImageView btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //View declarations
            imgProduct = itemView.findViewById(R.id.imgProduct);
            textName = itemView.findViewById(R.id.textName);
            textStatus = itemView.findViewById(R.id.textStatus);
            RLBtnRemove = itemView.findViewById(R.id.RLBtnRemove);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
