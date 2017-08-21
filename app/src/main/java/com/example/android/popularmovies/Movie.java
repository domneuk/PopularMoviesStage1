package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Movie implements Parcelable {
    private long id;

    private String title;
    private String originalTitle;
    private String posterUri;
    private String plotSynopsis;
    private String originalLanguage;
    private Double userRating;
    private Date releaseDate;

    static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {

        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(long id, String title, String originalTitle, String posterUri, String plotSynopsis, String originalLanguage, Double userRating, Date releaseDate) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.posterUri = posterUri;
        this.plotSynopsis = plotSynopsis;
        this.originalLanguage = originalLanguage;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    public Movie(JSONObject movie){
        try {
            this.id = movie.getLong("id");
            this.title = movie.getString("title");
            this.originalTitle = movie.getString("original_title");
            this.posterUri = movie.getString("poster_path");
            this.plotSynopsis = movie.getString("overview");
            this.originalLanguage = movie.getString("original_language");
            this.userRating = movie.getDouble("vote_average");
            this.releaseDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(movie.getString("release_date"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Movie(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.originalTitle = in.readString();
        this.posterUri = in.readString();
        this.plotSynopsis = in.readString();
        this.originalLanguage = in.readString();
        this.userRating = in.readDouble();
        this.releaseDate = new Date(in.readLong());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.originalTitle);
        parcel.writeString(this.posterUri);
        parcel.writeString(this.plotSynopsis);
        parcel.writeString(this.originalLanguage);
        parcel.writeDouble(this.userRating);
        parcel.writeLong(this.releaseDate.getTime());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUri() {
        return posterUri;
    }

    public void setPosterUri(String posterUri) {
        this.posterUri = posterUri;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public static List<Movie> fromJson(JSONArray movieList) {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieList.length(); i++) {
            try {
                movies.add(new Movie(movieList.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }
}
