package com.example.guest.derfilmdatenbank.models;

import java.util.ArrayList;

/**
 * Created by Guest on 7/6/16.
 */
public class Person {
    private String mName;
    private String mCharacter;
    private String mId;
    private String mPicture;
    private String mBiography;
    private String mBirthday;
    private String mDeathday;
    private String mPlaceofbirth;
    private ArrayList<Movie> mMovies;




    public Person(String name, String character, String id, String picture) {
        this.mName = name;
        this.mCharacter = character;
        this.mId = id;
        this.mPicture = picture;
    }

    public Person(String name, String biography, String id, String picture, String birthday, String deathday, String placeofbirth, ArrayList<Movie> movies) {
        this.mName = name;
        this.mBiography = biography;
        this.mId = id;
        this.mPicture = picture;
        this.mBirthday = birthday;
        this.mDeathday = deathday;
        this.mPlaceofbirth = placeofbirth;
        this.mMovies = movies;
    }

    public String getName() {
        return mName;
    }
    public String getCharacter() {
        return mCharacter;
    }
    public String getId() {
        return mId;
    }
    public String getPicture() {
        return mPicture;
    }
    public String getBiography() {
        return mBiography;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public String getDeathday() {
        return mDeathday;
    }

    public String getPlaceofbirth() {
        return mPlaceofbirth;
    }

    public ArrayList<Movie> getMovies() {
        return mMovies;
    }
}
