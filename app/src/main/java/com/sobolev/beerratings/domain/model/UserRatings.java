package com.sobolev.beerratings.domain.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class UserRatings {
    public ArrayList<UserRating> user_ratings;

    public UserRatings() {
    }

    public UserRatings(ArrayList<UserRating> user_ratings) {
        this.user_ratings = user_ratings;
    }
}
