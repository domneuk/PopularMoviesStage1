package com.example.android.popularmovies;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private List<Movie> mMovieList;

    public MovieAdapter(Activity context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public int getCount() {
        return mMovieList == null ? 0 : mMovieList.size();
    }

    @Nullable
    @Override
    public Movie getItem(int position) {
        if (mMovieList != null) return mMovieList.get(position);
        return super.getItem(position);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Movie movie = getItem(i);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.movie_overview_item, viewGroup, false);
        }

        ImageView posterView = view.findViewById(R.id.iv_movie_poster);
        try {
            String posterUri = movie.getPosterUri();
            Uri imageUrl = NetworkUtils.buildPosterUri(posterUri);
            Picasso.with(getContext()).load(imageUrl).into(posterView);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return view;

    }

    public void setMovieData(List<Movie> movies) {
        mMovieList = movies;
        notifyDataSetChanged();
    }

    public List<Movie> getMovieData() {
        return mMovieList;
    }
}
