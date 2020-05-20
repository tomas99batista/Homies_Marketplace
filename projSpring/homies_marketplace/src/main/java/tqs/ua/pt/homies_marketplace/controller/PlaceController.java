package tqs.ua.pt.homies_marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.service.PlaceService;

import java.util.List;

@Controller
public class PlaceController {

    @Autowired
    private PlaceService placeService;


    @PostMapping("/places")
    public Place createPlace(@RequestBody Place place){
        return placeService.save(place);
    }
    // get all places
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

    @GetMapping("/search")
    public List<Place> search(@RequestParam(value = "city", required = false) String city){
        return placeService.searchByCity(city);
    }


}
