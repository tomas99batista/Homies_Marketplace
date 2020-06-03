package tqs.ua.pt.homies_marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.ua.pt.homies_marketplace.dtos.PlaceDTO;
import tqs.ua.pt.homies_marketplace.dtos.UserDTO;
import tqs.ua.pt.homies_marketplace.models.Booking;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.PlaceId;
import tqs.ua.pt.homies_marketplace.models.User;
import tqs.ua.pt.homies_marketplace.service.BookService;
import tqs.ua.pt.homies_marketplace.service.PlaceService;
import tqs.ua.pt.homies_marketplace.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private BookService bookService;


    @PostMapping("/users/{email}/booking")
    public String addToRentedHouses(@PathVariable("email") String email, @RequestBody PlaceId placeId){
        boolean saved= userService.addToRentedHouses(email, placeId);
        if (saved){
            return "{" + "\"success\""+":"+ true + "}";
        }
        return "{" + "\"success\""+":"+ false + "}";
    }
    @PostMapping("/users/{email}/favorites")
    public String addToFavorites(@PathVariable("email") String email, @RequestBody PlaceId placeId){
        boolean saved= userService.addToFavorites(email, placeId);
        if (saved){
            return "{" + "\"success\""+":"+ true + "}";
        }
        return "{" + "\"success\""+":"+ false + "}";
    }

    @GetMapping("/users/{email}/favorites")
    public List<Place> getUserFavorites(@PathVariable("email") String email){
        return placeService.getFavoriteHouses(email);
    }

    //publish new house
    @PostMapping("/users/{email}/publishedHouses")
    public String addPublishedHouse(@PathVariable("email") String email, @RequestBody PlaceDTO placeDTO){
        Place place= new Place(placeDTO);
        boolean saved= userService.addPublishedHouse(email, place);
        if (saved){
            return "{" + "\"success\""+":"+ true + "}";
        }
        return "{" + "\"success\""+":"+ false + "}";
    }

    //get publishedHouses
    @GetMapping("/users/{email}/publishedHouses")
    public List<Place> getUserPublishedHouses(@PathVariable("email") String email){
        return placeService.getPublishedHouses(email);
    }


    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        HttpStatus status=HttpStatus.CREATED;
        User user= new User(userDTO);
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

    @PostMapping("/login")
    public User login(@RequestBody UserDTO userDTO){
        if (userService.exists(userDTO.getEmail())){
            User user= userService.getUserByEmail(userDTO.getEmail());
            if (user.getPassword().equals(userDTO.getPassword())){
                return user;
            }
            return null;
        }
        return null;
    }

    @DeleteMapping("/users/{email}/favorites")
    public String deleteFavoritePlace(@PathVariable("email") String email, @RequestBody PlaceId placeId){
        boolean saved= userService.removeFavoritePlace(email, placeId);
        if (saved){
            return "{" + "\"success\""+":"+ true + "}";
        }
        return "{" + "\"success\""+":"+ false + "}";
    }

    @DeleteMapping("/users/{email}/booking")
    public String cancelRentedHouse(@PathVariable("email") String email, @RequestBody PlaceId placeId){
        Booking booking = bookService.getBooking(placeId.getId());

        // Delete from the owner rented_houses
        String requesterEmail = booking.getRequester();
        User requester = userService.getUserByEmail(requesterEmail);
        List<Long> rentedHouses = requester.getRentedHouses();
        rentedHouses.remove(placeId.getId());
        requester.setRentedHouses(rentedHouses);

        // Delete from booking
        bookService.deleteBooking(booking);
        return "{" + "\"success\""+":"+ true + "}";

    }

    @GetMapping("/users/{email}/occupiedHouses")
    public List<Place> getRentedHousesByUser(@PathVariable("email") String email){
        ArrayList<Booking> allByOwner = bookService.getAllBookingsByEmail(email);
        ArrayList<Place> places = new ArrayList<>();
        for (Booking book : allByOwner) {
            places.add(placeService.getPlaceById(book.getPlaceId()));
        }
        return places;
    }
}
