package com.example.agroobot_fms;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new BerandaFragment();
            case 1:
                return new JadwalFragment();
            case 2:
                return new PanenFragment();
            case 3:
                return new AnggaranFragment();
            default:
                return null;

        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
