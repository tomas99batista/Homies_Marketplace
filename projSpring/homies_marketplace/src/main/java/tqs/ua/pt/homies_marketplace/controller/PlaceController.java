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

import java.util.List;

@Controller
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
    /*@GetMapping("/places")
    public List<Place> getAllPlaces() {
        return placeService.getAllPlaces();

    }*/

    @RequestMapping(method = RequestMethod.GET, value="/places")
    public String getAllplaces(Model model){
        List<Place> places = placeService.getAllPlaces();
        model.addAttribute("places", places);
        return "houseList";
    }

    @GetMapping("/places/{id}")
    public String getPlaceDetails(@PathVariable("id") long id, Model model){
        Place place = placeService.getPlaceById(0L);
        model.addAttribute("placeTitle", place.getTitle());
        model.addAttribute("placeFeatures", place.getFeatures());
        System.out.println(place.getFeatures());
        return "details";
    }

    @RequestMapping(method = RequestMethod.GET, value="/place/city/{city}")
    public String getPlacebyCity(Model model, @PathVariable String city) {
        System.out.println("City>> " + city);
        model.addAttribute("city", city);
        return "houseList";
    }


    @GetMapping("/search")
    public List<Place> search(@RequestParam(value = "city", required = false) String city){
        return placeService.searchByCity(city);
    }




}
