package tqs.ua.pt.homies_marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.User;
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;
import tqs.ua.pt.homies_marketplace.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class WebController {
    @Autowired
    PlaceController placeController; // Podemos usar este e chamamos os metodos da API
    @Autowired
    PlaceRepository placeRepository; // OU USAR O REPOSITORY E CHAMAR PELO REPOSITORY

    @Autowired
    UserController userController; // Podemos usar este e chamamos os metodos da API
    @Autowired
    UserRepository userRepository; // OU USAR O REPOSITORY E CHAMAR PELO REPOSITORY

    @RequestMapping(method = GET, value = "/")
    String index(Model model){
        return "index";
    }

    @RequestMapping(method = GET, value = "/register")
    String register(Model model){
        return "register";
    }

    // lisbon page
    @GetMapping("/test")
    String test(Model model){
        model.addAttribute("user", "user");
        model.addAttribute("place", "place");
        return "test";
    }

    @RequestMapping(value = "/places", method = RequestMethod.GET)
    public String places(Model model){
        List<Place> places = placeController.getAllPlaces();
        List<String> cities = placeController.getAllCities();
        model.addAttribute("places", places);
        model.addAttribute("cities", cities);
        return "houseList";
    }

    @GetMapping("/places/{id}")
    public String details(@PathVariable("id") long id, Model model){
        Place place = placeController.getPlaceById(0L);
        model.addAttribute("placeTitle", place.getTitle());
        model.addAttribute("place", place);
        model.addAttribute("placeFeatures", place.getFeatures());

        System.out.println(place.getFeatures());
        return "details";
    }

    @RequestMapping(method = RequestMethod.GET, value="/places/city/{city}")
    public String places_by_city(Model model, @PathVariable("city") String city) {
        //Quando tiver alguma coisa na bd
        //List<Place> placesbycity = placeController.search_by_city(city);
        List<String> cities = placeController.getAllCities();
        System.out.println("City>> " + city);
        List<Place> places = placeController.getAllPlaces();
        System.out.println(cities);
        List<Place> returnPlaces = new ArrayList<>();
        for(Place p: places){
            if(p.getCity().equals(city))
                returnPlaces.add(p);
        }
        System.out.println(returnPlaces);
        model.addAttribute("places", returnPlaces);
        model.addAttribute("cities", cities);
        return "houseList";
    }


    @GetMapping("/profile")
    public String profile(Model model){
        List<User> users = userController.getAllUsers();
        model.addAttribute("users",users);
        return "profile";
    }

}
