package com.example.agroobot_fms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.agroobot_fms.AnggaranFragment;
import com.example.agroobot_fms.BerandaFragment;
import com.example.agroobot_fms.JadwalFragment;
import com.example.agroobot_fms.PanenFragment;
import com.example.agroobot_fms.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // as soon as the application opens the first
        // fragment should be shown to the user
        // in this case it is algorithm fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BerandaFragment()).commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        // By using switch we can easily get
        // the selected fragment
        // by using there id.
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.beranda) {
            selectedFragment = new BerandaFragment();
        } else if (itemId == R.id.jadwal) {
            selectedFragment = new JadwalFragment();
        } else if (itemId == R.id.panen) {
            selectedFragment = new PanenFragment();
        } else if (itemId == R.id.anggaran) {
            selectedFragment = new AnggaranFragment();
        }
        // It will help to replace the
        // one fragment to other.
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        }
        return true;
    };
}