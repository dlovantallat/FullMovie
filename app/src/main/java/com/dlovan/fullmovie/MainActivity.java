package com.dlovan.fullmovie;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    private Subscription subscription;
    public TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rec_list);
        mEmptyView = (TextView) findViewById(R.id.empty_view);

        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (Configuration.ORIENTATION_LANDSCAPE == getScreenOrientation()) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

        adapter = new MovieAdapter(MainActivity.this);
        recyclerView.setAdapter(adapter);

        //this is for checking internet
        checkInternet();
    }

    @Override
    protected void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    private void getMovie(String username) {
        subscription = MovieClient.getInstance()
                .getMovies(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movies>() {
                    @Override
                    public void onCompleted() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_indicator);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        recyclerView.setVisibility(View.GONE);
                        mEmptyView.setVisibility(View.VISIBLE);
                        mEmptyView.setText("No movie Found");
                        e.printStackTrace();
                        Log.d("TAG", "In onError()");
                    }

                    @Override
                    public void onNext(Movies movies) {
                        adapter.setMovieList(movies.getResults());
                    }
                });
    }

    public int getScreenOrientation() {
        Display getOrient = getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        if (getOrient.getWidth() == getOrient.getHeight()) {
            orientation = Configuration.ORIENTATION_SQUARE;
        } else {
            if (getOrient.getWidth() < getOrient.getHeight()) {
                orientation = Configuration.ORIENTATION_PORTRAIT;
            } else {
                orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        }
        return orientation;
    }

    public void reloadInternet(View view) {
        checkInternet();
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_indicator);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        TextView nInternet = (TextView) findViewById(R.id.no_internet);
        ImageView imageView = (ImageView) findViewById(R.id.no_internet_image);
        Button btnReload = (Button) findViewById(R.id.btn_reload);

        if (networkInfo != null && networkInfo.isConnected()) {
            getMovie("5ba1e2bf08bea434def560bf5014dbb8");

            Log.d("NO_INTERNET", "if work");
            nInternet.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            btnReload.setVisibility(View.GONE);
        } else {
            Log.d("NO_INTERNET", "else work");
            nInternet.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            btnReload.setVisibility(View.VISIBLE);
            nInternet.setText("No Internet Connection");
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_indicator);
            progressBar.setVisibility(View.GONE);
        }
    }
}
