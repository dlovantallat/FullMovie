package com.dlovan.fullmovie.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dlovan.fullmovie.R;
import com.dlovan.fullmovie.database.Columns;
import com.dlovan.fullmovie.database.MovieContentProvider;
import com.dlovan.fullmovie.models.Movie;
import com.dlovan.fullmovie.utils.Utils;
import com.dlovan.fullmovie.views.adapter.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * hold all top_rated fragment
 * Created by dlovan on 7/3/17.
 */
public class TopRatedFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rec_list)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    TextView mEmptyView;
    @BindView(R.id.loading_indicator)
    ProgressBar progressBar;
    @BindView(R.id.no_internet)
    TextView noInternet;
    @BindView(R.id.no_internet_image)
    ImageView imageView;
    @BindView(R.id.btn_reload)
    Button btnReload;

    private MovieAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.pager_fragment, container, false);
        ButterKnife.bind(this, root);

        //setup recycler view
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Utils.getNumOfColumns(getActivity())));
        mAdapter = new MovieAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(1, null, this);
        super.onActivityCreated(savedInstanceState);
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
        if (cursor.getCount() == 0) {
            if (Utils.isInternetAvailable(getActivity())) {
                noInternet.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
            } else {
                noInternet.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                btnReload.setVisibility(View.VISIBLE);
            }
        }
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
        mAdapter.setMovieList(list);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
