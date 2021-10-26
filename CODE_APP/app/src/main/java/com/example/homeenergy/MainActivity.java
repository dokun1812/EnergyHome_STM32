package com.example.homeenergy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView =findViewById(R.id.bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new ValueFragment());
    }
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()){
                case R.id.home_nav:
                    fragment=new ValueFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.control_nav:
                    fragment=new ControlFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.person_nav:
                    fragment=new PersonFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.replace(R.id.flContent,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}