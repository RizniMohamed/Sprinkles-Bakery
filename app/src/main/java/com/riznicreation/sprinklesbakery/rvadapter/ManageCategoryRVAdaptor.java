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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.entity.Category;
import com.riznicreation.sprinklesbakery.entity.Order;
import com.riznicreation.sprinklesbakery.helper.Message;

import java.util.ArrayList;

//Recycle adapter class for generate repeater elements with various data
public class ManageCategoryRVAdaptor extends RecyclerView.Adapter<ManageCategoryRVAdaptor.ViewHolder>{

    private ArrayList<Category> categories = new ArrayList<>();
    private final Context context;

    public ManageCategoryRVAdaptor(Context context) {
        this.context = context;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_manage_category,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Category category = categories.get(position);


        holder.txtCatName.setText(category.getName());

        holder.btnDown.setOnClickListener( v -> {
            if(holder.rv_manage_products.getVisibility() == View.VISIBLE){
                holder.rv_manage_products.setVisibility(View.GONE);
                holder.btnDown.setRotation(360);
            }else{
                holder.rv_manage_products.setVisibility(View.VISIBLE);
                holder.btnDown.setRotation(180);
            }
        });

        //Generate items of product
        ProductRVAdaptor prvAdaptor = new ProductRVAdaptor(context);
        prvAdaptor.setProducts(new DBHelper(context).product().getProducts(category.getId()));
        //set default category list as linear
        holder.rv_manage_products.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        holder.rv_manage_products.setAdapter(prvAdaptor);

        holder.btnEdit.setOnClickListener( v -> {
            Message.info(context,"Edit clicked");
        });

        holder.btnDelete.setOnClickListener( v -> {
            Message.info(context,"Delete clicked");
        });


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    // Class which holds the elements view details
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //view castings
        private final TextView txtCatName;
        private final ImageButton btnEdit,btnDelete,btnDown;
        private final RecyclerView rv_manage_products;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //View declarations
            txtCatName = itemView.findViewById(R.id.txtCatName);
            btnDown = itemView.findViewById(R.id.btnDown);
            rv_manage_products = itemView.findViewById(R.id.rv_manage_products);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
