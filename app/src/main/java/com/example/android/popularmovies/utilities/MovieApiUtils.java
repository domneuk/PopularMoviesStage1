package com.example.android.popularmovies.utilities;

import android.content.Context;

import com.example.android.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.List;

public final class MovieApiUtils {

    public static List<Movie> getListOfMoviesFromJson(Context context, String apiJsonStr) throws JSONException {

        final String LIST_OBJECT = "results";
        final String ERROR_MSG = "status_message";
        final String ERROR_CODE = "status_code";

        List<Movie> moviesList = null;

        JSONObject apiJson = new JSONObject(apiJsonStr);

        if (apiJson.has(ERROR_MSG) || apiJson.has(ERROR_CODE)) {
            int errorCode = apiJson.getInt(ERROR_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

        JSONArray moviesArray = apiJson.getJSONArray(LIST_OBJECT);
        moviesList = Movie.fromJson(moviesArray);

        return moviesList;
    }

}
