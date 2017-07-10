package com.dlovan.fullmovie.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlovan.fullmovie.R;
import com.dlovan.fullmovie.database.Columns;
import com.dlovan.fullmovie.database.MovieContentProvider;
import com.dlovan.fullmovie.service.MovieServiceDownload;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

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
    @BindView(R.id.star)
    ImageView star;

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
        Log.i("TAG", "onCreate: " + movieId);

        setTitle(bundle.getString("TITLE"));

        getLoaderManager().initLoader(2, null, this);
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
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                Columns.DetailMovie._ID,
                Columns.DetailMovie.ID,
                Columns.DetailMovie.TITLE,
                Columns.DetailMovie.OVERVIEW,
                Columns.DetailMovie.POSTER_PATH,
                Columns.DetailMovie.BACKDROP_PATH,
                Columns.DetailMovie.VOTE};

        return new CursorLoader(getApplicationContext(), MovieContentProvider.DetailMovie.CONTENT_URI, projection, " movieId = ? ", new String[]{Integer.toString(movieId)}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.getCount() == 0) {
            Intent intent = new Intent(this, MovieServiceDownload.class);
            intent.putExtra(MovieServiceDownload.DETAIL, movieId);
            startService(intent);
        } else {
            cursor.moveToFirst();
            String title = cursor.getString(cursor.getColumnIndex(Columns.DetailMovie.TITLE));
            String overview = cursor.getString(cursor.getColumnIndex(Columns.DetailMovie.OVERVIEW));
            String poster = cursor.getString(cursor.getColumnIndex(Columns.DetailMovie.POSTER_PATH));
            String backdropImage = cursor.getString(cursor.getColumnIndex(Columns.DetailMovie.BACKDROP_PATH));
            double movieVote = cursor.getDouble(cursor.getColumnIndex(Columns.DetailMovie.VOTE));

            titleMovie.setText(title);
            overviewMovie.setText(overview);
            vote.setText(Double.toString(movieVote));

            Picasso.with(getApplicationContext())
                    .load("https://image.tmdb.org/t/p/w500" + poster)
                    .placeholder(R.drawable.placeholders)
                    .error(R.drawable.placeholders)
                    .into(imageDetail);

            Picasso.with(getApplicationContext())
                    .load("https://image.tmdb.org/t/p/w500" + backdropImage)
                    .placeholder(R.drawable.placeholders)
                    .error(R.drawable.placeholders)
                    .into(backdrop);

            star.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
