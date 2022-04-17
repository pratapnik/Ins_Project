package com.odroid.inspro.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity(tableName = "all_movies")
public class BaseMovie {

    @PrimaryKey
    @ColumnInfo(name = "id")
    public long id;
    @ColumnInfo(name = "movie_title")
    public String title;
    @ColumnInfo(name = "movie_description")
    public String movieDescription;
    @ColumnInfo(name = "release_date")
    public String releaseDate;
    @ColumnInfo(name = "image")
    public String posterUrl;
    @ColumnInfo(name = "rating")
    public float rating;
    @ColumnInfo(name = "rating_count")
    public long ratingCount;
    @ColumnInfo(name = "bookmarked")
    public boolean isBookmarked;
    @ColumnInfo(name = "time_stamp")
    public long timeStamp = System.currentTimeMillis();

    public BaseMovie(long id, String title, String movieDescription, String releaseDate,
                     String posterUrl, float rating, long ratingCount,
                     boolean isBookmarked) {
        this.id = id;
        this.title = title;
        this.movieDescription = movieDescription;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.isBookmarked = isBookmarked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BaseMovie)) return false;

        BaseMovie baseMovie = (BaseMovie) o;

        return new EqualsBuilder()
                .append(id, baseMovie.id)
                .append(title, baseMovie.title).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(title).toHashCode();
    }
}