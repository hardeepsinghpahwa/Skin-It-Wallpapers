package com.pahwa.hardeep.wallpapers;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pahwa.hardeep.wallpapers.View_Pager_Frags.Categories;
import com.pahwa.hardeep.wallpapers.View_Pager_Frags.Downloaded_Images;
import com.pahwa.hardeep.wallpapers.View_Pager_Frags.Random_Images;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    int tabs;

    public ViewPagerAdapter(FragmentManager fm,int tabs) {
        super(fm);
        this.tabs=tabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                Categories categories = new Categories();
                return categories;

            case 1:
                Random_Images random=new Random_Images();
                return random;

            case 2:
                Downloaded_Images popular=new Downloaded_Images();
                return popular;

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
