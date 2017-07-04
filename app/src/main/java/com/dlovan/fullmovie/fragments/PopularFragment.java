package com.dlovan.fullmovie.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.dlovan.fullmovie.adapter.MovieAdapter;
import com.dlovan.fullmovie.R;
import com.dlovan.fullmovie.models.Movies;
import com.dlovan.fullmovie.network.MovieClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dlovan on 7/3/17.
 */

public class PopularFragment extends Fragment {


    @BindView(R.id.rec_list)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    public TextView mEmptyView;

    MovieAdapter adapter;
    private Subscription subscription;

    View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.pager_fragment, container, false);
        ButterKnife.bind(this, root);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (Configuration.ORIENTATION_LANDSCAPE == getScreenOrientation()) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
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

        return root;
    }

    @Override
    public void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    private void getMovie(String username) {
        subscription = MovieClient.getInstance()
                .getMovies("popular", username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movies>() {
                    @Override
                    public void onCompleted() {
                        ProgressBar progressBar = (ProgressBar) root.findViewById(R.id.loading_indicator);
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
            ProgressBar progressBar = (ProgressBar) root.findViewById(R.id.loading_indicator);
            progressBar.setVisibility(View.GONE);
        }
    }
}
