package tqs.ua.pt.homies_marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;

import java.util.List;

@RestController
public class PlaceController {
    @Autowired
    PlaceRepository placeRepository;

    // get all places
    @GetMapping("/places")
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }
}
