package com.riznicreation.sprinklesbakery;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.riznicreation.sprinklesbakery.db.DBHelper;
import com.riznicreation.sprinklesbakery.helper.Message;
import com.riznicreation.sprinklesbakery.tabs.VPAdapter;

public class Home extends AppCompatActivity {

    //Declaration of global variables
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private TextView textHeader;
    private ImageButton btnSearch,btnMenu;
    private final DBHelper db = new DBHelper(this);
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        initTabs();
        initMenu();

        //Open view product page for search and view items
        btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(this,ViewProduct.class);
            startActivity(intent);
        });

        //Open Menu
        btnMenu.setOnClickListener(v -> {
            drawerLayout.setVisibility(View.VISIBLE);
            drawerLayout.openDrawer(GravityCompat.START);

            //Change the auth option's text and icon according to auth status in the menu
            if(db.auth().checkLoginStatus()){
                navigationView.getMenu().getItem(6).setTitle("Logout");
                navigationView.getMenu().getItem(6).setIcon(R.drawable.ic_logout);
            } else {
                navigationView.getMenu().getItem(6).setTitle("Login");
                navigationView.getMenu().getItem(6).setIcon(R.drawable.ic_login);
            }

            if(!db.auth().checkLoginStatus()){
                for (int i = 0; i < 6; i++) navigationView.getMenu().getItem(i).setVisible(false);
            }
        });


    }

    private void initMenu() {
        //Set drawer visible to gone when drawer is closed
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) { super.onDrawerSlide(drawerView, slideOffset); }

            @Override
            public void onDrawerOpened(View drawerView) { super.onDrawerOpened(drawerView); }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                drawerLayout.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        });




        //Set actions for each element in the menu
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.START);

            int id = item.getItemId();
            if (id == R.id.menuDashboard) { //Dashboard
                    Toast.makeText(Home.this, "menuDashboard is Clicked", Toast.LENGTH_LONG).show();
            } else if (id == R.id.menuStore) { //Store
                    tabLayout.selectTab(tabLayout.getTabAt(0));
                    viewPager2.setCurrentItem(0);
            } else if (id == R.id.menuOrder) { //Order
                    tabLayout.selectTab(tabLayout.getTabAt(1));
                    viewPager2.setCurrentItem(1);
            } else if (id == R.id.menuCart) { //Cart
                    tabLayout.selectTab(tabLayout.getTabAt(2));
                    viewPager2.setCurrentItem(2);
            } else if (id == R.id.menuProfile) { //Profile
                    tabLayout.selectTab(tabLayout.getTabAt(3));
                    viewPager2.setCurrentItem(3);
            } else if (id == R.id.menuReport) { //Report
                    Toast.makeText(Home.this, "menuReport is Clicked", Toast.LENGTH_LONG).show();
            } else if (id == R.id.menuAuth) { //Login or Logout
                if(navigationView.getMenu().getItem(6).getTitle() == "Login"){
                        startActivity(new Intent(this, Login.class));
                        finish();
                }else{
                    if (!db.auth().logout()) Message.error(this,"Error on logout");
                }
            } else {
                return true;
            }
            return true;
        });

    }

    private void initTabs() {
        VPAdapter adapter = new VPAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(adapter);

        //set pages for their respective tabs by tab selected listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(db.auth().checkLoginStatus()){
                    //If user logged in Direct to respective tabs
                    viewPager2.setCurrentItem(tab.getPosition());
                }else{
                    //If not user logged in Direct to login page
                    Intent intent = new Intent(Home.this,Login.class);
                    startActivity(intent);
                    finish();
                }
                //Set title of page according to their tabs
                switch (tab.getPosition()){
                    case 1: textHeader.setText(R.string.order); break;
                    case 2: textHeader.setText(R.string.cart); break;
                    case 3: textHeader.setText(R.string.profile); break;
                    default: textHeader.setText(R.string.Home);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        //Disable tab switching by swapping
        viewPager2.setUserInputEnabled(false);
    }

    private void initViews() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);
        textHeader = findViewById(R.id.textHeader);
        btnSearch = findViewById(R.id.btnSearch);
        btnMenu = findViewById(R.id.btnMenu);
        drawerLayout  = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
    }

    @Override
    public void onBackPressed() { }
}