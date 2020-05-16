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




}