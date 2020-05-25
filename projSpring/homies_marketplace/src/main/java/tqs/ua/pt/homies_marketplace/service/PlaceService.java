package tqs.ua.pt.homies_marketplace.service;

import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.Review;

import java.util.List;

public interface PlaceService {

    List<Place> searchByCityAndPrice(String city, String minPrice, String maxPrice);
    List<Place> searchByPrice(String minPrice, String maxPrice);
    List<Review> getReviews(long placeId);
    boolean addReview(long placeId, Review review);
    List<Place> getFavoriteHouses(String email);
    Place getPlaceById(long id);
    List<Place> searchByCity(String city);
    List<Place> getPublishedHouses(String email);
    Place save(Place place);
    List<Place> getAllPlaces();


}
