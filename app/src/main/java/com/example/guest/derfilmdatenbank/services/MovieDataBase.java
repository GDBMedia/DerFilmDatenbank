package com.example.guest.derfilmdatenbank.services;

/**
 * Created by Guest on 7/6/16.
 */
import android.util.Log;

import com.example.guest.derfilmdatenbank.Constants;
import com.example.guest.derfilmdatenbank.models.Movie;
import com.example.guest.derfilmdatenbank.models.Person;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDataBase {
    public static void nowplaying(Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.NOW_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.KEY_PARAM, Constants.API_KEY);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void search(String query, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.SEARCH_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.QUERY_PARAM, query)
                  .addQueryParameter(Constants.KEY_PARAM, Constants.API_KEY);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }



    public static void movie(String id, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.MOVIE_BASE_URL + id).newBuilder();
        urlBuilder.addQueryParameter(Constants.APPEND_PARAM, Constants.FILM_APPEND_PARAM_ANS)
                  .addQueryParameter(Constants.LANGUAGE_PARAM, Constants.LANGUAGE_PARAM_ANS)
                  .addQueryParameter(Constants.KEY_PARAM, Constants.API_KEY);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }


    public static void tv(String id, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.TV_BASE_URL + id).newBuilder();
        urlBuilder.addQueryParameter(Constants.APPEND_PARAM, Constants.FILM_APPEND_PARAM_ANS)
                .addQueryParameter(Constants.LANGUAGE_PARAM, Constants.LANGUAGE_PARAM_ANS)
                .addQueryParameter(Constants.KEY_PARAM, Constants.API_KEY);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }



    public static void person(String id, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.PERSON_BASE_URL + id).newBuilder();
        urlBuilder.addQueryParameter(Constants.APPEND_PARAM, Constants.PERSON_APPEND_PARAM_ANS)
                  .addQueryParameter(Constants.LANGUAGE_PARAM, Constants.LANGUAGE_PARAM_ANS)
                  .addQueryParameter(Constants.KEY_PARAM, Constants.API_KEY);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public Movie processMovie(Response response){
        Movie movie = null;
        SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFormat = new SimpleDateFormat("MMMM dd, yyyy");
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject movieJSON = new JSONObject(jsonData);
                    String title = movieJSON.getString("title");
                    String imageUrl = Constants.IMAGE_BASE_URL + movieJSON.getString("poster_path");
                    String id = Integer.toString(movieJSON.getInt("id"));
                    double rating = movieJSON.getDouble("vote_average");
                    String overview = movieJSON.getString("overview");
                    String releaseDate = movieJSON.getString("release_date");
                    String revenue = movieJSON.getString("revenue");
                    double amount = Double.parseDouble(revenue);
                    DecimalFormat formatter = new DecimalFormat("#,###.00");
                    String runtime = movieJSON.getString("runtime");
                    String reformattedStr = "N/A";
                    try {
                        reformattedStr = myFormat.format(sdfSource.parse(releaseDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    ArrayList<Person> Actors = new ArrayList<>();
                    JSONArray actorsJSON = movieJSON.getJSONObject("credits").getJSONArray("cast");
                    for (int y = 0; y < actorsJSON.length(); y++) {
                        JSONObject actorsArray = actorsJSON.getJSONObject(y);
                        String actorId = Integer.toString(actorsArray.getInt("id"));
                        Actors.add(new Person(actorsArray.getString("name"), actorsArray.getString("character"), actorId , Constants.IMAGE_BASE_URL + actorsArray.getString("profile_path")));
                    }

                    movie = new Movie(title, imageUrl, id, rating, overview, reformattedStr, formatter.format(amount), runtime, Actors);
                }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }


    public ArrayList<Movie> processMovies(Response response) {
        ArrayList<Movie> movies = new ArrayList<>();
        SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFormat = new SimpleDateFormat("MMMM DE, yyyy");

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject movieDatabaseJSON = new JSONObject(jsonData);
                JSONArray moviesJSON = movieDatabaseJSON.getJSONArray("results");
                for (int i = 0; i < moviesJSON.length(); i++) {
                    JSONObject movieJSON = moviesJSON.getJSONObject(i);
                    String title = movieJSON.getString("title");
                    String imageUrl = Constants.IMAGE_BASE_URL + movieJSON.getString("poster_path");
                    String id = Integer.toString(movieJSON.getInt("id"));
                    double rating = movieJSON.getDouble("vote_average");
                    String overview = movieJSON.getString("overview");
                    String releaseDate = movieJSON.getString("release_date");
                    String reformattedStr = "N/A";
                    try {
                        reformattedStr = myFormat.format(sdfSource.parse(releaseDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Movie movie = new Movie(title, imageUrl, id, rating, overview, reformattedStr);

                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
