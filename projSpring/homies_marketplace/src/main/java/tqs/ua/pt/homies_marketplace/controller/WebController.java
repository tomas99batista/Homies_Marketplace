package tqs.ua.pt.homies_marketplace.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tqs.ua.pt.homies_marketplace.dtos.PlaceDTO;
import tqs.ua.pt.homies_marketplace.form.AddPlaceForm;
import tqs.ua.pt.homies_marketplace.form.FilterForm;
import tqs.ua.pt.homies_marketplace.form.LoginRegistrationForm;
import tqs.ua.pt.homies_marketplace.form.UserRegistrationForm;
import tqs.ua.pt.homies_marketplace.models.*;
import org.springframework.web.bind.annotation.GetMapping;
import tqs.ua.pt.homies_marketplace.repository.BookingRepository;
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;
import tqs.ua.pt.homies_marketplace.service.BookService;
import tqs.ua.pt.homies_marketplace.service.PlaceService;
import tqs.ua.pt.homies_marketplace.service.UserService;
import java.util.*;
import static java.lang.Long.parseLong;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @PostMapping("/cancel_rent/{placeId}")
    public String cancelRentedHouse(@PathVariable("placeId") long placeId){
        Booking booking = bookService.getBooking(placeId);

        // Delete from the owner rented_houses
        String requester_email = booking.getRequester();
        User requester = userService.getUserByEmail(requester_email);
        List<Long> rented_houses = requester.getRentedHouses();
        rented_houses.remove(placeId);
        requester.setRentedHouses(rented_houses);
        // Delete from booking
        bookService.deleteBooking(booking);

        return "profile";
    }

    @GetMapping("/rented_houses")
    public String getRentedHousesByUser(Model model){
        List<Booking> allByOwner = bookService.getAllBookingsByEmail(user_logged.getEmail());
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
            user.setRentedHouses(new ArrayList<>());
            user.setFavorites(new ArrayList<>());
            user.setFavorites(new ArrayList<>());
            System.out.println("new user: " + user);
            userService.save(user);
            user_logged = user;
            user_status = "user_logged";

            // navbar
            model.addAttribute("user_status",user_status);
            return "redirect:/";
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

    @RequestMapping(method = RequestMethod.GET, value = "/update_profile")
    public String updateProfileGet(Model model){
        // Ja vai c dados
        UserRegistrationForm userRegistrationForm = new UserRegistrationForm(); // O EMAIL N PODE SER ALTERADO
        userRegistrationForm.setEmail(user_logged.getEmail());
        userRegistrationForm.setFirstName(user_logged.getFirstName());
        userRegistrationForm.setLastName(user_logged.getLastName());
        userRegistrationForm.setCity(user_logged.getCity());

        model.addAttribute("user", userRegistrationForm);

        //navbar
        model.addAttribute("user_status",user_status);
        return "update_profile";
    }

    @PostMapping("/update_profile")
    public String updatePofilePost(@ModelAttribute UserRegistrationForm userRegistrationForm, Model model){
        System.out.println("URF " + userRegistrationForm.toString());
        User user = userService.getUserByEmail(userRegistrationForm.getEmail());
        user.setFirstName(userRegistrationForm.getFirstName());
        user.setLastName(userRegistrationForm.getLastName());
        user.setCity(userRegistrationForm.getCity());
        user.setPassword(userRegistrationForm.getPassword());
        userService.save(user);
        System.out.println("USER UPDATED " + user.toString());
        return "redirect:/";
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

    // Filtered Search
    @PostMapping("/list")
    public String filters(Model model, @ModelAttribute("filtered_places") FilterForm myFormObject, @RequestParam Map<String,String> data){
        List<String> cities = placeController.getAllCities();
        model.addAttribute("cities", cities);

        // navbar
        model.addAttribute("user_status",user_status);

        List<Place> favorites = new ArrayList<>();

        if(user_status.equals("user_logged")){
            favorites = placeService.getFavoriteHouses(user_logged.getEmail());
        }
        model.addAttribute("favorites", favorites);
        model.addAttribute("filtered_places", new FilterForm());

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

    // Add To Favorite
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

    // Place Details
    @GetMapping("/list/{id}")
    public String details(@PathVariable("id") long id, Model model){
        List<User> users = userService.getAllUsers();

        Place place = placeService.getPlaceById(id);
        List<String> cities = placeController.getAllCities();
        List<Place> favoriteHouses = new ArrayList<>();
        List<Review> reviews = placeService.getReviews(id);
        User owner = userService.findOwner(id);
        if(user_status.equals("user_logged")){
            favoriteHouses = placeService.getFavoriteHouses(user_logged.getEmail());
        }

        // Get Rented by All
        List<Place> rentedPlacesByAll = new ArrayList<>();
        for (User user : userService.getAllUsers()){
            System.out.println(user);
            User req = userService.getUserByEmail(user.getEmail());
            List<Long> rentedHousesByAll = req.getRentedHouses();
            for(Long id_house : rentedHousesByAll) rentedPlacesByAll.add(placeService.getPlaceById(id_house));
            System.out.println("RENTED PLACES ID: " + rentedHousesByAll);
            System.out.println("RENTED PLACES Place: " + rentedPlacesByAll);

        }

        // Is it rented?
        for(User user : users) {
            if (rentedPlacesByAll.contains(place)) {
                System.out.println("IT IS RENTED");
                System.out.println(rentedPlacesByAll);
                System.out.println(place);
                model.addAttribute("book_status", "false");
                break;
            } else model.addAttribute("book_status", "true");
        }

        model.addAttribute("cities", cities);
        model.addAttribute("place", place);
        model.addAttribute("owner", owner);
        model.addAttribute("placeFeatures", place.getFeatures());
        model.addAttribute("favorites",favoriteHouses);
        model.addAttribute("reviews", reviews);

        // navbar
        model.addAttribute("user_status",user_status);
        return "details";
    }

    // Review
    @RequestMapping(value = "/reviews", method = RequestMethod.POST, headers="Content-Type=application/json")
    public @ResponseBody JSONObject reviews(@RequestBody JSONObject data) {
        System.out.println(data);
        JSONObject response = new JSONObject();
        System.out.println(user_logged.equals("user_logged"));
        if(user_status.equals("user_logged")){
            response.put("user_status","user_logged");
            System.out.println("in");
            Long place_id = Long.parseLong(String.valueOf(data.get("place_id")));
            System.out.println(String.valueOf(data.get("reviewGrade")));
            System.out.println( (String.valueOf(data.get("reviewGrade"))).getClass().getName()  );

            Double rating = Double.parseDouble(String.valueOf(data.get("reviewGrade")));
            String comment = String.valueOf(data.get("comment"));
            String email = user_logged.getEmail();
            Review review = new Review(email, rating, comment);
            placeService.addReview(place_id, review);
            response.put("status","success");
        }
        else{
            System.out.println("failed");
            response.put("user_status","user_not_logged");
            response.put("status","failed");

        }
        return response;
    }

    // Get User
    @RequestMapping(value = "/getUser", method = RequestMethod.POST, headers="Content-Type=application/json")
    public @ResponseBody JSONObject sendUser(@RequestBody JSONObject data) {
        // Get id - USELESS
        String status = String.valueOf(data.get("status"));
        System.out.println(status);
        JSONObject response = new JSONObject();
        response.put("user", user_logged.getFirstName());
        return response;

    }

    // Place By City
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

    @PostMapping("/logout")
    public String logout(@ModelAttribute String logout, Model model) {
        user_status = "user_not_logged";
        user_logged = new User();
        model.addAttribute("user_status",user_status);
        return "index";
    }

    // Booking
    @RequestMapping(value = "/book_place", method = RequestMethod.POST, headers="Content-Type=application/json")
    public @ResponseBody JSONObject book(@RequestBody JSONObject data) {
        String placeId = String.valueOf(data.get("place_id"));
        Long long_id = Long.parseLong(placeId);
        PlaceId id = new PlaceId(long_id);
        JSONObject response = new JSONObject();

        if(user_status.equals("user_logged")){
            response.put("user_status","logged");
            if(user_logged.getRentedHouses().contains(Long.parseLong(placeId))){
                response.put("success","false");
            }
            else{
                boolean saved = userService.addToRentedHouses(user_logged.getEmail(), id);
                System.out.println(saved);
                if (saved) response.put("success","true");
                else response.put("success","false");
            }
        }
        else{
            response.put("user_status","not_logged");
            response.put("success","false");
        }
        return response;
    }

    // Cancel Booking
    @RequestMapping(value = "/cancel_booking", method = RequestMethod.POST, headers="Content-Type=application/json")
    public @ResponseBody JSONObject cancelBooking(@RequestBody JSONObject data) {
        String placeId = String.valueOf(data.get("place_id"));
        JSONObject response = new JSONObject();

        User requester = userService.getUserByEmail(user_logged.getEmail());
        List<Long> rentedHouses = requester.getRentedHouses();

        try{
            // TESTING
            System.out.println("1 : RENTED HOUSES id:" + rentedHouses);
            System.out.println("SIZE BEFORE: " + rentedHouses.size());
            int size_before = rentedHouses.size();

            // Delete from the owner rented_houses
            Booking booking = bookService.getBooking(Long.parseLong(placeId));
            rentedHouses.remove(Long.parseLong(placeId));
            System.out.println(rentedHouses);
            user_logged.setRentedHouses(rentedHouses);

            // Delete from booking
            bookService.deleteBooking(booking);

            // TESTING
            System.out.println("END: " + rentedHouses);
            System.out.println("SIZE AFTER: " + rentedHouses.size());
            int size_after = rentedHouses.size();
            System.out.println("USER:" + user_logged);

            // VERIFY
            response.put("success","true");
        }
        catch(Exception e){
            response.put("success","false");
        }

        return response;
    }

    // Profile
    @GetMapping("/profile")
    public String profile(Model model){
        model.addAttribute("addPlace", new AddPlaceForm());

        List<User> users = userService.getAllUsers();

        // Get Favorites
        List<Place> favoriteHouses = placeService.getFavoriteHouses(user_logged.getEmail());

        // Get Published
        List<Place> publishedHouses = placeService.getPublishedHouses(user_logged.getEmail());

        // Get Bookings
        List<Booking> allByOwner = bookService.getAllBookingsByEmail(user_logged.getEmail());
        List<Place> bookedHouses = new ArrayList<>();
        for (Booking book : allByOwner) bookedHouses.add(placeService.getPlaceById(book.getPlaceId()));

        // Get Rented by User
        User requester = userService.getUserByEmail(user_logged.getEmail());
        List<Long> rentedHouses = requester.getRentedHouses();
        List<Place> rentedPlaces = new ArrayList<>();
        for(Long id : rentedHouses) rentedPlaces.add(placeService.getPlaceById(id));

        // Get Rented by All
        List<Place> rentedPlacesByAll = new ArrayList<>();
        for ( User user : userService.getAllUsers()){
            User req = userService.getUserByEmail(user.getEmail());
            List<Long> rentedHousesByAll = req.getRentedHouses();
            for(Long id : rentedHousesByAll) rentedPlacesByAll.add(placeService.getPlaceById(id));
        }


        model.addAttribute("rentedHouses", rentedPlaces);
        model.addAttribute("rentedHousesAll",rentedPlacesByAll);
        model.addAttribute("bookedHouses", bookedHouses);
        model.addAttribute("users",users);
        model.addAttribute("user_logged",user_logged);
        model.addAttribute("favorites",favoriteHouses);
        model.addAttribute( "published", publishedHouses);
        model.addAttribute("user_status",user_status);
        return "profile";
    }

    @PostMapping("/profile")
    public String addPlace(@ModelAttribute AddPlaceForm addPlaceForm, Model model){
        System.out.println("all users: " + userService.getAllUsers());
        if (userService.getUserByEmail(addPlaceForm.getTitle()) == null){
            Place place = new Place();
            place.setType(addPlaceForm.getType());
            place.setCity(addPlaceForm.getCity());
            place.setPrice(addPlaceForm.getPrice());
            place.setTitle(addPlaceForm.getTitle());
            place.setNumberBathrooms(addPlaceForm.getNumBathrooms());
            place.setNumberBedrooms(addPlaceForm.getNumBedrooms());
            place.setFeatures(addPlaceForm.getFeatures());
            place.setReviews(new ArrayList<>());
            place.setPhotos(addPlaceForm.getPhoto());
            placeService.save(place);
            userService.addPublishedHouse(user_logged.getEmail(), place);
            System.out.println("new place: " + place.toString());
            return "redirect:/profile";
        } else {
            System.out.println("ERROR");
            return "profile";
        }
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