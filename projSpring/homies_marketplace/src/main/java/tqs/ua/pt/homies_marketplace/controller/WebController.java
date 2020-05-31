package tqs.ua.pt.homies_marketplace.controller;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tqs.ua.pt.homies_marketplace.dtos.PlaceDTO;
import tqs.ua.pt.homies_marketplace.form.FilterForm;
import tqs.ua.pt.homies_marketplace.form.LoginRegistrationForm;
import tqs.ua.pt.homies_marketplace.form.UserRegistrationForm;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;
import tqs.ua.pt.homies_marketplace.repository.UserRepository;
import tqs.ua.pt.homies_marketplace.service.PlaceService;
import tqs.ua.pt.homies_marketplace.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
public class WebController {
    public static String user_status = "user_not_logged";

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceController placeController;

    @Autowired
    UserController userController;

    @Autowired
    UserService userService;


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
        System.out.println("login - all users: " + userService.getAllUsers());
        if (userService.getUserByEmail(loginRegistrationForm.getEmail()) != null){
            if (userService.getUserByEmail(loginRegistrationForm.getEmail()).getPassword().equals(loginRegistrationForm.getPassword())){
                User user = userService.getUserByEmail(loginRegistrationForm.getEmail());
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
    public String index(Model model){
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
        model.addAttribute("filtered_places", new FilterForm());
        return "houseList";
    }


    @PostMapping("/list")
    public String filters(Model model, @ModelAttribute FilterForm myFormObject, BindingResult result){
        List<String> cities = placeController.getAllCities();
        model.addAttribute("cities", cities);
        model.addAttribute("user_status",user_status);
        try{
            String city = myFormObject.getCity();
            System.out.println("ENtrOU");
            System.out.println(city);
            System.out.println(result);
            List<Place> places = placeService.searchByCity(city);
            model.addAttribute("places", places);
            return "houseList";
        }
        catch(Exception e){
            List<Place> places = placeService.getAllPlaces();
            model.addAttribute("places", places);
            return "houseList";
        }
    }


    /* AJAX REQUEST ATTEMPT
    @PostMapping(value = "/list", headers = "Accept=application/json")
    public String filter_places(Model model, @ResponseBody Map<String,Object> data) throws Exception {

        List<String> cities = placeController.getAllCities();   //cities
        try {
            System.out.println(data.getClass().getName());
            String city = (String) data.get("city");
            System.out.println(city);

            //List<Place> places = placeService.searchByCity((String) city);
            PlaceDTO placeDTO = new PlaceDTO(city, null, null, null, null, null);

            // search(city, price, rating, bedrooms, bathrooms, type, minPrice, maxPrice)
            List<Place> places = placeService.search(placeDTO, (String) data.get("minPrice"), (String) data.get("maxPrice"));
            System.out.print(places);
            model.addAttribute("places", places);
            model.addAttribute("cities", cities);
            model.addAttribute("user_status",user_status);



            return "houseList";
        }
        catch (Exception e){
            model.addAttribute("cities", cities);
            model.addAttribute("user_status",user_status);

            return "error";
        }

    }*/

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
        //usa o search em vez de search_by_city
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
        model.addAttribute("filtered_places", new FilterForm());
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

    @RequestMapping(method = RequestMethod.GET, value = "/load_db")
    String load_db(Model model){

        List<String> listFeatures = Arrays.asList("Wifi", "TV", "Área Exterior", "Ar Condicionado", "Aquecimento Central", "Animais de Estimação", "Fumar", "Aquecimento Central");
        List<String> listFeatures1 = Arrays.asList("TV", "Área Exterior", "Elevador", "Aquecimento Central", "Animais de Estimação", "Fumar", "Aquecimento Central");
        List<String> listFeatures2 = Arrays.asList("Wifi", "Área Exterior", "Elevador", "Ar Condicionado", "Animais de Estimação", "Fumar", "Aquecimento Central");
        List<String> listFeatures3 = Arrays.asList("Wifi", "TV", "Elevador", "Ar Condicionado", "Aquecimento Central", "Fumar", "Aquecimento Central");
        List<String> listFeatures4 = Arrays.asList("Wifi", "TV", "Área Exterior","Ar Condicionado", "Aquecimento Central", "Animais de Estimação", "Aquecimento Central");
        List<String> listFeatures5 = Arrays.asList("Wifi", "TV", "Área Exterior", "Elevador", "Aquecimento Central", "Animais de Estimação", "Fumar");

        // LISBOA
        placeService.save(new Place("Quarto espaçoso", 150.0, 0.0, listFeatures1, 3, 5, "Quarto privado", "Lisboa", "https://tecniconstroi.pt/wp-content/uploads/2019/02/quartos-10.jpg"));
        placeService.save(new Place("Casa central", 600.0, 0.0, listFeatures2, 2, 3, "Casa inteira", "Lisboa", "https://s2.glbimg.com/O0rQP5eOoWQ4aVL4WC16p-sUf9w=/smart/e.glbimg.com/og/ed/f/original/2019/12/20/quartos-pequenos-ensinam-como-aproveitar-espaco2.jpg"));
        placeService.save(new Place("Apartamento simples", 750.0, 0.0, listFeatures3, 1, 2, "Quarto partilhado", "Lisboa", "https://st3.idealista.pt/news/arquivos/styles/news_detail/public/2014-01/2_20.jpg?sv=uFOA6b2c&itok=vPd752SP"));
        placeService.save(new Place("Moradia estudantes", 250.0, 0.0, listFeatures4, 5, 4, "Quarto privado", "Lisboa", "https://r-cf.bstatic.com/images/hotel/max1024x768/215/215257948.jpg"));
        placeService.save(new Place("Casa de pasto", 650.0, 0.0, listFeatures5, 4, 2, "Casa inteira", "Lisboa", "https://i.pinimg.com/736x/2e/27/20/2e2720266ab45bc40d24c7a16aacdec6.jpg"));
        // AVEIRO
        placeService.save(new Place("Casa junto à universidade", 150.0, 0.0, listFeatures1, 2, 1, "Casa inteira", "Aveiro", "https://tecniconstroi.pt/wp-content/uploads/2019/02/quartos-10.jpg"));
        placeService.save(new Place("Apartamento junto à universidade", 250.0, 0.0, listFeatures2, 1, 5, "Quarto privado", "Aveiro", "https://s2.glbimg.com/O0rQP5eOoWQ4aVL4WC16p-sUf9w=/smart/e.glbimg.com/og/ed/f/original/2019/12/20/quartos-pequenos-ensinam-como-aproveitar-espaco2.jpg"));
        placeService.save(new Place("Quarto espaçoso junto à universidade", 180.0, 0.0, listFeatures3, 2, 3, "Quarto partilhado", "Aveiro", "https://st3.idealista.pt/news/arquivos/styles/news_detail/public/2014-01/2_20.jpg?sv=uFOA6b2c&itok=vPd752SP"));
        placeService.save(new Place("Quarto em apartamento simples", 190.0, 0.0, listFeatures4, 3, 4, "Quarto partilhado", "Aveiro", "https://lh3.googleusercontent.com/AcGhoHYB-z0quTYygoi-DNhPCwzO_vKwJRETpZoLcTRQvTEld5U53_kVfIQlp6DAIwaL9Ek70iOeVy3JQkG5aA=s1900"));
        placeService.save(new Place("Quarto espaçoso em casa central", 70.0, 0.0, listFeatures5, 4, 2, "Quarto privado", "Aveiro", "https://r-cf.bstatic.com/images/hotel/max1024x768/215/215257948.jpg"));
        placeService.save(new Place("Quarto", 350.0, 0.0, listFeatures, 1, 2, "Quarto privado", "Aveiro", "https://i.pinimg.com/736x/2e/27/20/2e2720266ab45bc40d24c7a16aacdec6.jpg"));
        // PORTO
        placeService.save(new Place("Quarto em casa de pasto", 120.0, 0.0, listFeatures1, 1, 3, "Quarto partilhado", "Porto", "https://tecniconstroi.pt/wp-content/uploads/2019/02/quartos-10.jpg"));
        placeService.save(new Place("Quarto espaçoso em moradia estudantes", 130.0, 0.0, listFeatures2, 2, 2, "Quarto partilhado", "Porto", "https://s2.glbimg.com/O0rQP5eOoWQ4aVL4WC16p-sUf9w=/smart/e.glbimg.com/og/ed/f/original/2019/12/20/quartos-pequenos-ensinam-como-aproveitar-espaco2.jpg"));
        placeService.save(new Place("Quarto", 170.0, 0.0, listFeatures3, 5, 7, "Quarto privado", "Porto", "https://st3.idealista.pt/news/arquivos/styles/news_detail/public/2014-01/2_20.jpg?sv=uFOA6b2c&itok=vPd752SP"));
        placeService.save(new Place("Quarto simples", 299.0, 0.0, listFeatures4, 3, 7, "Quarto partilhado", "Porto", "https://r-cf.bstatic.com/images/hotel/max1024x768/215/215257948.jpg"));
        placeService.save(new Place("Quarto partilhado", 120.0, 0.0, listFeatures5, 2, 4, "Quarto partilhado", "Porto", "https://i.pinimg.com/736x/2e/27/20/2e2720266ab45bc40d24c7a16aacdec6.jpg"));

        return "load_db";
    }


}