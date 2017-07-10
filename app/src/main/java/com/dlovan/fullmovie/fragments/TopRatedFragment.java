package com.dlovan.fullmovie.fragments;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dlovan on 7/3/17.
 */

public class TopRatedFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rec_list)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    public TextView mEmptyView;
    MovieAdapter adapter;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.pager_fragment, container, false);
        ButterKnife.bind(this, root);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new MovieAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        checkInternet();

        getActivity().getLoaderManager().initLoader(1, null, this);
        return root;
    }


    public void checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        TextView nInternet = (TextView) root.findViewById(R.id.no_internet);
        ImageView imageView = (ImageView) root.findViewById(R.id.no_internet_image);
        Button btnReload = (Button) root.findViewById(R.id.btn_reload);

        if (networkInfo != null && networkInfo.isConnected()) {
            nInternet.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            btnReload.setVisibility(View.GONE);
        } else {
            nInternet.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            btnReload.setVisibility(View.VISIBLE);
            nInternet.setText("No Internet Connection");
            ProgressBar progressBar = (ProgressBar) root.findViewById(R.id.loading_indicator);
            progressBar.setVisibility(View.GONE);
        }
    }

    public int getNumOfColumns() {

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        int numOfColumns;
        if (dpWidth < dpHeight) {
            // portrait mode
            numOfColumns = 2;
            if (dpWidth >= 600) { // for tablet sw600
                numOfColumns = 3;
            }
        } else {
            // landscape mode
            numOfColumns = 3;
        }
        return numOfColumns;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                Columns.ListMovie._ID,
                Columns.ListMovie.ID,
                Columns.ListMovie.TITLE,
                Columns.ListMovie.POSTER_PATH,
                Columns.ListMovie.TYPE
        };

        return new CursorLoader(getActivity(),
                MovieContentProvider.ListMovie.CONTENT_URI,
                projection, " type = ? ", new String[]{"top_rated"}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<Movie> list = new ArrayList<>();
        int id = cursor.getColumnIndex(Columns.ListMovie.ID);
        int ti = cursor.getColumnIndex(Columns.ListMovie.TITLE);
        int img = cursor.getColumnIndex(Columns.ListMovie.POSTER_PATH);

        while (cursor.moveToNext()) {
            String title = cursor.getString(ti);
            String image = cursor.getString(img);
            int movieId = cursor.getInt(id);
            list.add(new Movie(movieId, title, image));
        }
        adapter.setMovieList(list);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
