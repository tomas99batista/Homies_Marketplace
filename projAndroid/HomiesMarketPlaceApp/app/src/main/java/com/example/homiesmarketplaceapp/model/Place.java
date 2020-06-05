package com.example.homiesmarketplaceapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
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

    public Place(String title, double price, List<String> features, int numberBathrooms, int numberBedrooms, String type, String city, String photos) {
        this.title = title;
        this.price = price;
        this.rating = 0.0;
        this.features = features;
        this.numberBathrooms = numberBathrooms;
        this.numberBedrooms = numberBedrooms;
        this.type = type;
        this.city = city;
        this.reviews = new ArrayList<>();

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
