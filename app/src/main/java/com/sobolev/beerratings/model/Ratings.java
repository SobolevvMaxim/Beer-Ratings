package com.sobolev.beerratings.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Ratings {
    public ArrayList<Rating> ratings;

    public Ratings() {
    }

    public Ratings(ArrayList<Rating> ratings) {
        this.ratings = ratings;
    }

    @NonNull
    @Override
    public String toString() {
        return "Ratings{" +
                "ratings=" + ratings +
                '}';
    }
}
