package com.example.asus.subthreemvvm.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

@Entity(tableName = "favorite_movie")
public class MovieModelDb implements Parcelable {

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    /** The name of the name column. */
    public static final String COLUMN_NAME = "name";

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "vote_average")
    private double voteAverage;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.overview);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeDouble(this.voteAverage);
        dest.writeInt(this.id);
    }

    public MovieModelDb() {
    }

    protected MovieModelDb(Parcel in) {
        this.overview = in.readString();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.voteAverage = in.readDouble();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<MovieModelDb> CREATOR = new Parcelable.Creator<MovieModelDb>() {
        @Override
        public MovieModelDb createFromParcel(Parcel source) {
            return new MovieModelDb(source);
        }

        @Override
        public MovieModelDb[] newArray(int size) {
            return new MovieModelDb[size];
        }
    };

    public static MovieModelDb fromContentValues(ContentValues values) {
        final MovieModelDb movie = new MovieModelDb();
        if (values.containsKey(COLUMN_ID)) {
            movie.id = values.getAsInteger(COLUMN_ID);
        }
        if (values.containsKey(COLUMN_NAME)) {
            movie.title = values.getAsString(COLUMN_NAME);
        }
        return movie;
    }

}
