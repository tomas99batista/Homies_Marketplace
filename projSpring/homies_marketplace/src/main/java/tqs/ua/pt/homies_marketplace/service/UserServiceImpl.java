package tqs.ua.pt.homies_marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tqs.ua.pt.homies_marketplace.models.Booking;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.PlaceId;
import tqs.ua.pt.homies_marketplace.models.User;
import tqs.ua.pt.homies_marketplace.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookServiceImpl bookService;

    @Override
    public boolean addToRentedHouses(String email, PlaceId placeId){
        if (exists(email)){
            //check if the number of rows updated is one
            if (placeService.getPlaceById(placeId.getId())!=null) {
                int rowsUpdated = userRepository.insertRentedHouse(email, placeId.getId());
                //see owner of the place
                User owner=userRepository.findOwner(placeId.getId());
                bookService.createBooking(new Booking(owner.getEmail(), email, placeId.getId()));
                return rowsUpdated == 1;
            }
            return false;

        }
        return false;
    }

    @Override
    public boolean addToFavorites(String email, PlaceId placeId) {
        if (exists(email)){
            //check if the number of rows updated is one
            if (placeService.getPlaceById(placeId.getId())!=null) {
                int rowsUpdated = userRepository.insertFavoriteHouse(email, placeId.getId());
                return rowsUpdated == 1;
            }
            return false;

        }
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean exists(String email) {
        return userRepository.findByEmail(email) != null;
    }



    @Override
    public boolean addPublishedHouse(String email, Place place) {

        if (exists(email)){
            //save place to places Repository
            Place saved=placeService.save(place);
            if (saved!=null) {
                //get ID of the place
                long savedPlaceId = saved.getId();
                //add ID of the place to the user published houses

                //check if the number of rows updated is one
                int rowsUpdated=userRepository.insertPublishedHouse(email, savedPlaceId);

                return rowsUpdated == 1;
            }
            return false;
        }
        return false;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
