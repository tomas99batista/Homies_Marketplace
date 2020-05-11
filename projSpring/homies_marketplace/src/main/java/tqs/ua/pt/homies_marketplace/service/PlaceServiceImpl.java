package tqs.ua.pt.homies_marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class PlaceServiceImpl implements PlaceService{

    @Autowired
    private PlaceRepository placeRepository;


    @Override
    public Place save(Place place) {
        return placeRepository.save(place);
    }

    public List<Place> getPublishedHouses(String email){
        return placeRepository.findPublishedHouses(email);
    }

    @Override
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }
}
