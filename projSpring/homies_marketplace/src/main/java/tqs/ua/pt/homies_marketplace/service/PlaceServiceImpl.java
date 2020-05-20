package tqs.ua.pt.homies_marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.Review;
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class PlaceServiceImpl implements PlaceService{

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ReviewServiceImpl reviewService;


    @Override
    public List<Review> getReviews(long placeId) {
        return reviewService.getReviews(placeId);
    }

    @Override
    public boolean addReview(long placeId, Review review) {
        if (userService.exists(review.getEmail())) {
            //save it in review repository
            Review saved=reviewService.save(review);
            //get id of the review
            if (saved!=null) {
                long savedId = saved.getId();

                //save it in places_review
                return placeRepository.insertReview(placeId, savedId) == 1;
            }
            return false;
        }

        return false;
    }

    @Override
    public Place save(Place place) {
        return placeRepository.save(place);
    }

    @Override
    public List<Place> getFavoriteHouses(String email) {
        return placeRepository.findFavoriteHouses(email);
    }

    @Override
    public Place getPlaceById(long id){
        return placeRepository.findById(id);
    }

    @Override
    public List<Place> searchByCity(String city) {
        return placeRepository.findByCity(city);
    }

    public List<Place> getPublishedHouses(String email){
        return placeRepository.findPublishedHouses(email);
    }

    @Override
    public List<Place> getAllPlaces() {
        List<String> features = new ArrayList<>();
        features.add("Barato");
        features.add("Gamer");
        Place place = new Place(0L, "Test", 100.0, 3.0, features, 1, 1, "casa", "Lisboa");
        Place newPlace = new Place(1L, "NEWTEST", 10.0, 2.3, features, 1, 1, "coisa", "Porto");
        List<Place> places = new ArrayList<>();
        places.add(place);
        places.add(newPlace);
        return places;
        //return placeRepository.findAll();
    }
}
