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
        switch (position){
            case 1:
                return new Order();
            case 2:
                return new Cart();
            case 3:
                return new Profile();
            default:
                return new Store();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}
