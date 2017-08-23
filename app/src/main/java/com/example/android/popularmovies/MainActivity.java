package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.utilities.AsyncTaskHelper;
import com.example.android.popularmovies.utilities.FetchMovieDataTask;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private GridView mMovieOverview;
    private MovieAdapter mMovieAdapter;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    private MenuItem mActionSortPopular;
    private MenuItem mActionSortTopRated;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", (ArrayList<Movie>) mMovieAdapter.getMovieData());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieOverview = (GridView) findViewById(R.id.gv_movie_overview);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            mMovieAdapter = new MovieAdapter(this, null);
        } else {
            List<Movie> movies = savedInstanceState.getParcelableArrayList("movies");
            mMovieAdapter = new MovieAdapter(this, movies);
        }

        mMovieOverview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Movie selectedMovie = mMovieAdapter.getItem(i);

                if (selectedMovie != null) {
                    Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                    intent.putExtra("movie", selectedMovie);

                    // sendIntent with parcable as extra
                    startActivity(intent);
                } else
                    Log.d(TAG, "Could not get movie from list!");
            }
        });

        loadMovieData(NetworkUtils.SORTING_POPULAR); // Initially get all popular movies
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main_actions, menu);

        mActionSortPopular = menu.findItem(R.id.action_sort_popular);
        mActionSortTopRated = menu.findItem(R.id.action_sort_toprated);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        item.setChecked(true);
        item.setEnabled(false);

        switch (item.getItemId()) {
            case R.id.action_sort_toprated:
                mActionSortPopular.setEnabled(true);
                mActionSortPopular.setChecked(false);
                mMovieAdapter.setMovieData(null);
                loadMovieData(NetworkUtils.SORTING_TOP_RATED);
                return true;
            case R.id.action_sort_popular:
                mActionSortTopRated.setEnabled(true);
                mActionSortTopRated.setChecked(false);
                mMovieAdapter.setMovieData(null);
                loadMovieData(NetworkUtils.SORTING_POPULAR);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public class FetchMovieDataTaskCompleteListener implements AsyncTaskHelper.AsyncTaskCompleteListener<List<Movie>> {
        @Override
        public void onTaskComplete(List<Movie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                mMovieAdapter.setMovieData(movies);
                mMovieOverview.setAdapter(mMovieAdapter);
                showGridView();
            } else {
                showErrorMessage();
            }
        }
    }

    private void loadMovieData(String sort) {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        showGridView();
        new FetchMovieDataTask(this, new FetchMovieDataTaskCompleteListener()).execute(sort);
    }

    private void showGridView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mMovieOverview.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mMovieOverview.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
