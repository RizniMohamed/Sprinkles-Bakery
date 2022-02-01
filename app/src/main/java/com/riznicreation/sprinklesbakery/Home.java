package com.riznicreation.sprinklesbakery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.riznicreation.sprinklesbakery.tabs.VPAdapter;

public class Home extends AppCompatActivity {

    //Declaration of global variables
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private TextView textHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        handleNotchScreen();

        initViews();
        initTabs();

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
    }


    //move whole application down when notch is present in the screen
    private void handleNotchScreen() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        if (statusBarHeight > convertDpToPixel(24)) {
            LinearLayout LLMain = findViewById(R.id.LLMain);
            LLMain.setPadding(0, statusBarHeight - convertDpToPixel(15), 0, 0);
        }
    }

    private int convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
}