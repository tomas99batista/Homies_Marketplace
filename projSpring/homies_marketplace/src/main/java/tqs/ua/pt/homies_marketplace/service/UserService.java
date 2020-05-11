package tqs.ua.pt.homies_marketplace.service;

import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.User;

import java.util.List;

public interface UserService {

    public User getUserByEmail(String email);

    public boolean exists(String email);

    public boolean addPublishedHouse(String email, Place place);

    public User save(User user);

    public List<User> getAllUsers();
}
