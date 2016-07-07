package com.example.guest.derfilmdatenbank.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.derfilmdatenbank.R;
import com.example.guest.derfilmdatenbank.adapters.ActorAdapter;
import com.example.guest.derfilmdatenbank.models.Movie;
import com.example.guest.derfilmdatenbank.models.Person;
import com.example.guest.derfilmdatenbank.services.MovieDataBase;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieDetailActivity extends AppCompatActivity {
    private Movie mMovie;
    @Bind(R.id.actorImageView) ImageView mImageView;
    @Bind(R.id.actorNameTextView) TextView mMovieName;
    @Bind(R.id.ratingTextView) TextView mRating;
    @Bind(R.id.biographyTextView) TextView mDescription;
    @Bind(R.id.birthTextView) TextView mRelease;
    @Bind(R.id.placeofbirthTextView) TextView mRevenue;
    @Bind(R.id.deathTextView) TextView mRuntime;
    @Bind(R.id.recycler2View) RecyclerView mRecyclerView;
    private ActorAdapter mAdapter;
    private ArrayList<Person> mActors = new ArrayList<>();


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
                        mRevenue.setVisibility(View.GONE);
                        mRuntime.setVisibility(View.GONE);
                        mActors = mMovie.getActors();
                        Picasso.with(MovieDetailActivity.this).load(mMovie.getImageUrl()).into(mImageView);
                        mMovieName.setText(mMovie.getTitle());
                        mDescription.setText(mMovie.getOverview());
                        mRelease.setText("Release Date: " + mMovie.getReleaseDate());
                        if(!mMovie.getRuntime().equals("0")){
                            mRuntime.setVisibility(View.VISIBLE);
                            mRuntime.setText("Runtime: " + mMovie.getRuntime() + " Minutes");
                        }
                        if(!mMovie.getRevenue().equals("null")){
                            mRevenue.setVisibility(View.VISIBLE);
                            mRevenue.setText("Revenue: $" + mMovie.getRevenue());
                        }
                        mRating.setText("Rating: " + mMovie.getRating() + "/10");
                        mAdapter = new ActorAdapter(getApplicationContext(), mActors);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(MovieDetailActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }

                });
            }

        });
    }
}
