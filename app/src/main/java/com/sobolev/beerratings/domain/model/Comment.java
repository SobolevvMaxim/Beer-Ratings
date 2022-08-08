package com.sobolev.beerratings.domain.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Comment {
    public String username;
    public String comment;
    public Double user_rating;

    public Comment() {
    }

    public Comment(String username, String comment, Double user_rating) {
        this.username = username;
        this.comment = comment;
        this.user_rating = user_rating;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "username='" + username + '\'' +
                ", comment='" + comment + '\'' +
                ", user_rating=" + user_rating +
                '}';
    }
}
