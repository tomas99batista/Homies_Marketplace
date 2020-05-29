package tqs.ua.pt.homies_marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tqs.ua.pt.homies_marketplace.form.LoginRegistrationForm;
import tqs.ua.pt.homies_marketplace.form.UserRegistrationForm;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.User;
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;
import tqs.ua.pt.homies_marketplace.repository.UserRepository;
import tqs.ua.pt.homies_marketplace.service.PlaceService;
import tqs.ua.pt.homies_marketplace.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {
    @Autowired
    public static String user_status = "user_not_logged";

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceController placeController;

    @Autowired
    UserController userController;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    String index(Model model){
        model.addAttribute("user_status",user_status);
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    String register(Model model){
        model.addAttribute("user", new UserRegistrationForm());
        model.addAttribute("user_status",user_status);
        return "register";
    }

    @PostMapping("/register")
    String registerSubmit(@ModelAttribute UserRegistrationForm userRegistrationForm, Model model){
        System.out.println("all users: " + userService.getAllUsers());
        if (userService.getUserByEmail(userRegistrationForm.getEmail()) == null){
            User user = new User();
            user.setEmail(userRegistrationForm.getEmail());
            user.setFirstName(userRegistrationForm.getFirstName());
            user.setLastName(userRegistrationForm.getLastName());
            user.setPassword(userRegistrationForm.getPassword());
            user.setCity(userRegistrationForm.getCity());
            System.out.println("new user: " + user);
            userService.save(user);
            user_status = "user_logged";
            model.addAttribute("user_status",user_status);
            return "index";
        } else {
            System.out.println("User already picked");
            return "user_picked";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    String login(Model model){
        model.addAttribute("user", new LoginRegistrationForm());
        model.addAttribute("user_status",user_status);
        return "login";
    }

    @PostMapping("/login")
    String loginSubmit(@ModelAttribute LoginRegistrationForm loginRegistrationForm, Model model){
        System.out.println("login - all users: " + userController.getAllUsers());
        if (userController.getUserByEmail(loginRegistrationForm.getEmail()) != null){
            if (userController.getUserByEmail(loginRegistrationForm.getEmail()).getPassword().equals(loginRegistrationForm.getPassword())){
                User user = userController.getUserByEmail(loginRegistrationForm.getEmail());
                System.out.println("logged user: " + user);
                user_status = "user_logged";
                model.addAttribute("user_status",user_status);
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
    String test(Model model){
        model.addAttribute("user", "user");
        model.addAttribute("place", "place");
        model.addAttribute("user_status",user_status);
        return "test";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String places(Model model){
        //cities
        List<String> cities = placeController.getAllCities();

        List<Place> places = placeService.getAllPlaces();
        model.addAttribute("places", places);
        model.addAttribute("cities", cities);
        model.addAttribute("user_status",user_status);
        return "houseList";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String filter_places(Model model, @ModelAttribute String city){
        //cities

        System.out.println(city);

        List<String> cities = placeController.getAllCities();

        List<Place> places = placeService.getAllPlaces();
        model.addAttribute("places", places);
        model.addAttribute("cities", cities);
        model.addAttribute("user_status",user_status);
        return "houseList";
    }

    @GetMapping("/list/{id}")
    public String details(@PathVariable("id") long id, Model model){
        Place place = placeService.getPlaceById(0L);
        List<String> cities = placeController.getAllCities();
        model.addAttribute("cities", cities);
        model.addAttribute("placeTitle", place.getTitle());
        model.addAttribute("place", place);
        model.addAttribute("placeFeatures", place.getFeatures());
        model.addAttribute("user_status",user_status);
        System.out.println(place.getFeatures());
        return "details";
    }

    @RequestMapping(method = RequestMethod.GET, value="/list/city/{city}")
    public String places_by_city(Model model, @PathVariable("city") String city) {
        //Quando tiver alguma coisa na bd
        //List<Place> placesbycity = placeController.search_by_city(city);
        System.out.println("City>> " + city);
        List<Place> places = placeService.getAllPlaces();
        List<Place> returnPlaces = new ArrayList<>();
        for(Place p: places){
            if(p.getCity().equals(city))
                returnPlaces.add(p);
        }
        System.out.println(returnPlaces);
        List<String> cities = placeController.getAllCities();
        model.addAttribute("cities", cities);
        model.addAttribute("places", returnPlaces);
        model.addAttribute("user_status",user_status);
        return "houseList";
    }


    @GetMapping("/profile")
    public String profile(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users",users);
        model.addAttribute("user_status",user_status);
        return "profile";
    }

    public String getUser_status(){
        System.out.println("USER_STATUS: " + user_status);
        return user_status;
    }

}