package tqs.ua.pt.homies_marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.Review;
import tqs.ua.pt.homies_marketplace.service.PlaceService;

import java.util.List;

@RestController
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
    public List<Place> search(@RequestParam(value = "city", required = false) String city, @RequestParam(value="price", required = false ) String price, @RequestParam(value="rating", required = false ) String rating, @RequestParam(value = "bedrooms", required = false) String bedrooms, @RequestParam(value = "bathrooms", required = false) String bathrooms, @RequestParam(value = "type", required = false) String type, @RequestParam(value = "minPrice", required = false) String minPrice, @RequestParam(value = "maxPrice", required = false) String maxPrice)  {
        return placeService.search(city, price, rating, bedrooms, bathrooms, type, minPrice, maxPrice);

    }
}
