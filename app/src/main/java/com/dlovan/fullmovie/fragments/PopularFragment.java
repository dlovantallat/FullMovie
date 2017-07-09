package com.dlovan.fullmovie.fragments;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dlovan.fullmovie.R;
import com.dlovan.fullmovie.adapter.MovieAdapter;
import com.dlovan.fullmovie.database.Columns;
import com.dlovan.fullmovie.database.MovieContentProvider;
import com.dlovan.fullmovie.models.Movie;
import com.dlovan.fullmovie.service.MovieServiceDownload;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    @BindView(R.id.rec_list)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    public TextView mEmptyView;

    MovieAdapter adapter;

    private static final int MOVIE_LOADER = 0;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.pager_fragment, container, false);
        ButterKnife.bind(this, root);

        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (Configuration.ORIENTATION_LANDSCAPE == getScreenOrientation()) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }

        adapter = new MovieAdapter(getActivity());

        recyclerView.setAdapter(adapter);

        //this is for checking internet
        checkInternet();

        Button btnReload = (Button) root.findViewById(R.id.btn_reload);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInternet();
                ProgressBar progressBar = (ProgressBar) root.findViewById(R.id.loading_indicator);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        getActivity().getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
        return root;
    }

    public int getScreenOrientation() {
        Display getOrient = getActivity().getWindowManager().getDefaultDisplay();
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

    public void checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        TextView nInternet = (TextView) root.findViewById(R.id.no_internet);
        ImageView imageView = (ImageView) root.findViewById(R.id.no_internet_image);
        Button btnReload = (Button) root.findViewById(R.id.btn_reload);

        if (networkInfo != null && networkInfo.isConnected()) {
//            getMovie("5ba1e2bf08bea434def560bf5014dbb8");

            Intent intent = new Intent(getActivity(), MovieServiceDownload.class);
            intent.putExtra(MovieServiceDownload.Api_key, "5ba1e2bf08bea434def560bf5014dbb8");
            intent.putExtra(MovieServiceDownload.TYPE, "popular");
            getActivity().startService(intent);

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
            ProgressBar progressBar = (ProgressBar) root.findViewById(R.id.loading_indicator);
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                Columns.ListMovie._ID,
                Columns.ListMovie.TITLE,
                Columns.ListMovie.POSTER_PATH,
                Columns.ListMovie.STATUS
        };

        return new CursorLoader(getActivity(),
                MovieContentProvider.ListMovie.CONTENT_URI,
                projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.i("Movie", "onLoadFinished: returned cursor in popular: " + cursor.getCount());
        List<Movie> list = new ArrayList<>();
        int ti = cursor.getColumnIndex(Columns.ListMovie.TITLE);
        int img = cursor.getColumnIndex(Columns.ListMovie.POSTER_PATH);
        int type = cursor.getColumnIndex(Columns.ListMovie.STATUS);

        while (cursor.moveToNext()) {
            String title = cursor.getString(ti);
            String image = cursor.getString(img);
            String status = cursor.getString(type);
            list.add(new Movie(title, image, status));
        }

        for (Movie movie : list) {
            Log.i("Movie", "onLoadFinished: " + movie.getTitle());
            Log.i("Movie", "onLoadFinished: " + movie.getStatus());
        }

        adapter.setMovieList(list);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
