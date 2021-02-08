package Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    Double rating;
    int movie_ID;


    public Movie()
    {
       //empty constructor needed by Parceler library
    }

    public Movie (JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        movie_ID = jsonObject.getInt("id");
    }

    public static List<Movie> fromJsonArray (JSONArray movieJSONArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i=0; i<movieJSONArray.length(); i++)
        {
            movies.add(new Movie(movieJSONArray.getJSONObject(i)));
        }
        return movies;
    }

    public Double getRating() {
        return rating;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
        //%s means attach the 'posterPath" to end of url
        // hardcode size : w342 size
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdropPath);
        //%s means attach the 'posterPath" to end of url
        // hardcode size : w342 size
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public int getMovie_ID() {
        return movie_ID;
    }
}
