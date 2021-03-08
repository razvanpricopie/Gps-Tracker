package com.example.loginapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.loginapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        AuthenticationPagerAdapter pagerAdapter = new AuthenticationPagerAdapter(this);
        pagerAdapter.addFragment(new LoginFragment());
        pagerAdapter.addFragment(new RegisterFragment());
        viewPager.setAdapter(pagerAdapter);
    }


    static class AuthenticationPagerAdapter extends FragmentStateAdapter {
        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        public AuthenticationPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }

        @NonNull
        @Override
        public Fragment createFragment(int i) {
            return fragmentList.get(i);
        }


        void addFragment(Fragment fragment){
            fragmentList.add(fragment);
        }
    }
}