package com.example.guest.derfilmdatenbank.models;

import java.util.ArrayList;

/**
 * Created by Guest on 7/6/16.
 */


public class Movie {
    private String mTitle;
    private String mUrlImage;
    private String mId;
    private String mOverview;
    private String mReleaseDate;
    private String mRevenue;
    private String mRuntime;
    private String mCharacter;
    private String mYear;
    private String mMediaType;
    private double mRating;
    private ArrayList<Person> mActors;

    public Movie(String title, String imageUrl, String id, double rating, String overview) {
        this.mTitle = title;
        this.mUrlImage = imageUrl;
        this.mId = id;
        this.mRating = rating;
        this.mOverview = overview;
    }

    public Movie(String title, String imageUrl, String id, String character, String year, String mediatype) {
        this.mTitle = title;
        this.mUrlImage = imageUrl;
        this.mId = id;
        this.mCharacter = character;
        this.mYear = year;
        this.mMediaType = mediatype;
    }

    public Movie(String title, String imageUrl, String id, double rating, String overview, String releaseDate, String revenue, String runtime, ArrayList<Person> actors) {
        this.mTitle = title;
        this.mUrlImage = imageUrl;
        this.mId = id;
        this.mRating = rating;
        this.mOverview = overview;
        this.mReleaseDate = releaseDate;
        this.mRevenue = revenue;
        this.mRuntime = runtime;
        this.mActors = actors;
    }

    public String getCharacter() {
        return mCharacter;
    }
    public String getRevenue() {
        return mRevenue;
    }
    public String getRuntime() {
        return mRuntime;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImageUrl() {
        return mUrlImage;
    }

    public String getId() {
        return mId;
    }

    public double getRating() {
        return mRating;
    }

    public String getOverview(){
        return mOverview;
    }
    public String getReleaseDate(){
        return mReleaseDate;
    }
    public String getYear() {
        return mYear;
    }
    public String getMediaType() {
        return mMediaType;
    }
    public ArrayList<Person> getActors() {
        return mActors;
    }
}
