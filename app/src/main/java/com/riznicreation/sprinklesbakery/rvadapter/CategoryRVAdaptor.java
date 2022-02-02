package com.riznicreation.sprinklesbakery.rvadapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.entity.Category;

import java.util.ArrayList;

//Recycle adapter class for generate repeater elements with various data
public class CategoryRVAdaptor extends RecyclerView.Adapter<CategoryRVAdaptor.ViewHolder>{

    private ArrayList<Category> categories = new ArrayList<>();
    private final Context context;

    public CategoryRVAdaptor(Context context) {
        this.context = context;
    }

    public void setCategories(ArrayList<Category> Category) {
        this.categories = Category;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_category,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Category category = categories.get(position);
        //set dynamic values for each elements
        holder.btnIcon.setBackgroundResource(category.getIconID());
        holder.name.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    // Class which holds the elements view details
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //view castings
        private final ImageView btnIcon;
        private final TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //View declarations
            btnIcon = itemView.findViewById(R.id.btnIcon);
            name = itemView.findViewById(R.id.name);

        }
    }
}
