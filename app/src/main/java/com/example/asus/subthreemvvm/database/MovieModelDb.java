package com.example.asus.subthreemvvm.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "favorite_movie")
public class MovieModelDb implements Parcelable {

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
}
