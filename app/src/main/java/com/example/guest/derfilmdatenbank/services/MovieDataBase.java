package com.example.guest.derfilmdatenbank.services;

/**
 * Created by Guest on 7/6/16.
 */
import com.example.guest.derfilmdatenbank.Constants;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

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
}
