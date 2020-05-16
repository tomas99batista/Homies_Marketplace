package tqs.ua.pt.homies_marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.service.PlaceService;

import java.util.List;

@RestController
public class PlaceController {

    @Autowired
    private PlaceService placeService;


    @PostMapping("/places")
    public Place createPlace(@RequestBody Place place){
        return placeService.save(place);
    }
    // get all places
    @GetMapping("/places")
    public List<Place> getAllPlaces() {
        return placeService.getAllPlaces();

    }

    @GetMapping("/places/{id}")
    public Place getPlaceDetails(@PathVariable("id") long id){
        return placeService.getPlaceById(id);
    }

    @GetMapping("/search")
    public List<Place> search(@RequestParam(value = "city", required = false) String city){
        return placeService.searchByCity(city);
    }




}
