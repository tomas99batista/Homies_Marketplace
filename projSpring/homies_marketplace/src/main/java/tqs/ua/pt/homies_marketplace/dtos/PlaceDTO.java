package tqs.ua.pt.homies_marketplace.dtos;

import java.util.ArrayList;
import java.util.List;

public class PlaceDTO {

    private String title;
    private double price;
    private double rating;
    private List<String> features;
    private int numberBathrooms;
    private int numberBedrooms;
    private String type;
    private String city;
    private List<Long> reviews;
    private String photos;

    public PlaceDTO(){

    }

    public PlaceDTO(String title, double price, List<String> features, int numberBathrooms, int numberBedrooms, String type, String city, String photos) {
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

    public PlaceDTO(String city, String price, String rating, String bedrooms, String bathrooms, String type) {
        this.city=city;
        this.price= price != null ? Double.parseDouble(price) :-1;
        this.rating=rating !=null ? Double.parseDouble(rating): -1;
        this.numberBedrooms=bedrooms!= null ? Integer.parseInt(bedrooms) : -1;
        this.numberBathrooms= bathrooms !=null? Integer.parseInt(bathrooms): -1;
        this.type=type;
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
}
