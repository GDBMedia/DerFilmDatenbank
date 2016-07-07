package com.example.guest.derfilmdatenbank.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.derfilmdatenbank.R;
import com.example.guest.derfilmdatenbank.models.Movie;
import com.example.guest.derfilmdatenbank.ui.ActorDetailActivity;
import com.example.guest.derfilmdatenbank.ui.MovieDetailActivity;
import com.example.guest.derfilmdatenbank.ui.TvDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 7/7/16.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private Context mContext;

    public MovieListAdapter(Context context, ArrayList<Movie> movies) {
        mContext = context;
        mMovies = movies;
    }
    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_movie_list, parent, false);
        MovieListViewHolder viewHolder = new MovieListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, int position) {
        holder.bindActor(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }
    public class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.actorImageView) ImageView mMovieImageView;
        @Bind(R.id.actorNameTextView) TextView mNameTextView;
        @Bind(R.id.characterTextView) TextView mCharacterTextView;
        @Bind(R.id.yearTextView) TextView mYearTextView;
        @Bind(R.id.mediaTextView) TextView mMediaTextView;


        private Context mContext;

        public MovieListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);

        }

        public void bindActor(Movie movie) {
            Picasso.with(mContext).load(movie.getImageUrl()).into(mMovieImageView);
            mNameTextView.setText(movie.getTitle());
            if(movie.getMediaType().equals("tv")){
                mMediaTextView.setText("TV");
            }
            else if(movie.getMediaType().equals("movie")){
                mMediaTextView.setText("Movie");
            }
            mCharacterTextView.setText(movie.getCharacter());
            if (movie.getYear().equals("0")){
                mYearTextView.setText("Unknown");
            }else{
                mYearTextView.setText(movie.getYear());
            }

        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = null;
            if(mMovies.get(itemPosition).getMediaType().equals("movie")){
                intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("id", mMovies.get(itemPosition).getId());
            }
            else if(mMovies.get(itemPosition).getMediaType().equals("tv")){
                intent = new Intent(mContext, TvDetailActivity.class);
                intent.putExtra("id", mMovies.get(itemPosition).getId());
            }

            mContext.startActivity(intent);
        }


    }

}
