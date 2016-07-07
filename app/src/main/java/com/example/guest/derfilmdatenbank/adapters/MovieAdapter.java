package com.example.guest.derfilmdatenbank.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.derfilmdatenbank.R;
import com.example.guest.derfilmdatenbank.models.Movie;
import com.example.guest.derfilmdatenbank.ui.MovieDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 7/6/16.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
private ArrayList<Movie> mMovies = new ArrayList<>();
private Context mContext;

public MovieAdapter(Context context, ArrayList<Movie> Movies) {
        mContext = context;
        mMovies = Movies;
        }
@Override
public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_layout, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
        }

@Override
public void onBindViewHolder(MovieAdapter.MovieViewHolder holder, int position) {
        holder.bindMovie(mMovies.get(position));
        }

@Override
public int getItemCount() {
        return mMovies.size();
        }
public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Bind(R.id.movieImageView) ImageView mMovieImageView;
    @Bind(R.id.movieNameTextView) TextView mNameTextView;
    @Bind(R.id.descriptionTextView) TextView mDescriptionTextView;
    @Bind(R.id.ratingTextView) TextView mRatingTextView;

    private Context mContext;

    public MovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);

    }

    public void bindMovie(Movie Movie) {
        String description = Movie.getOverview();
        String cutDescription = Movie.getOverview();
        if(description.length() > 163){
            cutDescription = description.substring(0, 163) +"...";
        }
        Picasso.with(mContext).load(Movie.getImageUrl()).into(mMovieImageView);
        mNameTextView.setText(Movie.getTitle());
        mDescriptionTextView.setText(cutDescription);
        mRatingTextView.setText("Rating: " + Movie.getRating() + "/10");
    }

    @Override
    public void onClick(View v) {
        int itemPosition = getLayoutPosition();
        Intent intent = new Intent(mContext, MovieDetailActivity.class);
        intent.putExtra("id", mMovies.get(itemPosition).getId());
        mContext.startActivity(intent);
    }


}

}
