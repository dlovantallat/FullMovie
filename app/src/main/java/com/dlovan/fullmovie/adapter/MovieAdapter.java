package com.dlovan.fullmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlovan.fullmovie.R;
import com.dlovan.fullmovie.activities.DetailActivity;
import com.dlovan.fullmovie.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dlovan on 7/2/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private List<Movie> list = null;
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

        final Movie movie = list.get(position);
        holder.title.setText(movie.getTitle());

        Picasso.with(context)
                .load(movie.getPosterPath())
                .placeholder(R.drawable.placeholders)
                .error(R.drawable.placeholders)
                .into(holder.imageMovie);

        holder.viewClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, movie.getTitle() + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("ID", movie.getId());
                intent.putExtra("TITLE", movie.getTitle());
                context.startActivity(intent);
            }
        });
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
        @BindView(R.id.view_click)
        LinearLayout viewClick;

        public MovieHolder(View root) {
            super(root);
            ButterKnife.bind(this, root);
        }
    }
}
