package tqs.ua.pt.homies_marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tqs.ua.pt.homies_marketplace.models.Place;

import java.util.List;

@Repository
@Transactional
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Place findById(long id);

    //get favorite houses
    @Query(value="select * from place where id in (select favorites from users_favorites where users_email like ?1);", nativeQuery = true)
    List<Place> findFavoriteHouses(String email);

    //get published houses
    @Query(value="select * from place where id in (select published_houses from users_published_houses where users_email like ?1);", nativeQuery = true)
    List<Place> findPublishedHouses(String email);


    //rows with city that contains or is equal to city name
    @Query(value="SELECT * FROM PLACE WHERE CITY ~* ?1 ;", nativeQuery = true)
    List<Place> findByCity(String city);

    @Modifying
    @Query(value = "insert into place_reviews values (?1, ?2)", nativeQuery = true)
    int insertReview(long placeId, long reviewId);

    @Query(value = "select * from place where price >= ?1 and price <=?2", nativeQuery = true)
    List<Place> findByPrice(double minPrice, double maxPrice);

    @Query(value = "select * from place where city ~* ?1 and price >= ?2 and price <=?3", nativeQuery = true)
    List<Place> findByCityAndPrice(String city, double minPrice, double maxPrice);
}
