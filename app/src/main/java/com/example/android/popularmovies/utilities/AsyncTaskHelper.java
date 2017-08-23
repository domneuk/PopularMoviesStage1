package com.example.android.popularmovies.utilities;

public class AsyncTaskHelper {

    public interface AsyncTaskCompleteListener<T> {
        public void onTaskComplete(T result);
    }

}
