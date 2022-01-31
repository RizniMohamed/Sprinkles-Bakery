package com.riznicreation.sprinklesbakery.store;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.entity.Category;

import java.util.ArrayList;

public class CategoryRVAdaptor extends RecyclerView.Adapter<CategoryRVAdaptor.ViewHolder>{

    private ArrayList<Category> categories = new ArrayList<>();


    public void setCategories(ArrayList<Category> Category) {
        this.categories = Category;
    }

    public ArrayList<Category> Category() {
        return categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_category,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Category category = categories.get(position);

        holder.btnIcon.setBackgroundResource(category.getIconID());
        holder.name.setText(category.getName());


//        mContext.startActivity(new Intent(mContext,BookActivity.class));

    };

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageButton btn;
        private ImageView btnIcon;
        private TextView name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btn = itemView.findViewById(R.id.btn);
            btnIcon = itemView.findViewById(R.id.btnIcon);
            name = itemView.findViewById(R.id.name);

        }
    }
}
