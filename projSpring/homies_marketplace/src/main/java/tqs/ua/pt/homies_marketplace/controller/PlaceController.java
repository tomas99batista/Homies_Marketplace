package tqs.ua.pt.homies_marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.Review;
import tqs.ua.pt.homies_marketplace.service.PlaceService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/places")
public class PlaceController {

    @Autowired
    private PlaceService placeService;


    @GetMapping("/places/{id}/reviews")
    public List<Review> getReviews(@PathVariable("id") long id){
        return placeService.getReviews(id);
    }

    @PostMapping("/places/{id}/reviews")
    public boolean createReview(@PathVariable("id") long id,@RequestBody Review review){
        return placeService.addReview(id, review);
    }

    @PostMapping("/places")
    public ResponseEntity<Place> createPlace(@RequestBody Place place){
        HttpStatus status=HttpStatus.CREATED;
        Place saved=placeService.save(place);
        return new ResponseEntity<>(saved, status);
    }

    //get all places
    @GetMapping("/places")
    public List<Place> getAllPlaces() {
        return placeService.getAllPlaces();
    }

    @GetMapping("/places/{city}")
    public List<Place> search_by_city(@PathVariable("city") String city){
        return placeService.searchByCity(city);
    }

    @GetMapping("/places/{id}")
    public Place getPlaceById(@PathVariable("id") long id) {
        return placeService.getPlaceById(id);
    }



}
