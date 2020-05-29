package tqs.ua.pt.homies_marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.PlaceId;
import tqs.ua.pt.homies_marketplace.models.User;
import tqs.ua.pt.homies_marketplace.service.PlaceService;
import tqs.ua.pt.homies_marketplace.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlaceService placeService;

    @PostMapping("/users/{email}/rentedHouses")
    public boolean addToRentedHouses(@PathVariable("email") String email, @RequestBody PlaceId placeId){
        return userService.addToRentedHouses(email, placeId);
    }
    @PostMapping("/users/{email}/favorites")
    public boolean addToFavorites(@PathVariable("email") String email, @RequestBody PlaceId placeId){
        return userService.addToFavorites(email, placeId);
    }

    @GetMapping("/users/{email}/favorites")
    public List<Place> getUserFavorites(@PathVariable("email") String email){
        return placeService.getFavoriteHouses(email);
    }

    //publish new house
    @PostMapping("/users/{email}/publishedHouses")
    public boolean addPublishedHouse(@PathVariable("email") String email, @RequestBody Place place){
        return userService.addPublishedHouse(email, place);
    }

    //get publishedHouses
    @GetMapping("/users/{email}/publishedHouses")
    public List<Place> getUserPublishedHouses(@PathVariable("email") String email){
        return placeService.getPublishedHouses(email);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        HttpStatus status=HttpStatus.CREATED;
        User saved=userService.save(user);
        return new ResponseEntity<>(saved, status);
    }

    // get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // get users by email
    @GetMapping("/users/{email}")
    public User getUserByEmail(@PathVariable("email") String email){
        return userService.getUserByEmail(email);
    }

}
