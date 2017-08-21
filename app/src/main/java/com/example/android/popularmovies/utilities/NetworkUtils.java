package com.example.android.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public final static String API_BASE_URL = "https://api.themoviedb.org/3/movie/";

    public final static String API_ENDPOINT_TOPRATED = "top_rated";
    public final static String API_ENDPOINT_POPULAR = "popular";

    public final static String API_KEY_PARAM = "api_key";
    public final static String API_KEY = "INSERT_YOUR_API_KEY_HERE";

    public final static String API_LANGUAGE_PARAM = "language";
    public final static String API_LANGUAGE = "de-DE";

    public final static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    public final static String DEFAULT_IMAGE_SIZE = "w185";
    public final static String SMALL_IMAGE_SIZE = "w92";
    public final static String BIG_IMAGE_SIZE = "w500";

    public static final String SORTING_TOP_RATED = "TOPRATED";
    public static final String SORTING_POPULAR = "POPULAR";

    public static URL buildUrl(String sorting) {
        String endpoint;
        if (sorting.toUpperCase().equals(SORTING_TOP_RATED)) {
            endpoint = API_ENDPOINT_TOPRATED;
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
            endpoint = API_ENDPOINT_TOPRATED;
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
