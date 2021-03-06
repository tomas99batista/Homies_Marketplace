package tqs.ua.pt.homies_marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tqs.ua.pt.homies_marketplace.models.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);


    //insert new published house given a user email
    @Modifying
    @Query(value="insert into users_published_houses values (?1, ?2)", nativeQuery=true)
    int insertPublishedHouse(String email, long savedPlaceId);

    @Modifying
    @Query(value = "insert into users_favorites values (?1, ?2)", nativeQuery = true)
    int insertFavoriteHouse(String email, long placeId);

    @Modifying
    @Query(value = "insert into users_rented_houses values (?1, ?2)", nativeQuery = true)
    int insertRentedHouse(String email, long placeId);

    @Query(value = "select * from users where email in (select users_email from users_published_houses where published_houses=?1)", nativeQuery = true)
    User findOwner(long placeId);

    @Modifying
    @Query(value = "delete from users_favorites where users_email like ?1 and favorites=?2", nativeQuery = true)
    int removeFavoriteHouse(String email, long id);

}