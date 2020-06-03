package com.example.homiesmarketplaceapp.model;

import com.google.gson.annotations.SerializedName;

<<<<<<< HEAD
=======
import java.util.ArrayList;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
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

<<<<<<< HEAD
    public User(String email, List<Long> favorites, String password, String firstName, String lastName, String city, List<Long> publishedHouses, List<Long> rentedHouses) {
        this.email = email;
        this.favorites = favorites;
=======
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String firstName, String lastName, String city) {
        this.email = email;
        this.favorites = new ArrayList<>();
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
<<<<<<< HEAD
        this.publishedHouses = publishedHouses;
        this.rentedHouses = rentedHouses;
=======
        this.publishedHouses = new ArrayList<>();
        this.rentedHouses = new ArrayList<>();
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
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
