package com.example.guest.derfilmdatenbank.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.guest.derfilmdatenbank.R;
import com.example.guest.derfilmdatenbank.adapters.MovieAdapter;
import com.example.guest.derfilmdatenbank.models.Movie;
import com.example.guest.derfilmdatenbank.services.MovieDataBase;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieResults extends AppCompatActivity {
    @Bind(R.id.recycler3View) RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;

    public ArrayList<Movie> mMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_results);
        ButterKnife.bind(this);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            getSearch(query);
        }
    }
    private void getSearch(String query) {
        final MovieDataBase movieDatabase = new MovieDataBase();
        movieDatabase.search(query, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mMovies = movieDatabase.processMovies(response);



                MovieResults.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter = new MovieAdapter(getApplicationContext(), mMovies);
                        Log.d("title", mAdapter.toString());
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MovieResults.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }

                });
            }

        });
    }
}
