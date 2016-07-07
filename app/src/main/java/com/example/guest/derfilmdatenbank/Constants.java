package com.example.guest.derfilmdatenbank;

/**
 * Created by Guest on 7/6/16.
 */
public class Constants {

    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String SEARCH_BASE_URL = "http://api.themoviedb.org/3/search/movie?";
    public static final String NOW_BASE_URL = "http://api.themoviedb.org/3/movie/now_playing?";
    public static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String PERSON_BASE_URL = "http://api.themoviedb.org/3/person/";
    public static final String TV_BASE_URL = "http://api.themoviedb.org/3/tv/";
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/";
    public static final String QUERY_PARAM = "query";
    public static final String APPEND_PARAM = "append_to_response";
    public static final String FILM_APPEND_PARAM_ANS = "credits,images";
    public static final String PERSON_APPEND_PARAM_ANS = "combined_credits,images";
    public static final String LANGUAGE_PARAM = "include_image_language";
    public static final String LANGUAGE_PARAM_ANS = "en";
    public static final String KEY_PARAM = "api_key";



}
