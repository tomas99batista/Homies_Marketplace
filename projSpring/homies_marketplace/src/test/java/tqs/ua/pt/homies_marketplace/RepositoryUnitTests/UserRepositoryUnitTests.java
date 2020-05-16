package tqs.ua.pt.homies_marketplace.RepositoryUnitTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.User;
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;
import tqs.ua.pt.homies_marketplace.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryUnitTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByEmail_thenReturnUser() {
        List<Long> favorites= new ArrayList<>();
        favorites.add(1L);
        favorites.add(2L);

        List<Long> publishedHouses= new ArrayList<>();
        publishedHouses.add(3L);
        publishedHouses.add(4L);

        List<Long> rentedHouses= new ArrayList<>();
        rentedHouses.add(5L);
        rentedHouses.add(6L);

        String email="josefrias@email.com";
        String password="password";
        String firstName="Jose";
        String lastName="Frias";
        String city="Aveiro";

        User user= new User(email, favorites, password, firstName, lastName, city, publishedHouses, rentedHouses);
        entityManager.persistAndFlush(user);

        User fromDb = userRepository.findByEmail(user.getEmail());
        assertThat(fromDb.getFirstName()).isEqualTo(user.getFirstName());


    }

    @Test
    public void whenInvalidEmail_thenReturnNull() {
        User fromDb = userRepository.findByEmail("DoesNotExist@email.com");
        assertThat(fromDb).isNull();
    }






}
