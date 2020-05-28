package tqs.ua.pt.homies_marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;
import tqs.ua.pt.homies_marketplace.repository.UserRepository;

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

    // lisbon page
    @GetMapping
    public String index(Model model){
        model.addAttribute("user", "user");
        model.addAttribute("place", "place");
        return "index";
    }
}
