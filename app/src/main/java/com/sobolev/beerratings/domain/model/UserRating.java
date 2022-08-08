package com.sobolev.beerratings.domain.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserRating {
    public String title;
    public Double average;
    public Long count;

    public UserRating() {
    }

    public UserRating(String title, Double average, Long count) {
        this.title = title;
        this.average = average;
        this.count = count;
    }
}
