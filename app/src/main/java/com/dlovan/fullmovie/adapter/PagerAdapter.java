package com.dlovan.fullmovie.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dlovan.fullmovie.fragments.PopularFragment;
import com.dlovan.fullmovie.fragments.TopRatedFragment;

/**
 * Created by dlovan on 7/3/17.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PopularFragment();
            case 1:
                return new TopRatedFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "POPULAR";
            case 1:
                return "TOP RATED";
        }
        return super.getPageTitle(position);
    }
}
