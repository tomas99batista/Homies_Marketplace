package tqs.ua.pt.homies_marketplace.RepositoryUnitTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlaceRepositoryUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlaceRepository placeRepository;

    @Test
    public void whenFindById_thenReturnPlace() {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "city",new ArrayList<>(), "photo1");
        entityManager.persistAndFlush(place);

        Place fromDb = placeRepository.findById(place.getId()).orElse(null);
        assertThat(fromDb.getTitle()).isEqualTo(place.getTitle());


    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Place fromDb = placeRepository.findById(-1L);
        assertThat(fromDb).isNull();
    }

    @Test
    public void whenFindByCity_thenReturnPlace() {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "cityTesting", new ArrayList<>(), "photo1");
        entityManager.persistAndFlush(place);

        List<Place> found = placeRepository.findByCity("cityTesting");
        assertThat(found).hasSize(1).extracting(Place::getTitle).containsOnly(place.getTitle());
    }

    @Test
    public void whenInvalidName_thenReturnNull() {
        List<Place> fromDb = placeRepository.findByCity("CityDoesNotExist");
        assertThat(fromDb).isEmpty();
    }

    @Test
    public void whenFindByPrice_thenReturnPlaces(){
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 1.0, 5.0,features, 1,1,"type1", "cityTesting", new ArrayList<>(), "photo1");
        entityManager.persistAndFlush(place);

        List<Place> found = placeRepository.findByPrice(0, 1);
        assertThat(found).hasSize(1).extracting(Place::getTitle).containsOnly(place.getTitle());

    }

    @Test
    public void whenFindByNonExistingPrice_thenNoReturnPlaces(){
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 1.0, 5.0,features, 1,1,"type1", "cityTesting", new ArrayList<>(), "photo1");
        entityManager.persistAndFlush(place);

        List<Place> found = placeRepository.findByPrice(0, -1);
        assertThat(found).hasSize(0);
    }

    @Test
    public void whenFindByCityAndPrice_thenPlacesShouldBeFound(){
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"titleTesting", 1.0, 5.0,features, 1,1,"type1", "cityTesting", new ArrayList<>(), "photo1");
        entityManager.persistAndFlush(place);

        List<Place> found=placeRepository.findByCityAndPrice("cityTesting", 0, 1);
        assertThat(found).hasSize(1).extracting(Place::getTitle).containsOnly(place.getTitle());


    }

    @Test
    public void whenFindByNonExistingCityAndPrice_thenPlacesShouldBeNotFound(){
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"titleTesting", 1.0, 5.0,features, 1,1,"type1", "cityTesting", new ArrayList<>(), "photo1");
        entityManager.persistAndFlush(place);

        List<Place> found=placeRepository.findByCityAndPrice("wrong_city", 0, -1);
        assertThat(found).hasSize(0);


    }

}
