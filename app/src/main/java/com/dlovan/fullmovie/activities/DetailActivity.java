package com.dlovan.fullmovie.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlovan.fullmovie.R;
import com.dlovan.fullmovie.models.Movie;
import com.dlovan.fullmovie.network.MovieClient;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    private Subscription subscription;
    int movieId;

    @BindView(R.id.title_detail_movie)
    TextView titleMovie;

    @BindView(R.id.overview_movie)
    TextView overviewMovie;

    @BindView(R.id.toolbar_detail)
    Toolbar toolbar;

    @BindView(R.id.backdrop)
    ImageView backdrop;

    @BindView(R.id.movie_image_detail)
    ImageView imageDetail;

    @BindView(R.id.vote_average)
    TextView vote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        movieId = bundle.getInt("ID");

        checkInternet();

        setTitle(bundle.getString("TITLE"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            getMovie("5ba1e2bf08bea434def560bf5014dbb8");

            Log.d("NO_INTERNET", "if work");
        } else {
            Log.d("NO_INTERNET", "else work");
//            ProgressBar progressBar = (ProgressBar) root.findViewById(R.id.loading_indicator);
//            progressBar.setVisibility(View.GONE);
        }
    }

    private void getMovie(String apiKey) {
        subscription = MovieClient.getInstance()
                .getMovie(movieId, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Movie movie) {
                        titleMovie.setText(movie.getTitle());
                        overviewMovie.setText(movie.getOverview());
                        vote.setText(Double.toString(movie.getVoteAverage()));

                        Picasso.with(getApplicationContext())
                                .load(movie.getPosterPath())
                                .placeholder(R.drawable.placeholders)
                                .error(R.drawable.placeholders)
                                .into(imageDetail);

                        Picasso.with(getApplicationContext())
                                .load(movie.getBackdropPath())
                                .placeholder(R.drawable.placeholders)
                                .error(R.drawable.placeholders)
                                .into(backdrop);
                    }
                });
    }
}
