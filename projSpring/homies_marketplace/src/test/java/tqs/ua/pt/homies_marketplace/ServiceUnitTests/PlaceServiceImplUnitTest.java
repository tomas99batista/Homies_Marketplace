package tqs.ua.pt.homies_marketplace.ServiceUnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.ua.pt.homies_marketplace.dtos.PlaceDTO;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.PlaceSpecification;
import tqs.ua.pt.homies_marketplace.models.Review;
import tqs.ua.pt.homies_marketplace.models.User;
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;
import tqs.ua.pt.homies_marketplace.service.PlaceServiceImpl;
import tqs.ua.pt.homies_marketplace.service.ReviewServiceImpl;
import tqs.ua.pt.homies_marketplace.service.UserServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
 class PlaceServiceImplUnitTest {

    @Mock( lenient = true)
    private PlaceRepository placeRepository;

    @Mock( lenient = true)
    private UserServiceImpl userService;

    @Mock(lenient = true)
    private ReviewServiceImpl reviewService;

    @InjectMocks
    private PlaceServiceImpl placeService;



    @BeforeEach
     void setUp(){
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place("title1", 5.0, features, 1,1,"type1", "city", "photo1");
        List<Place> places=new ArrayList<>();
        places.add(place);

        List<Review> reviews=new ArrayList<>();
        Review r= new Review("jose@email.com", 4.0, "comment1");
        reviews.add(r);
         Mockito.when(placeRepository.findAll()).thenReturn(places);
        Mockito.when(placeRepository.findByCity("city")).thenReturn(places);
        Mockito.when(placeRepository.findByCity("aveiro")).thenReturn(null);
        Mockito.when(placeRepository.findById(1L)).thenReturn(place);
        Mockito.when(placeRepository.findById(-1L)).thenReturn(null);
        Mockito.when(placeRepository.findFavoriteHouses("jose@email.com")).thenReturn(places);
        Mockito.when(placeRepository.findPublishedHouses("jose@email.com")).thenReturn(places);
        Mockito.when(reviewService.getReviews(1L)).thenReturn(reviews);
        Mockito.when(userService.exists("jose@email.com")).thenReturn(true);
        Mockito.when(userService.exists("wrong_email@email.com")).thenReturn(false);

    }

   @Test
   void whenGetPublishedHouses_thenReturnPublishedHouses(){
      List<Place> published=placeService.getPublishedHouses("jose@email.com");
      assertThat(published).hasSize(1).extracting(Place::getTitle).contains("title1");

   }

    @Test
    void whenGetFavoriteHouses_thenReturnFavoriteHouses(){
       List<Place> favorites=placeService.getFavoriteHouses("jose@email.com");
       assertThat(favorites).hasSize(1).extracting(Place::getTitle).contains("title1");

    }
    @Test
    void whenGetReviews_thenReturnReviews(){
       List<Review> reviews=placeService.getReviews(1L);
       assertThat(reviews).hasSize(1).extracting(Review::getRating).contains(4.0);

    }
    @Test
    void whenGetAllPlaces_thenReturnAll(){
       List<Place> allPlaces=placeService.getAllPlaces();
       assertThat(allPlaces).hasSize(1).extracting(Place::getTitle).contains("title1");
    }
    @Test
     void whenValidPlaceId_thenPlaceShouldBeFound(){
        Place fromDb=placeService.getPlaceById(1L);
        assertThat(fromDb.getTitle()).isEqualTo("title1");

        verifyFindByIdIsCalledOnce();
    }

    @Test
     void whenInvalidPlaceId_thenPlaceShouldNotBeFound() {
        Place fromDb = placeService.getPlaceById(-1L);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
     void whenPostPlace_thenPlaceShouldBeSaved() {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place("title1", 5.0, features, 1,1,"type1", "city", "photo1");
        Mockito.when(placeRepository.save(place)).thenReturn(place);
        Place saved = placeService.save(place);

        assertThat(saved.getTitle()).isEqualTo(place.getTitle());

    }

    @Test
     void WhenSearchByExistingCity_thenPlaceShouldBeFound(){
        List<Place> fromDb=placeService.searchByCity("city");
        verifyfindByCityIsCalledOnce("city");
        assertThat(fromDb).hasSize(1).extracting(Place::getTitle).contains("title1");

    }

    @Test
     void WhenSearchByNonExistingCity_thenPlaceShouldNotBeFound(){
        List<Place> fromDb=placeService.searchByCity("aveiro");
        verifyfindByCityIsCalledOnce("aveiro");
        assertThat(fromDb).isNull();

    }



    @Test
     void WhenValidEmail_thenAddReview(){
        String email="jose@email.com";
        Review review= new Review(1L,email, 4.0, "comment1");
        Mockito.when(reviewService.save(review)).thenReturn(review);
        Mockito.when(placeRepository.insertReview(8, 1L)).thenReturn(1);
        boolean saved=placeService.addReview(8, review);
        assertThat(saved).isEqualTo(true);
        verifyInsertReviewIsCalledOnce();

    }

    @Test
     void WhenValidEmailAndPlacesButRowsNotInserted_thenReviewShouldNotSaved(){
        String email="jose@email.com";
        Review review= new Review(1L,email, 4.0, "comment1");
        Mockito.when(reviewService.save(review)).thenReturn(review);
        Mockito.when(placeRepository.insertReview(8, 1L)).thenReturn(0);
        boolean saved=placeService.addReview(8, review);
        assertThat(saved).isEqualTo(false);
        verifyInsertReviewIsCalledOnce();
    }

    @Test
     void WhenValidEmailButNullPlace_thenReviewShouldNotSaved(){
        String email="jose@email.com";
        Review review= new Review(1L,email, 4.0, "comment1");
        Mockito.when(reviewService.save(review)).thenReturn(null);
        Mockito.when(placeRepository.insertReview(8, 1L)).thenReturn(1);
        boolean saved=placeService.addReview(8, review);
        assertThat(saved).isEqualTo(false);
        verifyInsertReviewIsNotCalled();
    }

    @Test
     void whenInvalidEmail_thenReviewNewIsNotSaved(){
        String email="wrong_email@email.com";
        Review review= new Review(1L,email, 4.0, "comment1");
        Mockito.when(userService.exists(email)).thenReturn(false);
        Mockito.when(reviewService.save(review)).thenReturn(review);
        Mockito.when(placeRepository.insertReview(8, 1L)).thenReturn(1);
        boolean saved=placeService.addReview(8, review);
        assertThat(saved).isEqualTo(false);
        verifyInsertReviewIsNotCalled();

    }

    @Test
    void whenSearch_thenReturnResults(){
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place("title1", 5.0, features, 1,1,"type1", "city", "photo1");
        List<Place> placeList=new ArrayList<>();
        placeList.add(place);
        Place spec=new Place();
        spec.setCity("city");
        spec.setPrice(5.0);
        spec.setType("type1");
        Mockito.when(placeRepository.findAll((PlaceSpecification)Mockito.any())).thenReturn(placeList);
        List<Place> results=placeService.search(new PlaceDTO("city", "5", null, null, null, "type1"), null, null);
        assertThat(results).hasSize(1).extracting(Place::getCity).contains("city");

    }

    @Test
    void whenSearchByMinAndMaxPrice_thenReturnResults(){
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place("title1", 5.0, features, 1,1,"type1", "city", "photo1");
        List<Place> placeList=new ArrayList<>();
        placeList.add(place);
        Place spec=new Place();
        spec.setCity("city");
        spec.setPrice(5.0);
        spec.setType("type1");
        Mockito.when(placeRepository.findAll((PlaceSpecification)Mockito.any())).thenReturn(placeList);
        List<Place> results=placeService.search(new PlaceDTO("city", "5", "5.0", "1", "1", "type1"), "0", "5");
        assertThat(results).hasSize(1).extracting(Place::getCity).contains("city");

    }

    @Test
    void whenSearchByMinPrice_thenReturnResults(){
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place("title1", 5.0, features, 1,1,"type1", "city", "photo1");
        List<Place> placeList=new ArrayList<>();
        placeList.add(place);
        Place spec=new Place();
        spec.setCity("city");
        spec.setPrice(5.0);
        spec.setType("type1");
        Mockito.when(placeRepository.findAll((PlaceSpecification)Mockito.any())).thenReturn(placeList);
        List<Place> results=placeService.search(new PlaceDTO("city", "5", null, null, null, "type1"), "0", null);
        assertThat(results).hasSize(1).extracting(Place::getCity).contains("city");

    }

    @Test
    void whenSearchByMaxPrice_thenReturnResults(){
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place("title1", 5.0, features, 1,1,"type1", "city", "photo1");
        List<Place> placeList=new ArrayList<>();
        placeList.add(place);
        Place spec=new Place();
        spec.setCity("city");
        spec.setPrice(5.0);
        spec.setType("type1");
        Mockito.when(placeRepository.findAll((PlaceSpecification)Mockito.any())).thenReturn(placeList);
        List<Place> results=placeService.search(new PlaceDTO("city", "5", null, null, null, "type1"), null, "5");
        assertThat(results).hasSize(1).extracting(Place::getCity).contains("city");

    }

    @Test
    void whenSearchByNonExistingAttributes_thenNoReturnResults(){
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place("title1", 5.0, features, 1,1,"type1", "city", "photo1");
        List<Place> placeList=new ArrayList<>();
        Place spec=new Place();
        spec.setCity("city");
        spec.setPrice(5.0);
        spec.setType("type1");
        Mockito.when(placeRepository.findAll((PlaceSpecification)Mockito.any())).thenReturn(placeList);
        List<Place> results=placeService.search(new PlaceDTO(null, null, null, null, null, null), null ,null);
        assertThat(results).hasSize(0);

    }

    private void verifyfindByCityIsCalledOnce(String city) {
        Mockito.verify(placeRepository, VerificationModeFactory.times(1)).findByCity(city);
        Mockito.reset(placeRepository);
    }



    private void verifyFindByIdIsCalledOnce(){
        Mockito.verify(placeRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
        Mockito.reset(placeRepository);
    }

    private void verifyInsertReviewIsCalledOnce(){
        Mockito.verify(placeRepository, VerificationModeFactory.times(1)).insertReview(Mockito.anyLong(),Mockito.anyLong());
        Mockito.reset(placeRepository);
    }
    private void verifyInsertReviewIsNotCalled(){
        Mockito.verify(placeRepository, VerificationModeFactory.times(0)).insertReview(Mockito.anyLong(), Mockito.anyLong());
        Mockito.reset(placeRepository);
    }


}