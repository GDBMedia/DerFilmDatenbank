package com.example.guest.derfilmdatenbank.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.guest.derfilmdatenbank.R;
import com.example.guest.derfilmdatenbank.adapters.MovieAdapter;
import com.example.guest.derfilmdatenbank.models.Movie;
import com.example.guest.derfilmdatenbank.services.MovieDataBase;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.Response;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;

    public ArrayList<Movie> mMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getNowPlaying();
    }

    private void getNowPlaying() {
        final MovieDataBase movieDatabase = new MovieDataBase();
        movieDatabase.nowplaying(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mMovies = movieDatabase.processMovies(response);

                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter = new MovieAdapter(getApplicationContext(), mMovies);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(MainActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }

                });
            }

        });
    }
}
