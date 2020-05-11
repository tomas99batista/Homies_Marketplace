package tqs.ua.pt.homies_marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.User;
import tqs.ua.pt.homies_marketplace.repository.UserRepository;
import tqs.ua.pt.homies_marketplace.service.PlaceService;
import tqs.ua.pt.homies_marketplace.service.UserService;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlaceService placeService;

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
    public User createUser(@RequestBody User user){
        return userService.save(user);
    }
    // get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


}
