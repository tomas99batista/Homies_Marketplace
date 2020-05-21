package tqs.ua.pt.homies_marketplace.service;

import tqs.ua.pt.homies_marketplace.models.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getReviews(long placeId);

    Review save(Review review);
}
