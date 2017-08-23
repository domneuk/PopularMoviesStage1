package com.example.android.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.android.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private final static String API_BASE_URL = "https://api.themoviedb.org/3/movie/";

    private final static String API_ENDPOINT_TOP_RATED = "top_rated";
    private final static String API_ENDPOINT_POPULAR = "popular";

    private final static String API_KEY_PARAM = "api_key";
    private final static String API_KEY = BuildConfig.MOVIE_DB_API_KEY;

    private final static String API_LANGUAGE_PARAM = "language";
    private final static String API_LANGUAGE = "de-DE";

    private final static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    private final static String DEFAULT_IMAGE_SIZE = "w185";
    private final static String SMALL_IMAGE_SIZE = "w92";
    private final static String BIG_IMAGE_SIZE = "w500";

    public static final String SORTING_TOP_RATED = "TOP_RATED";
    public static final String SORTING_POPULAR = "POPULAR";

    public static URL buildUrl(String sorting) {
        String endpoint;
        if (sorting.toUpperCase().equals(SORTING_TOP_RATED)) {
            endpoint = API_ENDPOINT_TOP_RATED;
        } else {
            endpoint = API_ENDPOINT_POPULAR;
        }
        Uri builtUri = Uri.parse(API_BASE_URL + endpoint).buildUpon()
                .appendQueryParameter(API_LANGUAGE_PARAM, API_LANGUAGE)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static Uri buildUri(String sorting) {
        String endpoint;
        if (sorting.toUpperCase().equals(SORTING_TOP_RATED)) {
            endpoint = API_ENDPOINT_TOP_RATED;
        } else {
            endpoint = API_ENDPOINT_POPULAR;
        }
        return Uri.parse(API_BASE_URL + endpoint).buildUpon()
                .appendQueryParameter(API_LANGUAGE_PARAM, API_LANGUAGE)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();
    }

    public static Uri buildPosterUri(String imageUri) {
        return Uri.parse(BASE_IMAGE_URL + DEFAULT_IMAGE_SIZE + imageUri).buildUpon()
                .build();
    }

    public static Uri buildPosterUri(String imageUri, String size) {
        String uri = BASE_IMAGE_URL;
        switch (size.toUpperCase()) {
            case "SMALL":
                uri += SMALL_IMAGE_SIZE;
                break;
            case "BIG":
                uri += BIG_IMAGE_SIZE;
                break;
            default:
                uri += DEFAULT_IMAGE_SIZE;
        }
        uri += imageUri;
        return Uri.parse(uri).buildUpon()
                .build();
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
