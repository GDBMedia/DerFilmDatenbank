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
import com.example.guest.derfilmdatenbank.models.Person;
import com.example.guest.derfilmdatenbank.models.Show;
import com.example.guest.derfilmdatenbank.services.MovieDataBase;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TvDetailActivity extends AppCompatActivity {
    private Show mShow;
    @Bind(R.id.tvImageView) ImageView mImageView;
    @Bind(R.id.tvNameTextView) TextView mTVName;
    @Bind(R.id.ratingTextView) TextView mRating;
    @Bind(R.id.biographyTextView) TextView mDescription;
    @Bind(R.id.firstAirTextView) TextView mFirstAir;
    @Bind(R.id.lastAirTextView) TextView mLastAir;
    @Bind(R.id.numSeasonsTextView) TextView mSeasons;
    @Bind(R.id.numEpisodesTextView) TextView mEpisodes;
    @Bind(R.id.statusTextView) TextView mStatus;
    @Bind(R.id.recycler5View) RecyclerView mRecyclerView;
    private ActorAdapter mAdapter;
    private ArrayList<Person> mActors = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        getMovie(id);
    }

    private void getMovie(String id) {
        final MovieDataBase movieDatabase = new MovieDataBase();
        movieDatabase.tv(id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mShow = movieDatabase.processTV(response);

                TvDetailActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
//                        mFirstAir.setVisibility(View.GONE);
//                        mLastAir.setVisibility(View.GONE);
                        mActors = mShow.getActors();
                        Picasso.with(TvDetailActivity.this).load(mShow.getImageUrl()).into(mImageView);
                        mTVName.setText(mShow.getName());
                        mDescription.setText(mShow.getOverview());
                        mFirstAir.setText("First Aired: " + mShow.getFirstAir());
                        mLastAir.setText("Last Aired: " + mShow.getLastAir());
                        mSeasons.setText("Seasons: " + mShow.getSeasons());
                        mEpisodes.setText("Episodes: " + mShow.getEpisodes());
                        mStatus.setText("Status: " + mShow.getStatus());
//                        if(!mShow.getRuntime().equals("0")){
//                            mRuntime.setVisibility(View.VISIBLE);
//                            mRuntime.setText("Runtime: " + mShow.getRuntime() + " Minutes");
//                        }
//                        if(!mShow.getRevenue().equals("null")){
//                            mRevenue.setVisibility(View.VISIBLE);
//                            mRevenue.setText("Revenue: $" + mShow.getRevenue());
//                        }
                        mRating.setText("Rating: " + mShow.getRating() + "/10");
                        mAdapter = new ActorAdapter(getApplicationContext(), mActors);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(TvDetailActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }

                });
            }

        });
    }
}
