package tqs.ua.pt.homies_marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.ua.pt.homies_marketplace.dtos.PlaceDTO;
import tqs.ua.pt.homies_marketplace.dtos.ReviewDTO;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.Review;
import tqs.ua.pt.homies_marketplace.service.PlaceService;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PlaceController {

    @Autowired
    private PlaceService placeService;


    @GetMapping("/places/{id:[0-9]+}/reviews")
    public List<Review> getReviews(@PathVariable("id") long id){
        return placeService.getReviews(id);
    }

    @PostMapping("/places/{id}/reviews")
    public boolean createReview(@PathVariable("id") long id,@RequestBody ReviewDTO reviewDTO){
        Review review= new Review(reviewDTO);
        return placeService.addReview(id, review);
    }

    @PostMapping("/places")
    public ResponseEntity<Place> createPlace(@RequestBody PlaceDTO placeDTO){
        HttpStatus status=HttpStatus.CREATED;
        Place place= new Place(placeDTO);
        Place saved=placeService.save(place);
        return new ResponseEntity<>(saved, status);
    }

    //get all places
    @GetMapping("/places")
    public List<Place> getAllPlaces() {
        return placeService.getAllPlaces();
    }


    @RequestMapping(value="/places/{identifier:[0-9]+}", method=RequestMethod.GET)
    public Place getPlaceById(@PathVariable("identifier") Long identifier) {
        return placeService.getPlaceById(identifier);
    }

    public List<String> getAllCities() {
        List<String> cities = new ArrayList<>();
        cities.add("Aveiro");
        cities.add("Viseu");
        cities.add("Porto");
        cities.add("Lisboa");
        cities.add("Vila Real");
        cities.add("Guarda");
        cities.add("Braga");
        cities.add("Bragança");
        cities.add("Portalegre");
        cities.add("Leiria");
        cities.add("Évora");
        return cities;
    }
  @GetMapping("/search")
    public List<Place> search(@RequestParam(value = "city", required = false) String city, @RequestParam(value="price", required = false ) String price, @RequestParam(value="rating", required = false ) String rating, @RequestParam(value = "bedrooms", required = false) String bedrooms, @RequestParam(value = "bathrooms", required = false) String bathrooms, @RequestParam(value = "type", required = false) String type, @RequestParam(value = "minPrice", required = false) String minPrice, @RequestParam(value = "maxPrice", required = false) String maxPrice)  {
        PlaceDTO placeDTO= new PlaceDTO(city, price, rating, bedrooms, bathrooms, type);
        return placeService.search(placeDTO, minPrice, maxPrice);
    }
}
