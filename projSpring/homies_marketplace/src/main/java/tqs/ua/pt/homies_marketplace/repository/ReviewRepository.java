package tqs.ua.pt.homies_marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tqs.ua.pt.homies_marketplace.models.Review;

import java.util.List;

@Repository
@Transactional
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value="select * from reviews where id in (select reviews from place_reviews where place_id=?1);", nativeQuery = true)
    List<Review> findReviews(long placeId);
}
