package com.riznicreation.sprinklesbakery.tabs;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class VPAdapter extends FragmentStateAdapter {

    public VPAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //get page for their respective tab
        switch (position){
            case 1:
                return new Order();// returning order page
            case 2:
                return new Cart();// returning cart page
            case 3:
                return new Profile();// returning profile page
            default:
                return new Store();// returning store page
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}
