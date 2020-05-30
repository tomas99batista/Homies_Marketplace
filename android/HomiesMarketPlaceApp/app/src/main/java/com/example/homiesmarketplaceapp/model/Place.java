package com.example.homiesmarketplaceapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Place {

    @SerializedName("id")
    private Long id;

    @SerializedName("title")
    private String title;

    @SerializedName("price")
    private double price;

    @SerializedName("rating")
    private double rating;

    @SerializedName("features")
    private List<String> features;

    @SerializedName("numberBathrooms")
    private int numberBathrooms;

    @SerializedName("numberBedrooms")
    private int numberBedrooms;

    @SerializedName("type")
    private String type;

    @SerializedName("city")
    private String city;

    @SerializedName("reviews")
    private List<Long> reviews;

    @SerializedName("photos")
    private String photos;

    public Place(){

    }

    public Place(Long id, String title, double price, double rating, List<String> features, int numberBathrooms, int numberBedrooms, String type, String city, List<Long> reviews, String photos) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.rating = rating;
        this.features = features;
        this.numberBathrooms = numberBathrooms;
        this.numberBedrooms = numberBedrooms;
        this.type = type;
        this.city = city;
        this.reviews = reviews;
        this.photos = photos;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public List<String> getFeatures() {
        return features;
    }

    public int getNumberBathrooms() {
        return numberBathrooms;
    }

    public int getNumberBedrooms() {
        return numberBedrooms;
    }

    public String getType() {
        return type;
    }

    public String getCity() {
        return city;
    }

    public List<Long> getReviews() {
        return reviews;
    }

    public String getPhotos() {
        return photos;
    }


    public String getAllFeatures(){
        String strFeatures="";
        for (String s: features){
            strFeatures+=s+ "\n";
        }
        return strFeatures;
    }
}
