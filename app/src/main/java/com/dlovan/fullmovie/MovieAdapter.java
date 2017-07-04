package com.dlovan.fullmovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    private LayoutInflater inflater;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = inflater.inflate(R.layout.list_movie, parent, false);
        return new MovieHolder(root);
    }

    @Override
    public void onBindViewHolder(final MovieHolder holder, int position) {

        final Movie movie = list.get(position);
        holder.title.setText(movie.getTitle());
        Picasso.with(context).load(movie.getPoster_path()).placeholder(R.drawable.placeholders).error(R.drawable.placeholders).into(holder.imageMovie);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setMovieList(List<Movie> movies) {
        if (movies == null)
            return;
        list.clear();
        list.addAll(movies);
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
