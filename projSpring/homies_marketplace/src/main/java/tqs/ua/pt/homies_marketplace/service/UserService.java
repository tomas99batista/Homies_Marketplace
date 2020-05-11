package tqs.ua.pt.homies_marketplace.service;

import tqs.ua.pt.homies_marketplace.models.User;

import java.util.List;

public interface UserService {

    public User save(User user);

    public List<User> getAllUsers();
}
