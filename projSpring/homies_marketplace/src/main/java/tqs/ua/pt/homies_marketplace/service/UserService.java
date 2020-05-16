package tqs.ua.pt.homies_marketplace.service;

import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.PlaceId;
import tqs.ua.pt.homies_marketplace.models.User;

import java.util.List;

public interface UserService {

    boolean addToFavorites(String email, PlaceId placeId);

    User getUserByEmail(String email);

    boolean exists(String email);

    boolean addPublishedHouse(String email, Place place);

    User save(User user);

    List<User> getAllUsers();
}
