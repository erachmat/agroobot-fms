package com.example.agroobot_fms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.agroobot_fms.AnggaranFragment;
import com.example.agroobot_fms.BerandaFragment;
import com.example.agroobot_fms.JadwalFragment;
import com.example.agroobot_fms.PanenFragment;
import com.example.agroobot_fms.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(new ViewPagerAdapter(this));

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                bottomNav.getMenu().getItem(position).setChecked(true);
                menuItem = bottomNav.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        switch (item.getItemId()) {
            case R.id.beranda:
                viewPager.setCurrentItem(0);
                break;

            case R.id.jadwal:
                viewPager.setCurrentItem(1);
                break;

            case R.id.panen:
                viewPager.setCurrentItem(2);
                break;

            case R.id.anggaran:
                viewPager.setCurrentItem(3);
                break;

        }
        return false;
    };
}