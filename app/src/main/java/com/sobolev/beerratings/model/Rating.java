package com.sobolev.beerratings.model;

public class Rating {
    public String title;
    public String description;
    public String imageUri;
    public Double rating;
    public String date;

    public Rating() {
    }

    public Rating(String title, String description, String imageUri, Double rating, String Date) {
        this.title = title;
        this.description = description;
        this.imageUri = imageUri;
        this.rating = rating;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", rating=" + rating +
                ", date='" + date + '\'' +
                '}';
    }
}
