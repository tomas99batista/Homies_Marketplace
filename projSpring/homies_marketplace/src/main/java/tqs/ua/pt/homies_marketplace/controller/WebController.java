package tqs.ua.pt.homies_marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tqs.ua.pt.homies_marketplace.form.LoginRegistrationForm;
import tqs.ua.pt.homies_marketplace.form.UserRegistrationForm;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;
import tqs.ua.pt.homies_marketplace.repository.UserRepository;
import tqs.ua.pt.homies_marketplace.service.UserService;

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
    UserService userService;

    @Autowired
    UserController userController; // Podemos usar este e chamamos os metodos da API
    @Autowired
    UserRepository userRepository; // OU USAR O REPOSITORY E CHAMAR PELO REPOSITORY
    /*
    @RequestMapping(method = GET, value = "/")
    String index(Model model){
        return "index";
    }
     */
    @RequestMapping(method = GET, value = "/register")
    String register(Model model){
        model.addAttribute("user", new UserRegistrationForm());
        return "register";
    }


    @PostMapping("/register")
    String registerSubmit(@ModelAttribute UserRegistrationForm userRegistrationForm){
        System.out.println("all users: " + userController.getAllUsers());
        if (userController.getUserByEmail(userRegistrationForm.getEmail()) == null){
            User user = new User();
            user.setEmail(userRegistrationForm.getEmail());
            user.setFirstName(userRegistrationForm.getFirstName());
            user.setLastName(userRegistrationForm.getLastName());
            user.setPassword(userRegistrationForm.getPassword());
            user.setCity(userRegistrationForm.getCity());
            System.out.println("new user: " + user);
            userService.save(user);
            return "index";
        } else {
            System.out.println("User already picked");
            return "user_picked";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    String login(Model model){
        model.addAttribute("user", new LoginRegistrationForm());
        return "login";
    }

    @PostMapping("/login")
    String loginSubmit(@ModelAttribute LoginRegistrationForm loginRegistrationForm, Model model){
        System.out.println("login - all users: " + userController.getAllUsers());
        if (userController.getUserByEmail(loginRegistrationForm.getEmail()) != null){
            if (userController.getUserByEmail(loginRegistrationForm.getEmail()).getPassword().equals(loginRegistrationForm.getPassword())){
                User user = userController.getUserByEmail(loginRegistrationForm.getEmail());
                System.out.println("logged user: " + user);
                model.addAttribute("user_logged", user);
                return "index";
            } else {
                System.out.println("wrong password");
                return "failed_combination";
            }

        } else {
            System.out.println("User not registered");
            return "user_not_registered";
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String index(Model model){
        model.addAttribute("user", "user");
        model.addAttribute("place", "place");
        return "test";
    }

    @RequestMapping(value = "/places", method = RequestMethod.GET)
    public String places(Model model){
        List<String> cities = placeController.getAllCities();
        List<Place> places = placeController.getAllPlaces();
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
        System.out.println("City>> " + city);
        List<String> cities = placeController.getAllCities();
        List<Place> places = placeController.getAllPlaces();
        List<Place> returnPlaces = new ArrayList<>();
        for(Place p: places){
            if(p.getCity().equals(city))
                returnPlaces.add(p);
        }
        System.out.println(returnPlaces);
        model.addAttribute("places", returnPlaces);
        model.addAttribute("cities",cities);
        return "houseList";
    }


    @GetMapping("/profile")
    public String profile(Model model){
        List<User> users = userController.getAllUsers();
        model.addAttribute("users",users);
        return "profile";
    }

}