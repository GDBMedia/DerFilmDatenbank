package com.example.guest.derfilmdatenbank.services;

/**
 * Created by Guest on 7/6/16.
 */

import android.util.Log;

import com.example.guest.derfilmdatenbank.Constants;
import com.example.guest.derfilmdatenbank.models.Movie;
import com.example.guest.derfilmdatenbank.models.Person;
import com.example.guest.derfilmdatenbank.models.Show;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
        Log.d("id", id);
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


    public Person processPerson(Response response){
        Person person = null;
        SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFormat = new SimpleDateFormat("MMMM dd, yyyy");
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject personJSON = new JSONObject(jsonData);
                String name = personJSON.getString("name");
                String biography = personJSON.getString("biography");
                String id = Integer.toString(personJSON.getInt("id"));
                String imageUrl = Constants.IMAGE_BASE_URL + personJSON.getString("profile_path");
                String birthday = personJSON.getString("birthday");
                String deathday = personJSON.optString("deathday");
                String placeofbirth = personJSON.getString("place_of_birth");
                String reformattedBirth = "";
                String reformattedDeath = "";
                try {
                    reformattedBirth = myFormat.format(sdfSource.parse(birthday));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(!deathday.equals("")){
                    try {
                        reformattedDeath = myFormat.format(sdfSource.parse(deathday));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                ArrayList<Movie> Movies = new ArrayList<>();
                JSONArray actorsJSON = personJSON.getJSONObject("combined_credits").getJSONArray("cast");
                for (int y = 0; y < actorsJSON.length(); y++) {
                    SimpleDateFormat myFormat2 = new SimpleDateFormat("yyyy");
                    JSONObject moviesArray = actorsJSON.getJSONObject(y);
                    String mediaType = moviesArray.getString("media_type");
                    String movieTitle = "";
                    String release = "";
                    if(mediaType.equals("movie")){
                        movieTitle = moviesArray.getString("title");
                        release = moviesArray.getString("release_date");
                    }
                    else if(mediaType.equals("tv")){
                        movieTitle = moviesArray.getString("name");
                        release = moviesArray.getString("first_air_date");
                    }

                    String moviePic = moviesArray.optString("poster_path", null);
                    String movieId = Integer.toString(moviesArray.getInt("id"));
                    String movieCharacter = moviesArray.getString("character");

                    String reformattedRelease = "0";
                    try {
                        reformattedRelease = myFormat2.format(sdfSource.parse(release));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Movies.add(new Movie(movieTitle, Constants.IMAGE_BASE_URL + moviePic, movieId , movieCharacter, reformattedRelease, mediaType));
                }
                Log.d("movies", Movies.toString());
                person = new Person(name, biography, id, imageUrl, reformattedBirth, reformattedDeath, placeofbirth, Movies);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return person;
    }

    public Show processTV(Response response){
        Show show = null;
        SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFormat = new SimpleDateFormat("MMMM dd, yyyy");
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject movieJSON = new JSONObject(jsonData);
                String name = movieJSON.getString("name");
                String imageUrl = Constants.IMAGE_BASE_URL + movieJSON.getString("poster_path");
                String id = Integer.toString(movieJSON.getInt("id"));
                double rating = movieJSON.getDouble("vote_average");
                String overview = movieJSON.getString("overview");
                String firstAir = movieJSON.getString("first_air_date");
                String lastAir = movieJSON.getString("last_air_date");
                String numEpisodes = movieJSON.getString("number_of_episodes");
                String numSeasons = movieJSON.getString("number_of_seasons");
                String status = movieJSON.getString("status");
                String reformattedFirstAir = "N/A";
                String reformattedLastAir = "N/A";
                try {
                    reformattedFirstAir = myFormat.format(sdfSource.parse(firstAir));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    reformattedLastAir = myFormat.format(sdfSource.parse(lastAir));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ArrayList<Person> Actors = new ArrayList<>();
                JSONArray actorsJSON = movieJSON.getJSONObject("credits").getJSONArray("cast");
                for (int y = 0; y < actorsJSON.length(); y++) {
                    JSONObject actorsArray = actorsJSON.getJSONObject(y);
                    String actorId = Integer.toString(actorsArray.getInt("id"));
                    String actorPic = actorsArray.optString("profile_path", null);
                    if(actorPic != "null"){
                        actorPic = Constants.IMAGE_BASE_URL + actorPic;
                    }
                    Actors.add(new Person(actorsArray.getString("name"), actorsArray.getString("character"), actorId , actorPic));
                }

                show = new Show(name, imageUrl, id, rating, overview, reformattedFirstAir, reformattedLastAir , numSeasons, numEpisodes, status, Actors);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return show;
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
                    String sRevenue = "null";
                    if(amount > 0){
                        DecimalFormat formatter = new DecimalFormat("#,###.00");
                        sRevenue = formatter.format(amount);
                    }
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
                        String actorPic = actorsArray.optString("profile_path", null);
                        if(actorPic != "null"){
                            actorPic = Constants.IMAGE_BASE_URL + actorPic;
                        }
                        Actors.add(new Person(actorsArray.getString("name"), actorsArray.getString("character"), actorId , actorPic));
                    }

                    movie = new Movie(title, imageUrl, id, rating, overview, reformattedStr, sRevenue , runtime, Actors);
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
                    Movie movie = new Movie(title, imageUrl, id, rating, overview);

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

    public ArrayList<Movie> processSearchedMovies(Response response) {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject movieDatabaseJSON = new JSONObject(jsonData);
                JSONArray moviesJSON = movieDatabaseJSON.getJSONArray("results");
                for (int i = 0; i < moviesJSON.length(); i++) {
                    JSONObject movieJSON = moviesJSON.getJSONObject(i);
                    String mediatype = movieJSON.getString("media_type");
                    String title = "";
                    String imageUrl = "";
                    String overview = "";
                    double rating = 0;
                    if(mediatype.equals("movie")){
                        title = movieJSON.getString("title");
                        imageUrl = Constants.IMAGE_BASE_URL + movieJSON.getString("poster_path");
                        rating = movieJSON.getDouble("vote_average");
                        overview = movieJSON.getString("overview");
                    }else if(mediatype.equals("tv")){
                        title = movieJSON.getString("name");
                        imageUrl = Constants.IMAGE_BASE_URL + movieJSON.getString("poster_path");
                        rating = movieJSON.getDouble("vote_average");
                        overview = movieJSON.getString("overview");
                    }
                    else if(mediatype.equals("person")){
                        title = movieJSON.getString("name");
                        imageUrl = Constants.IMAGE_BASE_URL + movieJSON.getString("profile_path");
                    }
                    Log.d("title", title);
                    String id = Integer.toString(movieJSON.getInt("id"));
                    Movie movie = new Movie(title, imageUrl, id, rating, overview);

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
