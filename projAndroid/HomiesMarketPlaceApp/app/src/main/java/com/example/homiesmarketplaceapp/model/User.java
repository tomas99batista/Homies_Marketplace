package com.example.homiesmarketplaceapp.model;

import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;
import java.util.List;

public class User {

    @SerializedName("email")
    private String email;

    @SerializedName("favorites")
    private List<Long> favorites;

    @SerializedName("password")
    private String password;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("city")
    private String city;

    @SerializedName("publishedHouses")
    private List<Long> publishedHouses;

    @SerializedName("rentedHouses")
    private List<Long> rentedHouses;

    public User(){

    }


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String firstName, String lastName, String city) {
        this.email = email;
        this.favorites = new ArrayList<>();
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.publishedHouses = new ArrayList<>();
        this.rentedHouses = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public List<Long> getFavorites() {
        return favorites;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public List<Long> getPublishedHouses() {
        return publishedHouses;
    }

    public List<Long> getRentedHouses() {
        return rentedHouses;
    }
}
