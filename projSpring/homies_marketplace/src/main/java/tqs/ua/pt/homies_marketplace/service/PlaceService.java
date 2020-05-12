package tqs.ua.pt.homies_marketplace.service;

import tqs.ua.pt.homies_marketplace.models.Place;

import java.util.List;

public interface PlaceService {

    List<Place> searchByCity(String city);
    List<Place> getPublishedHouses(String email);
    Place save(Place place);
    List<Place> getAllPlaces();


}
