package com.example.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private static final int NUM_IMBD_RATING_STARS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ImageView imageView = (ImageView) findViewById(R.id.iv_movie_poster);
        TextView titleView = (TextView) findViewById(R.id.tv_title);
        TextView originalTitleView = (TextView) findViewById(R.id.tv_original_title);
        TextView originalLanguageView = (TextView) findViewById(R.id.tv_original_language);
        RatingBar userRatingBar = (RatingBar) findViewById(R.id.rb_user_rating);
        userRatingBar.setNumStars(NUM_IMBD_RATING_STARS);
        TextView releaseDateView = (TextView) findViewById(R.id.tv_release_date);
        TextView plotSynopsisView = (TextView) findViewById(R.id.tv_plot_synopsis);

        Intent receivedIntent = getIntent();

        if (receivedIntent != null) {
            if (receivedIntent.hasExtra("movie")) {
                Movie selectedMovie = receivedIntent.getParcelableExtra("movie");

                try {
                    String posterUri = selectedMovie.getPosterUri();
                    Uri imageUrl = NetworkUtils.buildPosterUri(posterUri, "big");
                    Picasso.with(this).load(imageUrl).into(imageView);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                titleView.setText(selectedMovie.getTitle());
                originalTitleView.setText(selectedMovie.getOriginalTitle());
                originalLanguageView.setText(selectedMovie.getOriginalLanguage().toUpperCase());
                userRatingBar.setRating(selectedMovie.getUserRating().floatValue());
                Date releaseDate = selectedMovie.getReleaseDate();
                String formattedDate = new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(releaseDate);
                releaseDateView.setText(formattedDate);
                plotSynopsisView.setText(selectedMovie.getPlotSynopsis());
            } else
                Log.d(TAG, "No movie was set in the intent.");
        } else
            Log.d(TAG, "Activity called without an intent.");
    }
}
