package com.example.hardeep.wallpapers.NavigationFrags;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hardeep.wallpapers.R;
import com.example.hardeep.wallpapers.ViewPagerAdapter;
import com.google.firebase.FirebaseApp;

public class Home extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);

        FirebaseApp.initializeApp(getActivity());


        tabLayout=v.findViewById(R.id.tablayout);
        viewPager=v.findViewById(R.id.viewpager);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.categoriesstring)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.randomimgs)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.downloaded_image_tab)));
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        tabLayout.setTabGravity(Gravity.HORIZONTAL_GRAVITY_MASK);

        viewPager=v.findViewById(R.id.viewpager);

        final FragmentPagerAdapter adapter=new ViewPagerAdapter(getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return v;
    }
}
