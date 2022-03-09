package com.riznicreation.sprinklesbakery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ProductDetails extends AppCompatActivity {

    private ImageView ivProductPic;
    private EditText txtName,txtCream,txtFlavour,txtUnitPrice,txtQuantity,txtDiscountPrice;
    private TextView btn;
    private ImageButton btnBack;
    private ActivityResultLauncher<Intent> resultLauncher;
    private byte[] inputImageData;
    private DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        initViews();

        String task = getIntent().getStringExtra("task");

        switch (task) {
            case "view":
                //Get product id when click the product from prev intent
                int productID = getIntent().getIntExtra("productID", -1);
                //If product is present then set the product details and add button functionality
                if (productID != -1) setProductDetails(productID,false);

                setFieldDisable();
                break;
            case "add":

                int category_id = getIntent().getIntExtra("category_id", -1);
                if (category_id == -1) {
                    onBackPressed();
                    finish();
                }

                ((View) txtQuantity.getParent()).setVisibility(View.GONE);
                ((View) txtDiscountPrice.getParent()).setVisibility(View.GONE);

                setProductImage(R.mipmap.cake, 20);
                ivProductPic.setOnClickListener(v -> chooseImage());


                btn.setText(R.string.addproduct);

                btn.setOnClickListener(v -> {
                    if (validateAdd()) {
                        boolean status = DB.product().addProduct(
                                txtName.getText().toString(),
                                txtCream.getText().toString(),
                                txtFlavour.getText().toString(),
                                txtUnitPrice.getText().toString(),
                                inputImageData,
                                category_id
                        );
                        if (status) {
                            DB.product().getAllProducts();
                            Message.success(this, "New product added successfully");
                            onBackPressed();
                        } else {
                            Message.error(this, "Error on adding new product");
                        }
                    }
                });

                break;
            case "edit":

                //Get product id when click the product from prev intent
                productID = getIntent().getIntExtra("productID", -1);
                //If product is present then set the product details and add button functionality
                if (productID != -1) setProductDetails(productID, true);

                ((View) txtQuantity.getParent()).setVisibility(View.GONE);
                ((View) txtDiscountPrice.getParent()).setVisibility(View.GONE);

                setProductImage(R.mipmap.cake, 20);
                ivProductPic.setOnClickListener(v -> chooseImage());


                btn.setText("Update");

                btn.setOnClickListener(v -> {
                    if (validateAdd()) {
                        boolean status = DB.product().UpdateProduct(
                                txtName.getText().toString(),
                                txtCream.getText().toString(),
                                txtFlavour.getText().toString(),
                                txtUnitPrice.getText().toString(),
                                inputImageData,
                                productID
                        );
                        if (status) {
                            DB.product().getAllProducts();
                            Message.success(this, "Product updated successfully");
                            onBackPressed();
                        } else {
                            Message.error(this, "Error on adding new product");
                        }
                    }
                });

                break;
        }

        btnBack.setOnClickListener( v -> onBackPressed());

    }

    private boolean validateAdd() {

        EditText[] texts = new EditText[]{txtName,txtCream,txtFlavour,txtUnitPrice};
        for (EditText et: texts) {
            if(et.getText().toString().isEmpty()){
                Message.error(this,et.getHint().toString().concat(" cannot be empty"));
                return false;
            }
        }

        if(inputImageData == null || inputImageData.length == 0) {
            Message.error(this,"Product image cannot be empty");
            return false;
        }

        return true;
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLauncher.launch(intent);
    }

    private void imageActivityResultLauncher() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri uri = data.getData();
                            setProductImage(uri,15);
                            InputStream iStream = null;
                            try {
                                iStream = getContentResolver().openInputStream(uri);
                            } catch (FileNotFoundException e) {
                                Message.info(this,e.getLocalizedMessage());
                            }
                            assert iStream != null;
                            inputImageData = getBytes(iStream);
                        }
                    }
                });
    }

    @NonNull
    private byte[] getBytes(@NonNull InputStream inputStream)  {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while (true) {
            try {
                if ((len = inputStream.read(buffer)) == -1) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private boolean validateView() {
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
    private void setProductDetails(int id, boolean editable) {

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

                setProductImage(p.getImage(),3);

                if(!editable){
                    //Add product to card
                    btn.setOnClickListener(v -> {
                        if(DB.auth().checkLoginStatus()){
                            if(validateView()){
                                boolean isNew = true;
                                p.setQuantity(Integer.parseInt(txtQuantity.getText().toString()));

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
            }
        });
    }

    private void setProductImage(Object image, int corner) {
        Glide.with(this)
                .asBitmap()
                .centerCrop()
                .fitCenter()
                .placeholder(R.mipmap.cake)
                .load(image)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(corner)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.cake)
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
        btn = findViewById(R.id.btn);
        txtDiscountPrice = findViewById(R.id.txtDiscountPrice);
        btnBack = findViewById(R.id.btnBack);

        DB = new DBHelper(this);
        imageActivityResultLauncher();
    }

    @Override
    public void onBackPressed() {
        //TODO set home on back view
        startActivity(new Intent(this,Manage.class));
        finish();
    }
}