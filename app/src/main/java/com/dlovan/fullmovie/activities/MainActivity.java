package com.dlovan.fullmovie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dlovan.fullmovie.R;
import com.dlovan.fullmovie.fragments.PopularFragment;
import com.dlovan.fullmovie.fragments.TopRatedFragment;
import com.dlovan.fullmovie.views.adapter.PagerAdapter;
import com.dlovan.fullmovie.service.MovieServiceDownload;

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
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //setup toolbar
        setSupportActionBar(toolbar);

        //setup pager
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new PopularFragment(), getString(R.string.popular));
        pagerAdapter.addFragment(new TopRatedFragment(), getString(R.string.top_rated));
        pager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(pager);

        //call service
        Intent intent = new Intent(this, MovieServiceDownload.class);
        startService(intent);
    }
}
