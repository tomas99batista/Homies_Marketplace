package tqs.ua.pt.homies_marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.PlaceSpecification;
import tqs.ua.pt.homies_marketplace.models.Review;
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;

import org.springframework.transaction.annotation.Transactional;
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
    public List<Place> search(String city, String price, String rating, String bedrooms, String bathrooms, String type, String minPrice, String maxPrice) {
        Place filter= new Place();
        filter.setCity(city);
        filter.setPrice(price != null ? Double.parseDouble(price): -1);
        filter.setRating(rating !=null ? Double.parseDouble(rating): -1);
        filter.setNumberBedrooms(bedrooms !=null ? Integer.parseInt(bedrooms): -1);
        filter.setNumberBathrooms(bathrooms != null ? Integer.parseInt(bathrooms): -1);
        filter.setType(type);
        Specification<Place> spec;

        if (minPrice !=null || maxPrice !=null){
            spec = new PlaceSpecification(filter, minPrice !=null ? Double.parseDouble(minPrice): -1, maxPrice != null ? Double.parseDouble(maxPrice): -1);
        }
        else {
            spec = new PlaceSpecification(filter);
        }
        return placeRepository.findAll(spec);
    }


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
        return placeRepository.findAll();
    }
}
