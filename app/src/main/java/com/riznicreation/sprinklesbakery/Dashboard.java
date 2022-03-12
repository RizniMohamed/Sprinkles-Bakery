package com.riznicreation.sprinklesbakery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.entity.Order;
import com.riznicreation.sprinklesbakery.rvadapter.OrderRVAdaptor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Dashboard extends AppCompatActivity {

    private ImageView btnDown_R, btnDown_U, btnDown_O;
    private TextView
            txt_R_LastYear,txt_R_CurrentYear,txt_R_FirstQ,
            txt_R_SecondQ,txt_R_ThirdQ,txt_R_FourthQ,txt_R_Diff,
            txt_U_Active,txt_U_Inactive,txt_U_Total;
    private RecyclerView rv_Order_dashboard;
    private DBHelper DB;
    private LinearLayout LLR,LLU,LLO;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initViews();

        initRevenue();
        initUsers();
        initOrders();
        initShowHide();

        btnBack.setOnClickListener( v -> onBackPressed());

    }

    private void initShowHide() {
        HashMap<ImageView,LinearLayout> map = new HashMap<>();
        map.put(btnDown_R,LLR);
        map.put(btnDown_U,LLU);
        map.put(btnDown_O,LLO);

        map.forEach( (imageView, linearLayout) ->
                imageView.setOnClickListener(v -> {
                            if( linearLayout.getVisibility() == View.VISIBLE ) {
                                linearLayout.setVisibility(View.GONE);
                                imageView.setRotation(0);
                            }else{
                                linearLayout.setVisibility(View.VISIBLE);
                                imageView.setRotation(180);
                            }
                        }
                )
        );

    }

    private void initOrders() {
        OrderRVAdaptor orvAdaptor = new OrderRVAdaptor(this);
        ArrayList<Order> orders = new ArrayList<>();
        for (Order o : DB.order().getAllOrders())
            if(o.getStatus() != 2)
                orders.add(o);

        orvAdaptor.setOrders(orders);
        orvAdaptor.setAdminManage(true);
        //set default category list as linear
        rv_Order_dashboard.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rv_Order_dashboard.setAdapter(orvAdaptor);
    }

    private void initUsers() {
        int[] userCounts = DB.user().getUserCounts();
        txt_U_Active.setText(String.valueOf(userCounts[0]));
        txt_U_Inactive.setText(String.valueOf(userCounts[1]));
        txt_U_Total.setText(String.valueOf(userCounts[2]));
    }

    private void initRevenue() {
        ArrayList<Order> orders = DB.order().getAllOrders();

        String[] prices = prices(orders);

        txt_R_LastYear.setText(prices[0].concat(" LKR"));
        txt_R_CurrentYear.setText(prices[1].concat(" LKR"));

        txt_R_FirstQ.setText(prices[2].concat(" LKR"));
        txt_R_SecondQ.setText(prices[3].concat(" LKR"));
        txt_R_ThirdQ.setText(prices[4].concat(" LKR"));
        txt_R_FourthQ.setText(prices[5].concat(" LKR"));

        if(Float.parseFloat(prices[6]) > 0)
            txt_R_Diff.setTextColor(getResources().getColor(R.color.success,getTheme()));
        else {
            txt_R_Diff.setTextColor(getResources().getColor(R.color.error, getTheme()));
            prices[6] = String.valueOf(Integer.parseInt(prices[6])*-1);
        }
        txt_R_Diff.setText(prices[6].concat(" LKR"));
    }

    /**
     * @param orders List of all orders
     * @return
     * 0 -> Last year <br>
     * 1 -> current year <br>
     * 2 -> first quarter <br>
     * 3 -> second quarter <br>
     * 4 -> third quarter <br>
     * 5 -> fourth quarter <br>
     * 6 -> Current - Last
     */
    @NonNull
    private String[] prices(@NonNull ArrayList<Order> orders){
        long total_last = 0;
        long total_current = 0;

        long total_1Q = 0;
        long total_2Q = 0;
        long total_3Q = 0;
        long total_4Q = 0;

        for (Order o : orders){
            if( LocalDate.parse(o.getOrderedDate()).getYear() == LocalDate.now().minusYears(1).getYear() )
                total_last += o.getTotPrice();

            if( LocalDate.parse(o.getOrderedDate()).getYear() == LocalDate.now().getYear() ){

                total_current += o.getTotPrice();

                if( LocalDate.parse(o.getOrderedDate()).getMonth().getValue() <= 3 )
                    total_1Q += o.getTotPrice();
                else if( LocalDate.parse(o.getOrderedDate()).getMonth().getValue() <= 6 )
                    total_2Q += o.getTotPrice();
                else if( LocalDate.parse(o.getOrderedDate()).getMonth().getValue() <= 9 )
                    total_3Q += o.getTotPrice();
                else if( LocalDate.parse(o.getOrderedDate()).getMonth().getValue() <= 12 )
                    total_4Q += o.getTotPrice();
            }

        }
        return new String[]{
                String.valueOf(total_last),
                String.valueOf(total_current),
                String.valueOf(total_1Q),
                String.valueOf(total_2Q),
                String.valueOf(total_3Q),
                String.valueOf(total_4Q),
                String.valueOf(total_current - total_last),
        };
    }

    private void initViews() {
        btnDown_R = findViewById(R.id.btnDown_R);
        btnDown_U = findViewById(R.id.btnDown_U);
        btnDown_O = findViewById(R.id.btnDown_O);

        txt_R_LastYear = findViewById(R.id.txt_R_LastYear);
        txt_R_CurrentYear = findViewById(R.id.txt_R_CurrentYear);
        txt_R_FirstQ = findViewById(R.id.txt_R_FirstQ);
        txt_R_SecondQ = findViewById(R.id.txt_R_SecondQ);
        txt_R_ThirdQ = findViewById(R.id.txt_R_ThirdQ);
        txt_R_FourthQ = findViewById(R.id.txt_R_FourthQ);
        txt_R_Diff = findViewById(R.id.txt_R_Diff);
        txt_U_Active = findViewById(R.id.txt_U_Active);
        txt_U_Inactive = findViewById(R.id.txt_U_Inactive);
        txt_U_Total = findViewById(R.id.txt_U_Total);

        rv_Order_dashboard = findViewById(R.id.rv_Order_dashboard);

        LLR = findViewById(R.id.LLR);
        LLU = findViewById(R.id.LLU);
        LLO = findViewById(R.id.LLO);

        btnBack = findViewById(R.id.btnBack);

        DB = new DBHelper(this);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,Home.class));
        finish();
    }
}