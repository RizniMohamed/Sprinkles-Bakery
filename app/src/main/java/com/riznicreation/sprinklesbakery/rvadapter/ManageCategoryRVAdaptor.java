package com.riznicreation.sprinklesbakery.rvadapter;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.riznicreation.sprinklesbakery.ProductDetails;
import com.riznicreation.sprinklesbakery.R;
import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.entity.Category;
import com.riznicreation.sprinklesbakery.entity.Product;
import com.riznicreation.sprinklesbakery.helper.Message;

import java.util.ArrayList;

//Recycle adapter class for generate repeater elements with various data
public class ManageCategoryRVAdaptor extends RecyclerView.Adapter<ManageCategoryRVAdaptor.ViewHolder>{

    private ArrayList<Category> categories = new ArrayList<>();
    private final Context context;
    private DBHelper DB;


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
        DB = new DBHelper(context);

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

        initProducts(holder.rv_manage_products,category);

        holder.btnDelete.setOnClickListener( v -> deleteCategory(category, position));

        holder.btnEdit.setOnClickListener( v -> updateProduct(category, position, holder.rv_manage_products));

        holder.btnAdd.setOnClickListener( v -> {
            Intent intent = new Intent(context, ProductDetails.class);
            intent.putExtra("category_id",category.getId());
            intent.putExtra("task","add");
            context.startActivity(intent);
        });


    }

    private void initProducts(RecyclerView rv,Category category){
        //Generate items of product
        ProductRVAdaptor prvAdaptor = new ProductRVAdaptor(context);
        prvAdaptor.setProducts(new DBHelper(context).product().getProducts(category.getId()));
        prvAdaptor.setEditable(true);

        //set default category list as linear
        rv.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        rv.setAdapter(prvAdaptor);

    }

    private void updateProduct(@NonNull Category category, int position, RecyclerView rv){
        AlertDialog dialog = new AlertDialog.Builder(context).create();

        LayoutInflater factory = LayoutInflater.from(context);
        final View model = factory.inflate(R.layout.text_model, null);

        EditText txt = model.findViewById(R.id.txt);
        EditText txtNum = model.findViewById(R.id.txtNum);

        txt.setHint("Category");
        txtNum.setHint("Discount");

        txtNum.setVisibility(View.VISIBLE);
        txtNum.setText(String.valueOf(category.getDiscount()));
        txt.setText(category.getName());

        Button btnApply = model.findViewById(R.id.btnApply);
        Button btnCancel = model.findViewById(R.id.btnCancel);

        btnApply.setOnClickListener(v1 -> {
            if(!txt.getText().toString().isEmpty() && !txtNum.getText().toString().isEmpty()){
                int status = 0;
                if(category.getName().equals(txt.getText().toString())){
                    status = DB.category().UpdateCategory(category.getId(),txt.getText().toString(), Integer.parseInt(txtNum.getText().toString()));
                }else{
                    if(DB.category().categoryAvailability(txt.getText().toString()))
                        status = DB.category().UpdateCategory(category.getId(),txt.getText().toString(), Integer.parseInt(txtNum.getText().toString()));
                    else
                        Message.error(context,"Category name already exits");
                }

                if(status == 1){
                    notifyItemChanged(position);
                    categories.get(position).setDiscount(Integer.parseInt(txtNum.getText().toString()));
                    categories.get(position).setName(txt.getText().toString());
                    new DBHelper(context).product().getAllProducts();
                    initProducts(rv,category);
                    Message.success(context,"Category updated successfully");
                }else
                    Message.error(context,"Error on creating new category");

                dialog.dismiss();
            }else{
                if(txt.getText().toString().isEmpty())
                    Message.error(context,"Category name cannot be empty");
                if(txtNum.getText().toString().isEmpty())
                    Message.error(context,"Discount cannot be empty");
            }

        });
        btnCancel.setOnClickListener(v1 -> dialog.dismiss());

        dialog.setCancelable(false);
        dialog.setView(model);
        dialog.show();

    }

    private void deleteCategory(Category category, int position){
        AlertDialog dialog = new AlertDialog.Builder(context).create();

        LayoutInflater factory = LayoutInflater.from(context);
        @SuppressLint("InflateParams") final View model = factory.inflate(R.layout.delete_model, null);

        Button btnYes = model.findViewById(R.id.btnYes);
        Button btnNo = model.findViewById(R.id.btnNo);

        btnYes.setOnClickListener( view -> {
            if(DB.category().deleteCategory(category.getId())) {
                categories.remove(category);
                notifyItemRemoved(position);
                Message.success(context, "Deleted successfully");
            }else
                Message.error(context,"Error on deletion");
            dialog.dismiss();
        });

        btnNo.setOnClickListener( view -> dialog.dismiss());

        dialog.setView(model);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    // Class which holds the elements view details
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //view castings
        private final TextView txtCatName;
        private final ImageButton btnEdit,btnDelete,btnDown,btnAdd;
        private final RecyclerView rv_manage_products;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //View declarations
            txtCatName = itemView.findViewById(R.id.txtCatName);
            btnDown = itemView.findViewById(R.id.btnDown);
            rv_manage_products = itemView.findViewById(R.id.rv_manage_products);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}
