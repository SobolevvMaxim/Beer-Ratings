package com.sobolev.beerratings.domain.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Rating {
    public String title;
    public String description;
    public String imageUrl;
    public Double rating;
    public String date;
    public Double users_rating;
    public Boolean addedToBookmarks = false;

    public Rating() {
    }

    public Rating(String title, String description, String imageUrl, Double rating, String date, Double users_rating) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.date = date;
        this.users_rating = users_rating;
    }

    @NonNull
    @Override
    public String toString() {
        return "Rating{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUri='" + imageUrl + '\'' +
                ", rating=" + rating +
                ", date='" + date + '\'' +
                ", users_rating=" + users_rating +
                '}';
    }
}
