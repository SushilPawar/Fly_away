package com.example.flyaway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlisterner);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_Booking()).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navlisterner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.book_flight:
                    fragment = new Fragment_Booking();
                    break;
                case R.id.history:
                    fragment = new Fragment_history();
                    break;
                case R.id.profile:
                    fragment = new Fragment_profile();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
    };
}