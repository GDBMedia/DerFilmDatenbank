package com.example.guest.derfilmdatenbank.models;

import java.util.ArrayList;

/**
 * Created by Guest on 7/6/16.
 */
public class Show {
    private String mName;
    private String mUrlImage;
    private String mId;
    private String mOverview;
    private String mFirstAirDate;
    private String mLastAirDate;
    private String mSeasons;
    private String mEpisodes;
    private String mCharacter;
    private String mYear;
    private String mStatus;
    private String mMediaType;
    private double mRating;
    private ArrayList<Person> mActors;

//    public Show(String title, String imageUrl, String id, double rating, String overview, String releaseDate) {
//        this.mName = title;
//        this.mUrlImage = imageUrl;
//        this.mId = id;
//        this.mRating = rating;
//        this.mOverview = overview;
//        this.mReleaseDate = releaseDate;
//    }

//    public Show(String title, String imageUrl, String id, String character, String year, String mediatype) {
//        this.mName = title;
//        this.mUrlImage = imageUrl;
//        this.mId = id;
//        this.mCharacter = character;
//        this.mYear = year;
//        this.mMediaType = mediatype;
//    }


    public Show(String name, String imageUrl, String id, double rating, String overview, String firstAir, String lastAir, String numSeasons, String numEpisodes, String status, ArrayList<Person> actors) {
        this.mName = name;
        this.mUrlImage = imageUrl;
        this.mId = id;
        this.mRating = rating;
        this.mOverview = overview;
        this.mFirstAirDate = firstAir;
        this.mLastAirDate = lastAir;
        this.mSeasons = numSeasons;
        this.mEpisodes = numEpisodes;
        this.mStatus = status;
        this.mActors = actors;
    }

    public String getCharacter() {
        return mCharacter;
    }
    public String getFirstAir() {
        return mFirstAirDate;
    }
    public String getLastAir() {
        return mLastAirDate;
    }

    public String getName() {
        return mName;
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
    public String getSeasons(){
        return mSeasons;
    }
    public String getEpisodes(){
        return mEpisodes;
    }

    public String getStatus() {
        return mStatus;
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


