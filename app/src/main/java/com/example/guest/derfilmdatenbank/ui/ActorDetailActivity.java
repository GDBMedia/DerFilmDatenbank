package com.example.guest.derfilmdatenbank.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.derfilmdatenbank.R;
import com.example.guest.derfilmdatenbank.adapters.ActorAdapter;
import com.example.guest.derfilmdatenbank.adapters.MovieListAdapter;
import com.example.guest.derfilmdatenbank.models.Movie;
import com.example.guest.derfilmdatenbank.models.Person;
import com.example.guest.derfilmdatenbank.services.MovieDataBase;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ActorDetailActivity extends AppCompatActivity {
    private Person mActor;
    @Bind(R.id.actorImageView) ImageView mImageView;
    @Bind(R.id.actorNameTextView) TextView mActorName;
    @Bind(R.id.biographyTextView) TextView mBiography;
    @Bind(R.id.birthTextView) TextView mBirth;
    @Bind(R.id.placeofbirthTextView) TextView mBirthPlace;
    @Bind(R.id.deathTextView) TextView mDeath;
    @Bind(R.id.recycler4View) RecyclerView mRecyclerView;
    private MovieListAdapter mAdapter;
    private ArrayList<Movie> mMovies = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        Log.d("id", id);
        getActor(id);
    }

    private void getActor(String id) {
        final MovieDataBase movieDatabase = new MovieDataBase();
        movieDatabase.person(id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mActor = movieDatabase.processPerson(response);

                ActorDetailActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mBirth.setVisibility(View.GONE);
                        mDeath.setVisibility(View.GONE);
                        mMovies = mActor.getMovies();
                        Collections.sort(mMovies, new Comparator<Movie>() {
                            @Override public int compare(Movie m1, Movie m2) {
                                return Integer.parseInt(m2.getYear()) - Integer.parseInt(m1.getYear());
                            }
                        });
                        Picasso.with(ActorDetailActivity.this).load(mActor.getPicture()).into(mImageView);
                        mActorName.setText(mActor.getName());
                        mBiography.setText(mActor.getBiography());
                        if(!mActor.getBirthday().equals("")){
                            mBirth.setVisibility(View.VISIBLE);
                            mBirth.setText("Birth Date: " + mActor.getBirthday());
                        }
                        if(!mActor.getDeathday().equals("")){
                            mDeath.setVisibility(View.VISIBLE);
                            mDeath.setText("Death Date: " + mActor.getDeathday());
                        }
                        mBirthPlace.setText("Place of Birth: " + mActor.getPlaceofbirth());
                        mAdapter = new MovieListAdapter(getApplicationContext(), mMovies);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActorDetailActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }

                });
            }

        });
    }
}

