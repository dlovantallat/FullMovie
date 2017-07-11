package com.dlovan.fullmovie.views.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * use for pager
 * Created by dlovan on 7/3/17.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }

    /**
     * Helper method to add fragments to the ViewPager
     *
     * @param fragment Fragment instance.
     */
    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    /**
     * Helper method to add fragments to the ViewPager
     *
     * @param fragment Fragment instance.
     * @param title    Fragment title.
     */
    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }
}
