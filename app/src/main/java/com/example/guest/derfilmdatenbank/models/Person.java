package com.example.guest.derfilmdatenbank.models;

/**
 * Created by Guest on 7/6/16.
 */
public class Person {
    private String mName;
    private String mCharacter;
    private String mId;
    private String mPicture;




    public Person(String name, String character, String id, String picture) {
        this.mName = name;
        this.mCharacter = character;
        this.mId = id;
        this.mPicture = picture;
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
}
