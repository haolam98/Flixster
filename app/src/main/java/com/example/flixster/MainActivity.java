package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.MovieAdapter;
import Models.Movie;
import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";
    List<Movie> movies;
    RecyclerView rvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initiate
        rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        //Create an adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this,movies);

        //Set adapter onto the Recycler View
        rvMovies.setAdapter(movieAdapter);

        //Set layout manager on the Recycler View
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                // pull out data from json
                JSONObject jsonObject = json.jsonObject;
                try {
                    //there might be un-existed array so we do try catch
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.d(TAG, "Results: "+ results.toString());

                    //Get movies info from 'results' object
                    movies.addAll( Movie.fromJsonArray(results));

                    //whenever movies list changes -> re-render adapter
                    movieAdapter.notifyDataSetChanged();

                    Log.i(TAG, "Movies: "+ movies.size());

                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON Exception!!");

                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

    }
}