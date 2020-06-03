package com.example.homiesmarketplaceapp.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("id")
    private Long id;

    @SerializedName("email")
    public String email;

    @SerializedName("rating")
    public double rating;

    @SerializedName("comment")
    public String comment;

    public Review(){

    }

    public Review(String email, String comment, double rating) {
        this.email = email;
        this.comment = comment;
        this.rating=rating;
    }

<<<<<<< HEAD
    public Review(Long id, String email, double rating, String comment) {
        this.id = id;
        this.email = email;
        this.rating = rating;
        this.comment = comment;
    }
=======
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
