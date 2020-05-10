package tqs.ua.pt.homies_marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.ua.pt.homies_marketplace.models.User;
import tqs.ua.pt.homies_marketplace.repository.UserRepository;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    // get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
