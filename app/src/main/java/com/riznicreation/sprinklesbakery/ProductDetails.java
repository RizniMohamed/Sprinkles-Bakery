package com.riznicreation.sprinklesbakery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.entity.Product;
import com.riznicreation.sprinklesbakery.helper.Message;
import com.riznicreation.sprinklesbakery.tabs.Cart;

public class ProductDetails extends AppCompatActivity {

    private ImageView ivProductPic;
    private EditText txtName,txtCream,txtFlavour,txtUnitPrice,txtQuantity,txtDiscountPrice;
    private TextView btnAddCart;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        initViews();

        //Get product id when click the product from prev intent
        int productID = getIntent().getIntExtra("productID",-1);
        //If product is present then set the product details
        if(productID != -1) setProductDetails(productID);

        setFieldDisable();

        btnBack.setOnClickListener( v -> onBackPressed());

    }

    private boolean validate() {
        if(txtQuantity.getText().toString().isEmpty() || txtQuantity.getText().toString().equals("0")){
            Message.error(this,"Quantity cannot be empty");
        }else{
            return true;
        }
        return false;
    }

    private void setFieldDisable() {
        txtName.setEnabled(false);
        txtCream.setEnabled(false);
        txtFlavour.setEnabled(false);
        txtUnitPrice.setEnabled(false);
        txtDiscountPrice.setEnabled(false);
    }

    @SuppressLint("SetTextI18n")
    private void setProductDetails(int id) {
        DBHelper DB = new DBHelper(this);
        DB.product().getAllProducts().forEach( p -> {
            if(p.getProduct_id() == id){

                txtCream.setText("Cream : ".concat(p.getCream()));
                txtFlavour.setText("Flavour : ".concat(p.getFlavour()));
                txtName.setText("Name : ".concat(p.getProduct_name()));
                txtUnitPrice.setText("Unit Price : ".concat(String.valueOf(p.getUnit_price())));

                if(p.getDiscount() != 0) {
                    txtDiscountPrice.setText("Discount Price : ".concat(calcDiscountPrice(p.getUnit_price(), p.getDiscount())));
                } else{
                    ((View) txtDiscountPrice.getParent()).setVisibility(View.GONE);
                    txtDiscountPrice.setVisibility(View.GONE);
                }

                setProductImage(p.getImage());

                btnAddCart.setOnClickListener(v -> {
                    if(new DBHelper(this).auth().checkLoginStatus()){
                        if(validate()){
                            boolean isNew = true;
                            p.setQuantity(Integer.parseInt(txtQuantity.getText().toString()));

                            if(Cart.products.isEmpty()) isNew = true;

                            //Prevent duplication
                            for ( Product cart : Cart.products) {
                                if (cart.getProduct_id() == p.getProduct_id()){
                                    isNew = false;
                                    cart.setQuantity(p.getQuantity());
                                }
                            }

                            if (isNew) Cart.products.add(p);

                            Message.success(this,"Product added to the cart");
                            this.onBackPressed();
                        }
                    }else{
                        startActivity(new Intent(this,Login.class));
                        finish();
                    }
                });
            }
        });
    }

    private void setProductImage(Bitmap image) {
        Glide.with(this)
                .asBitmap()
                .centerCrop()
                .fitCenter()
                .placeholder(R.drawable.ic_cake)
                .load(image)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(3)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_cake)
                .into(ivProductPic);
    }

    @NonNull
    private String calcDiscountPrice(float unit_price, int discount) {
        return String.valueOf((unit_price - discount*unit_price/100));
    }

    private void initViews() {
        ivProductPic = findViewById(R.id.ivProductPic);
        txtName = findViewById(R.id.txtName);
        txtCream = findViewById(R.id.txtCream);
        txtFlavour = findViewById(R.id.txtFlavour);
        txtQuantity = findViewById(R.id.txtQuantity);
        txtUnitPrice = findViewById(R.id.txtUnitPrice);
        btnAddCart = findViewById(R.id.btnAddCart);
        txtDiscountPrice = findViewById(R.id.txtDiscountPrice);
        btnBack = findViewById(R.id.btnBack);
    }
}