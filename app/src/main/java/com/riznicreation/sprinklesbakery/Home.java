package com.riznicreation.sprinklesbakery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.riznicreation.sprinklesbakery.tabs.VPAdapter;

public class Home extends AppCompatActivity {

    //Declaration of global variables
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private TextView textHeader;
    private ImageButton btnSearch,btnMenu;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        initTabs();

        //Open view product page for search and view items
        btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(this,ViewProduct.class);
            startActivity(intent);
        });






        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        btnMenu.setOnClickListener(v -> {

          drawerLayout.setVisibility(View.VISIBLE);
            drawerLayout.openDrawer(GravityCompat.START);
        });
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

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
        navigationView.setNavigationItemSelectedListener(item -> {


            int id = item.getItemId();
            drawerLayout.closeDrawer(GravityCompat.START);
            switch (id) {
                case R.id.menuDashboard:
                    Toast.makeText(Home.this, "menuDashboard is Clicked", Toast.LENGTH_LONG).show();break;
                case R.id.menuStore:
                    Toast.makeText(Home.this, "menuStore is Clicked",Toast.LENGTH_LONG).show();break;
                case R.id.menuOrder:
                    Toast.makeText(Home.this, "menuOrder is Clicked",Toast.LENGTH_LONG).show();break;
                case R.id.menuCart:
                    Toast.makeText(Home.this, "menuCart is Clicked",Toast.LENGTH_LONG).show();break;
                case R.id.menuProfile:
                    Toast.makeText(Home.this, "menuProfile is Clicked",Toast.LENGTH_LONG).show();break;
                case R.id.menuReport:
                    Toast.makeText(Home.this, "menuReport is Clicked",Toast.LENGTH_LONG).show();break;
                case R.id.menuAuth:
                    Toast.makeText(Home.this, "menuAuth is clicked",Toast.LENGTH_LONG).show();break;
                 default:
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
                viewPager2.setCurrentItem(tab.getPosition());
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
    }
}