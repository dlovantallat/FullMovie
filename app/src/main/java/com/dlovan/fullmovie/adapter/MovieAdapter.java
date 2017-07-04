package com.dlovan.fullmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlovan.fullmovie.R;
import com.dlovan.fullmovie.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dlovan on 7/2/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private List<Movie> list = new ArrayList<>();
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.list_movie, parent, false);
        return new MovieHolder(root);
    }

    @Override
    public void onBindViewHolder(final MovieHolder holder, int position) {

        Movie movie = list.get(position);
        holder.title.setText(movie.getTitle());

        Picasso.with(context)
                .load(movie.getPosterPath())
                .placeholder(R.drawable.placeholders)
                .error(R.drawable.placeholders)
                .into(holder.imageMovie);
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    public void setMovieList(List<Movie> movies) {
        list = movies;
        notifyDataSetChanged();
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_movie)
        TextView title;
        @BindView(R.id.image_movie)
        ImageView imageMovie;

        public MovieHolder(View root) {
            super(root);
            ButterKnife.bind(this, root);
        }
    }
}
