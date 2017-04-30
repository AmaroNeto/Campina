package com.andura.campina.timemachine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andura.campina.R;

/**
 * Created by amaro on 29/04/17.
 */

public class TimeMachineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timemachine_activity);

        int position = getIntent().getIntExtra("POSITION",0);


        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(position);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return TimeMachineFragment.newInstance(0);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return TimeMachineFragment.newInstance(1);
                case 2: // Fragment # 1 - This will show SecondFragment
                    return TimeMachineFragment.newInstance(2);
                case 3: // Fragment # 1 - This will show SecondFragment
                    return TimeMachineFragment.newInstance(3);
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}
