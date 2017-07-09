package com.dlovan.fullmovie.fragments;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.dlovan.fullmovie.database.Columns;
import com.dlovan.fullmovie.database.MovieContentProvider;
import com.dlovan.fullmovie.models.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dlovan on 7/3/17.
 */

public class TopRatedFragment extends SubFragments {

    @Override
    public String getType() {
        return "top_rated";
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                Columns.ListMovieTopRate._ID,
                Columns.ListMovieTopRate.TITLE,
                Columns.ListMovieTopRate.POSTER_PATH
        };

        return new CursorLoader(getActivity(),
                MovieContentProvider.ListMovieTopRate.CONTENT_URI,
                projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.i("Movie", "onLoadFinished: returned cursor: top_rate " + cursor.getCount());
        List<Movie> list = new ArrayList<>();
        int ti = cursor.getColumnIndex(Columns.ListMovieTopRate.TITLE);
        int img = cursor.getColumnIndex(Columns.ListMovieTopRate.POSTER_PATH);

        while (cursor.moveToNext()) {
            String title = cursor.getString(ti);
            String image = cursor.getString(img);
            list.add(new Movie(title, image));
        }

        for (Movie movie : list) {
            Log.i("Movie", "onLoadFinished: " + movie.getTitle());
        }

        adapter.setMovieList(list);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
