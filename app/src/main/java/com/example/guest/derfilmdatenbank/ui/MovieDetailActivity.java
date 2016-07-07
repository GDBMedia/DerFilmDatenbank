package com.example.guest.derfilmdatenbank.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.derfilmdatenbank.R;
import com.example.guest.derfilmdatenbank.adapters.MovieAdapter;
import com.example.guest.derfilmdatenbank.models.Movie;
import com.example.guest.derfilmdatenbank.services.MovieDataBase;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieDetailActivity extends AppCompatActivity {
    private Movie mMovie;
    @Bind(R.id.movieImageView) ImageView mImageView;
    @Bind(R.id.movieNameTextView) TextView mMovieName;
    @Bind(R.id.ratingTextView) TextView mRating;
    @Bind(R.id.descriptionTextView) TextView mDescription;
    @Bind(R.id.releaseTextView) TextView mRelease;
    @Bind(R.id.revenueTextView) TextView mRevenue;
    @Bind(R.id.runtimeTextView) TextView mRuntime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        getMovie(id);
    }

    private void getMovie(String id) {
        final MovieDataBase movieDatabase = new MovieDataBase();
        movieDatabase.movie(id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mMovie = movieDatabase.processMovie(response);

                MovieDetailActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Picasso.with(MovieDetailActivity.this).load(mMovie.getImageUrl()).into(mImageView);
                        mMovieName.setText(mMovie.getTitle());
                        mDescription.setText(mMovie.getOverview());
                        mRelease.setText("Release Date: " + mMovie.getReleaseDate());
                        mRuntime.setText("Runtime: " + mMovie.getRuntime() + " Minutes");
                        mRevenue.setText("Revenue: $" + mMovie.getRevenue());
                        mRating.setText("Rating: " + mMovie.getRating() + "/10");
                    }

                });
            }

        });
    }
}
