package tqs.ua.pt.homies_marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(method = RequestMethod.GET, value = "/")
    String index(Model model){
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list/{city}")
    String list(Model model, @PathVariable String city) {
        System.out.println("City>> " + city);
        model.addAttribute("city", city);
        return "houseList";
    }

    // lisbon page
    @RequestMapping(method = RequestMethod.GET, value = "/test")
    String test(Model model){
        model.addAttribute("user", "user");
        model.addAttribute("place", "place");
        return "test";
    }
}
