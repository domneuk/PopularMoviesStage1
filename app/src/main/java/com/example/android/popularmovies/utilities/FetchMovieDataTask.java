package com.example.android.popularmovies.utilities;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.popularmovies.Movie;

import java.net.URL;
import java.util.List;

public class FetchMovieDataTask extends AsyncTask<String, Void, List<Movie>> {

    private static final String TAG = FetchMovieDataTask.class.getSimpleName();

    private Context context;
    private AsyncTaskHelper.AsyncTaskCompleteListener<List<Movie>> listener;

    public FetchMovieDataTask(Context ctx, AsyncTaskHelper.AsyncTaskCompleteListener<List<Movie>> listener) {
        this.context = ctx;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        if (params.length == 0) return null;

        String sorting = params[0];
        URL apiUrl = NetworkUtils.buildUrl(sorting);

        try {
            String jsonResponse = NetworkUtils.getResponseFromHttpUrl(apiUrl);
            return MovieApiUtils.getListOfMoviesFromJson(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
        listener.onTaskComplete(movies);
    }
}
