package com.dlovan.fullmovie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dlovan.fullmovie.R;
import com.dlovan.fullmovie.fragments.PopularFragment;
import com.dlovan.fullmovie.fragments.TopRatedFragment;
import com.dlovan.fullmovie.service.MovieServiceDownload;
import com.dlovan.fullmovie.utils.Utils;
import com.dlovan.fullmovie.views.adapter.PagerAdapter;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tabs)
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setTitle(getString(R.string.app_name));

        //setup toolbar
        setSupportActionBar(toolbar);

        //setup pager
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        if (isRTL(Locale.getDefault())) {
            pagerAdapter.addFragment(new PopularFragment(), getString(R.string.popular));
            pagerAdapter.addFragment(new TopRatedFragment(), getString(R.string.top_rated));
            pager.setAdapter(pagerAdapter);
            pager.setCurrentItem(pagerAdapter.getCount());
            ViewCompat.setLayoutDirection(tabs, ViewCompat.LAYOUT_DIRECTION_LTR);
        } else {
            pagerAdapter.addFragment(new PopularFragment(), getString(R.string.popular));
            pagerAdapter.addFragment(new TopRatedFragment(), getString(R.string.top_rated));
            pager.setAdapter(pagerAdapter);
        }


        tabs.setupWithViewPager(pager);

        //call service
        Intent intent = new Intent(this, MovieServiceDownload.class);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, SettingActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Helper method to detect a local to know it is RTL or not.
     *
     * @param locale Local instance.
     * @return Boolean indicates the local is RTL or not
     */
    private boolean isRTL(Locale locale) {

        // ckb is an RTL language but Android system can't detect it
        // so that we manually handle it.
        if (locale.getLanguage().equals("ckb")) {
            return true;
        }

        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }
}
