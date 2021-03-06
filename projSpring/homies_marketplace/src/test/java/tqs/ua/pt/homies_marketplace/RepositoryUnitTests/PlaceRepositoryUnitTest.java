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
@AutoConfigureTestDatabase
 class PlaceRepositoryUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlaceRepository placeRepository;

    @Test
     void whenFindById_thenReturnPlace() {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place("title1", 5.0,features, 1,1,"type1", "city", "photo1");
        entityManager.persistAndFlush(place);

        Place fromDb = placeRepository.findById(place.getId()).orElse(null);
        assertThat(fromDb.getTitle()).isEqualTo(place.getTitle());


    }

    @Test
     void whenInvalidId_thenReturnNull() {
        Place fromDb = placeRepository.findById(-1L);
        assertThat(fromDb).isNull();
    }

    @Test
     void whenFindByCity_thenReturnPlace() {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place("title1", 5.0,features, 1,1,"type1", "cityTesting", "photo1");
        entityManager.persistAndFlush(place);

        List<Place> found = placeRepository.findByCity("cityTesting");
        assertThat(found).hasSize(1).extracting(Place::getTitle).containsOnly(place.getTitle());
    }

    @Test
     void whenInvalidName_thenReturnNull() {
        List<Place> fromDb = placeRepository.findByCity("CityDoesNotExist");
        assertThat(fromDb).isEmpty();
    }







}