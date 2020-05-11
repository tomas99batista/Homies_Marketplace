package tqs.ua.pt.homies_marketplace.service;

import org.springframework.stereotype.Service;
import tqs.ua.pt.homies_marketplace.models.Place;

import javax.transaction.Transactional;
import java.util.List;

public interface PlaceService {

    public List<Place> getPublishedHouses(String email);
    public Place save(Place place);
    public List<Place> getAllPlaces();


}
