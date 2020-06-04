package tqs.ua.pt.homies_marketplace.models;

import tqs.ua.pt.homies_marketplace.dtos.PlaceDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="place")
@Table(name="place")
public class Place {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false, unique=false)
    private String title;

    @Column(nullable=false, unique=false)
    private double price;

    @Column(nullable=false, unique=false)
    private double rating;

    @ElementCollection
    private List<String> features;

    @Column(nullable=false, unique=false)
    private int numberBathrooms;

    @Column(nullable=false, unique=false)
    private int numberBedrooms;

    @Column(nullable=false, unique=false)
    private String type;

    @Column(nullable=false, unique=false)
    private String city;

    @ElementCollection
    private List<Long> reviews;

    @Column(nullable=true, unique=false)
    private String photos;

    //needed for hibernate
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
        reviews = new ArrayList<>();
        this.photos = photos;
    }


    public Place(PlaceDTO placeDTO){
        this.title = placeDTO.getTitle();
        this.price = placeDTO.getPrice();
        this.rating = placeDTO.getRating();
        this.features = placeDTO.getFeatures();
        this.numberBathrooms = placeDTO.getNumberBathrooms();
        this.numberBedrooms = placeDTO.getNumberBedrooms();
        this.type = placeDTO.getType();
        this.city = placeDTO.getCity();
        this.reviews = placeDTO.getReviews();
        this.photos = placeDTO.getPhotos();
    }
    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumberBathrooms(int numberBathrooms) {
        this.numberBathrooms = numberBathrooms;
    }

    public void setNumberBedrooms(int numberBedrooms) {
        this.numberBedrooms = numberBedrooms;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setReviews(List<Long> reviews) {
        this.reviews = reviews;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                ", features=" + features +
                ", numberBathrooms=" + numberBathrooms +
                ", numberBedrooms=" + numberBedrooms +
                ", type='" + type + '\'' +
                ", city='" + city + '\'' +
                ", reviews=" + reviews +
                ", photos='" + photos + '\'' +
                '}';
    }
}
