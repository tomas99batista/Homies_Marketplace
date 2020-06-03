package tqs.ua.pt.homies_marketplace.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tqs.ua.pt.homies_marketplace.dtos.PlaceDTO;
import tqs.ua.pt.homies_marketplace.form.FilterForm;
import tqs.ua.pt.homies_marketplace.form.LoginRegistrationForm;
import tqs.ua.pt.homies_marketplace.form.UserRegistrationForm;
import tqs.ua.pt.homies_marketplace.models.Booking;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.PlaceId;
import tqs.ua.pt.homies_marketplace.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import tqs.ua.pt.homies_marketplace.repository.BookingRepository;
import tqs.ua.pt.homies_marketplace.service.BookService;
import tqs.ua.pt.homies_marketplace.service.PlaceService;
import tqs.ua.pt.homies_marketplace.service.UserService;
import java.util.*;
import static java.lang.Long.parseLong;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class WebController {

    public static String user_status = "user_not_logged";
    public static User user_logged = new User();

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceController placeController;

    @Autowired
    UserController userController;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @PostMapping("/cancel_rent/{house}")
    public String cancelRentedHouse(@PathVariable("house") Place place){
        Booking booking = bookService.getBooking(place);

        // Delete from the owner rented_houses
        String requester_email = booking.getRequester();
        User requester = userService.getUserByEmail(requester_email);
        List<Long> rented_houses = requester.getRentedHouses();
        rented_houses.remove(place.getId());
        requester.setRentedHouses(rented_houses);
        // Delete from booking
        bookService.deleteBooking(booking);

        return "profile";
    }

    @GetMapping("/rented_houses")
    public String getRentedHousesByUser(Model model){
        ArrayList<Booking> allByOwner = bookService.getAllBookingsByUser(user_logged);
        ArrayList<Place> places = new ArrayList<>();
        for (Booking book : allByOwner) {
            places.add(placeService.getPlaceById(book.getPlaceId()));
        }
        model.addAttribute("rented_houses", places);
        return "rented_houses";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public String register(Model model){
        model.addAttribute("user", new UserRegistrationForm());

        // navbar
        model.addAttribute("user_status",user_status);
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute UserRegistrationForm userRegistrationForm, Model model){
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
            user_logged = user;
            user_status = "user_logged";

            // navbar
            model.addAttribute("user_status",user_status);
            return "index";
        } else {
            System.out.println("User already picked");
            return "user_picked";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login(Model model){
        model.addAttribute("user", new LoginRegistrationForm());

        //navbar
        model.addAttribute("user_status",user_status);
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute LoginRegistrationForm loginRegistrationForm, Model model){
        System.out.println("login - all users: " + userService.getAllUsers());
        if (userService.getUserByEmail(loginRegistrationForm.getEmail()) != null){
            if (userService.getUserByEmail(loginRegistrationForm.getEmail()).getPassword().equals(loginRegistrationForm.getPassword())){
                User user = userService.getUserByEmail(loginRegistrationForm.getEmail());
                System.out.println("logged user: " + user);
                user_status = "user_logged";
                user_logged = user;
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


    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index(Model model){
        // navbar
        model.addAttribute("user_status",user_status);
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public String places(Model model){
        List<Place> favorites = new ArrayList<>();

        if(user_status.equals("user_logged")){
            favorites = placeService.getFavoriteHouses(user_logged.getEmail());
        }
        List<String> cities = placeController.getAllCities();
        List<Place> places = placeService.getAllPlaces();
        model.addAttribute("favorites", favorites);
        model.addAttribute("places", places);
        model.addAttribute("cities", cities);
        model.addAttribute("filtered_places", new FilterForm());

        // navbar
        model.addAttribute("user_status",user_status);
        return "houseList";
    }

    @PostMapping("/list")
    public String filters(Model model, @ModelAttribute("filtered_places") FilterForm myFormObject, @RequestParam Map<String,String> data){
        List<String> cities = placeController.getAllCities();
        model.addAttribute("cities", cities);

        // navbar
        model.addAttribute("user_status",user_status);
        try{
            String city = ((myFormObject.getType()).equals("none"))? null : myFormObject.getCity();
            List<String> features = (myFormObject.getFeatures());
            String bedrooms = (myFormObject.getBedrooms() == 0)? null : String.valueOf(myFormObject.getBedrooms());
            String bathrooms = (myFormObject.getBathrooms() == 0)? null : String.valueOf(myFormObject.getBathrooms());
            String type = ((myFormObject.getType()).equals("none"))? null : myFormObject.getType().replace('_',' ');
            String minPrice = data.get("min-price");
            String maxPrice = data.get("max-price");


            // search(city, price, rating, bedrooms, bathrooms, type, minPrice, maxPrice)
            PlaceDTO placeDTO = new PlaceDTO(city, null, null, bedrooms, bathrooms, type);
            List<Place> places = placeService.search(placeDTO, minPrice, maxPrice);

            if(features == null){
                System.out.println(places);
                model.addAttribute("places", places);
            }
            else{
                List<Place> result = new ArrayList<>();
                for(Place place : places){
                    List<String> features_place = place.getFeatures();
                    System.out.println("1: " + features_place);
                    System.out.println("2: " + features);
                    features_place.retainAll(features);
                    System.out.println("3: " + features_place);
                    if( features_place.size() != 0){
                        result.add(place);
                    }
                }
                System.out.println(result);
                model.addAttribute("places", result);
            }

            return "houseList";
        }

        catch(Exception e){
            List<Place> places = placeService.getAllPlaces();
            model.addAttribute("places", places);
            return "houseList";
        }
    }

    @RequestMapping(value = "/addtofavorite", method = RequestMethod.POST, headers="Content-Type=application/json")
    public @ResponseBody JSONObject post(@RequestBody JSONObject data) {
        System.out.println(data);
        // Get id
        Long id_long = parseLong( (String) data.get("place_id"),10);
        if(user_status.equals("user_logged")){
            // Logged Status
            JSONObject response = new JSONObject();
            response.put("user_status","user_logged");

            // Not Saved
            if(data.get("status").equals("not_saved")){
                // Add to Favorites
                userService.addToFavorites(user_logged.getEmail(), new PlaceId(id_long));
                response.put("action","added");
                System.out.println(" ADICIONADO // FAVORITOS DO MENINO: " + placeService.getFavoriteHouses(user_logged.getEmail()).size());
            }
            // Already Saved
            else{
                userService.removeFavoritePlace(user_logged.getEmail(), new PlaceId(id_long));
                response.put("action","removed");
                System.out.println(" REMOVIDO // FAVORITOS DO MENINO: " + placeService.getFavoriteHouses(user_logged.getEmail()).size());

            }
            return response;
        }
        else{
            // Not Logged Status
            System.out.println("User not logged");
            JSONObject response = new JSONObject();
            response.put("user_status","user_not_logged");
            return response;
        }
    }

    @GetMapping("/list/{id}")
    public String details(@PathVariable("id") long id, Model model){
        Place place = placeService.getPlaceById(id);
        List<String> cities = placeController.getAllCities();
        List<Place> favoriteHouses = new ArrayList<>();

        if(user_status.equals("user_logged")){
            favoriteHouses = placeService.getFavoriteHouses(user_logged.getEmail());
        }
        model.addAttribute("cities", cities);
        model.addAttribute("place", place);
        model.addAttribute("placeFeatures", place.getFeatures());
        model.addAttribute("favorites",favoriteHouses);

        // navbar
        model.addAttribute("user_status",user_status);
        System.out.println(place.getFeatures());
        return "details";
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.POST, headers="Content-Type=application/json")
    public @ResponseBody JSONObject sendUser(@RequestBody JSONObject data) {
        System.out.println(data);
        // Get id
        String status = (String) data.get("status");
        System.out.println(status);
        JSONObject response = new JSONObject();
        response.put("user", user_logged.getFirstName());
    /*
        if(status.equals("logged")){
            response.put("user", user_logged.getFirstName());
        }
        else{
            response.put("user","not_logged");
        }*/
        return response;

    }

    @RequestMapping(method = RequestMethod.GET, value="/list/city/{city}")
    public String places_by_city(Model model, @PathVariable("city") String city) {
        List<Place> favorites = new ArrayList<>();

        if(user_status.equals("user_logged")){
            favorites = placeService.getFavoriteHouses(user_logged.getEmail());
        }
        List<String> cities = placeController.getAllCities();
        List<Place> placesbycity = placeService.searchByCity(city);
        model.addAttribute("favorites", favorites);
        model.addAttribute("places", placesbycity);
        model.addAttribute("cities", cities);
        model.addAttribute("user_status",user_status);
        model.addAttribute("filtered_places", new FilterForm());
        return "houseList";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        List<User> users = userService.getAllUsers();
        List<Place> favoriteHouses = placeService.getFavoriteHouses(user_logged.getEmail());
        List<Place> publishedHouses = placeService.getPublishedHouses(user_logged.getEmail());
        model.addAttribute("users",users);
        model.addAttribute("user_logged",user_logged);
        model.addAttribute("favorites",favoriteHouses);
        model.addAttribute( "published", publishedHouses);
        model.addAttribute("user_status",user_status);
        return "profile";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/load_db")
    public String load_db(Model model){

        User user= new User("josefrias@email.com", "password", "Jose","Frias", "Aveiro");
        List<String> listFeatures = Arrays.asList("Wifi", "TV", "Área Exterior", "Ar Condicionado", "Aquecimento Central", "Animais de Estimação", "Fumar", "Aquecimento Central");
        List<String> listFeatures1 = Arrays.asList("TV", "Área Exterior", "Elevador", "Aquecimento Central", "Animais de Estimação", "Fumar", "Aquecimento Central");
        List<String> listFeatures2 = Arrays.asList("Wifi", "Área Exterior", "Elevador", "Ar Condicionado", "Animais de Estimação", "Fumar", "Aquecimento Central");
        List<String> listFeatures3 = Arrays.asList("Wifi", "TV", "Elevador", "Ar Condicionado", "Aquecimento Central", "Fumar", "Aquecimento Central");
        List<String> listFeatures4 = Arrays.asList("Wifi", "TV", "Área Exterior","Ar Condicionado", "Aquecimento Central", "Animais de Estimação", "Aquecimento Central");
        List<String> listFeatures5 = Arrays.asList("Wifi", "TV", "Área Exterior", "Elevador", "Aquecimento Central", "Animais de Estimação", "Fumar");
        userService.save(user);

        // LISBOA
        placeService.save(new Place("Quarto espaçoso", 150.0,  listFeatures1, 3, 5, "Quarto privado", "Lisboa", "https://tecniconstroi.pt/wp-content/uploads/2019/02/quartos-10.jpg"));
        placeService.save(new Place("Casa central", 600.0,  listFeatures2, 2, 3, "Casa inteira", "Lisboa", "https://s2.glbimg.com/O0rQP5eOoWQ4aVL4WC16p-sUf9w=/smart/e.glbimg.com/og/ed/f/original/2019/12/20/quartos-pequenos-ensinam-como-aproveitar-espaco2.jpg"));
        placeService.save(new Place("Apartamento simples", 750.0,  listFeatures3, 1, 2, "Quarto partilhado", "Lisboa", "https://st3.idealista.pt/news/arquivos/styles/news_detail/public/2014-01/2_20.jpg?sv=uFOA6b2c&itok=vPd752SP"));
        placeService.save(new Place("Moradia estudantes", 250.0,  listFeatures4, 5, 4, "Quarto privado", "Lisboa", "https://r-cf.bstatic.com/images/hotel/max1024x768/215/215257948.jpg"));
        placeService.save(new Place("Casa de pasto", 650.0,  listFeatures5, 4, 2, "Casa inteira", "Lisboa", "https://i.pinimg.com/736x/2e/27/20/2e2720266ab45bc40d24c7a16aacdec6.jpg"));
        // AVEIRO
        userService.addPublishedHouse(user.getEmail(), new Place("Casa junto à universidade", 150.0,  listFeatures1, 2, 1, "Casa inteira", "Aveiro", "https://tecniconstroi.pt/wp-content/uploads/2019/02/quartos-10.jpg"));
        userService.addPublishedHouse(user.getEmail(),new Place("Apartamento junto à universidade", 250.0,  listFeatures2, 1, 5, "Quarto privado", "Aveiro", "https://s2.glbimg.com/O0rQP5eOoWQ4aVL4WC16p-sUf9w=/smart/e.glbimg.com/og/ed/f/original/2019/12/20/quartos-pequenos-ensinam-como-aproveitar-espaco2.jpg"));
        placeService.save(new Place("Quarto espaçoso junto à universidade", 180.0,  listFeatures3, 2, 3, "Quarto partilhado", "Aveiro", "https://st3.idealista.pt/news/arquivos/styles/news_detail/public/2014-01/2_20.jpg?sv=uFOA6b2c&itok=vPd752SP"));
        placeService.save(new Place("Quarto em apartamento simples", 190.0,  listFeatures4, 3, 4, "Quarto partilhado", "Aveiro", "https://lh3.googleusercontent.com/AcGhoHYB-z0quTYygoi-DNhPCwzO_vKwJRETpZoLcTRQvTEld5U53_kVfIQlp6DAIwaL9Ek70iOeVy3JQkG5aA=s1900"));
        placeService.save(new Place("Quarto espaçoso em casa central", 70.0,  listFeatures5, 4, 2, "Quarto privado", "Aveiro", "https://r-cf.bstatic.com/images/hotel/max1024x768/215/215257948.jpg"));
        placeService.save(new Place("Quarto", 350.0,  listFeatures, 1, 2, "Quarto privado", "Aveiro", "https://i.pinimg.com/736x/2e/27/20/2e2720266ab45bc40d24c7a16aacdec6.jpg"));
        // PORTO
        placeService.save(new Place("Quarto em casa de pasto", 120.0,  listFeatures1, 1, 3, "Quarto partilhado", "Porto", "https://tecniconstroi.pt/wp-content/uploads/2019/02/quartos-10.jpg"));
        placeService.save(new Place("Quarto espaçoso em moradia estudantes", 130.0,  listFeatures2, 2, 2, "Quarto partilhado", "Porto", "https://s2.glbimg.com/O0rQP5eOoWQ4aVL4WC16p-sUf9w=/smart/e.glbimg.com/og/ed/f/original/2019/12/20/quartos-pequenos-ensinam-como-aproveitar-espaco2.jpg"));
        placeService.save(new Place("Quarto", 170.0, listFeatures3, 5, 7, "Quarto privado", "Porto", "https://st3.idealista.pt/news/arquivos/styles/news_detail/public/2014-01/2_20.jpg?sv=uFOA6b2c&itok=vPd752SP"));
        placeService.save(new Place("Quarto simples", 299.0, listFeatures4, 3, 7, "Quarto partilhado", "Porto", "https://r-cf.bstatic.com/images/hotel/max1024x768/215/215257948.jpg"));
        placeService.save(new Place("Quarto partilhado", 120.0,  listFeatures5, 2, 4, "Quarto partilhado", "Porto", "https://i.pinimg.com/736x/2e/27/20/2e2720266ab45bc40d24c7a16aacdec6.jpg"));

        return "load_db";
    }


}