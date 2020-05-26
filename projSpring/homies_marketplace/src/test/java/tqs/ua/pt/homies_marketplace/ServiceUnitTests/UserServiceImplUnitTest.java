package tqs.ua.pt.homies_marketplace.ServiceUnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.PlaceId;
import tqs.ua.pt.homies_marketplace.models.Review;
import tqs.ua.pt.homies_marketplace.models.User;
import tqs.ua.pt.homies_marketplace.repository.UserRepository;
import tqs.ua.pt.homies_marketplace.service.PlaceServiceImpl;
import tqs.ua.pt.homies_marketplace.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {

    @Mock(lenient = true)
    private UserRepository userRepository;

    @Mock(lenient = true)
    private PlaceServiceImpl placeService;


    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
     void setUp(){
        List<Long> favorites= new ArrayList<>();
        favorites.add(1L);
        favorites.add(2L);

        List<Long> publishedHouses= new ArrayList<>();
        publishedHouses.add(3L);
        publishedHouses.add(4L);

        List<Long> rentedHouses= new ArrayList<>();
        rentedHouses.add(5L);
        rentedHouses.add(6L);

        String email="jose@email.com";
        String password="password";
        String firstName="Jose";
        String lastName="Frias";
        String city="Aveiro";

        User user= new User(email, favorites, password, firstName, lastName, city, publishedHouses, rentedHouses);

        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(1L,"title1", 5.0, 5.0,features, 1,1,"type1", "city");

        List<User> users= new ArrayList<>();
        users.add(user);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        Mockito.when(userRepository.insertPublishedHouse(email, place.getId())).thenReturn(1);
        Mockito.when(userRepository.insertRentedHouse(email, place.getId())).thenReturn(1);
    }

    @Test
    void whenFindAll_thenGetAllUsers(){
        List<User> allUsers=userService.getAllUsers();
        assertThat(allUsers).hasSize(1).extracting(User::getEmail).contains("jose@email.com");

    }
    //test save of users
    @Test
     void whenPostUser_thenUserShouldBeSaved() {
        List<Long> favorites= new ArrayList<>();
        favorites.add(1L);
        favorites.add(2L);

        List<Long> publishedHouses= new ArrayList<>();
        publishedHouses.add(3L);
        publishedHouses.add(4L);

        List<Long> rentedHouses= new ArrayList<>();
        rentedHouses.add(5L);
        rentedHouses.add(6L);

        String email="jose@email.com";
        String password="password";
        String firstName="Jose";
        String lastName="Frias";
        String city="Aveiro";

        User user= new User(email, favorites, password, firstName, lastName, city, publishedHouses, rentedHouses);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User saved = userService.save(user);
        assertThat(saved.getEmail()).isEqualTo(user.getEmail());

    }

    //test if email exists then user should be found
    @Test
     void whenValidEmail_thenUserShouldBeFound(){
        String email="jose@email.com";
        User found=userService.getUserByEmail(email);
        assertThat(found.getEmail()).isEqualTo(email);

    }


    //test if it is email that does not exist, then no user should be found
    @Test
     void whenInValidEmail_thenUserShouldNotBeFound() {
        User fromDb = userService.getUserByEmail("wrong_email");
        assertThat(fromDb).isNull();

        verifyFindByEmailIsCalledOnce("wrong_email");
    }


    //test add published houses
    @Test
     void WhenValidEmail_thenPublishNewHouse(){
        String email="jose@email.com";
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(1L,"title1", 5.0, 5.0,features, 1,1,"type1", "city");
        Mockito.when(userRepository.insertPublishedHouse(email, place.getId())).thenReturn(1);
        Mockito.when(placeService.save(place)).thenReturn(place);
        boolean saved=userService.addPublishedHouse(email, place);
        assertThat(saved).isEqualTo(true);
        verifyInsertNewPublishedHouseIsCalledOnce(email, 1L);

    }

    @Test
     void WhenValidEmailAndPlacesButRowsNotInserted_thenPublishShouldNotSaved(){
        String email="jose@email.com";
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(1L,"title1", 5.0, 5.0,features, 1,1,"type1", "city");
        Mockito.when(userRepository.insertPublishedHouse(email, place.getId())).thenReturn(0);
        Mockito.when(placeService.save(place)).thenReturn(place);
        boolean saved=userService.addPublishedHouse(email, place);
        assertThat(saved).isEqualTo(false);
        verifyInsertNewPublishedHouseIsCalledOnce(email, 1L);
    }

    @Test
     void WhenValidEmailButNullPlace_thenPublishShouldNotSaved(){
        String email="jose@email.com";
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(1L,"title1", 5.0, 5.0,features, 1,1,"type1", "city");
        Mockito.when(userRepository.insertPublishedHouse(email, place.getId())).thenReturn(1);
        Mockito.when(placeService.save(place)).thenReturn(null);
        boolean saved=userService.addPublishedHouse(email, place);
        assertThat(saved).isEqualTo(false);
        verifyInsertNewPublishedHouseIsNotCalled(email, 1L);
    }

    @Test
     void whenInvalidEmail_thenPublishNewHouseNotOccurs(){
        String email="wrong_email@email.com";
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(1L,"title1", 5.0, 5.0,features, 1,1,"type1", "city");

        boolean bool=userService.addPublishedHouse(email, place);
        assertThat(bool).isEqualTo(false);
        verifyInsertNewPublishedHouseIsNotCalled(email, place.getId());
    }

    @Test
     void WhenValidEmailAndPlaceExists_thenAddToFavorites(){
        String email="jose@email.com";
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(1L,"title1", 5.0, 5.0,features, 1,1,"type1", "city");
        Mockito.when(userRepository.insertFavoriteHouse(email, 1L)).thenReturn(1);
        Mockito.when(placeService.getPlaceById(1L)).thenReturn(place);
        boolean saved=userService.addToFavorites(email, new PlaceId(place.getId()));
        assertThat(saved).isEqualTo(true);
        verifyInsertNewFavoriteHouseIsCalledOnce(email, 1L);

    }

    @Test
     void WhenInValidEmailAndPlaceExists_thenNotAddsToFavorites(){
        String email="wrong_email@email.com";
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(1L,"title1", 5.0, 5.0,features, 1,1,"type1", "city");
        Mockito.when(userRepository.findByEmail(email)).thenReturn(null);
        Mockito.when(placeService.getPlaceById(1L)).thenReturn(place);
        boolean saved=userService.addToFavorites(email, new PlaceId(place.getId()));
        assertThat(saved).isEqualTo(false);
        verifyInsertNewFavoriteHouseIsNotCalled(email, 1L);

    }

    @Test
     void WhenValidEmailAndPlaceNotExists_thenNotAddsToFavorites(){
        String email="jose@email.com";
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(1L,"title1", 5.0, 5.0,features, 1,1,"type1", "city");
        Mockito.when(placeService.getPlaceById(1L)).thenReturn(null);
        boolean saved=userService.addToFavorites(email, new PlaceId(place.getId()));
        assertThat(saved).isEqualTo(false);
        verifyInsertNewFavoriteHouseIsNotCalled(email, 1L);

    }

    @Test
     void WhenValidEmailAndPlaceExists_thenAddToRentedHouses(){
        String email="jose@email.com";
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(1L,"title1", 5.0, 5.0,features, 1,1,"type1", "city");
        Mockito.when(userRepository.insertRentedHouse(email, 1L)).thenReturn(1);
        Mockito.when(placeService.getPlaceById(1L)).thenReturn(place);
        boolean saved=userService.addToRentedHouses(email, new PlaceId(place.getId()));
        assertThat(saved).isEqualTo(true);
        verifyInsertNewRentedHouseHouseIsCalledOnce(email, 1L);
    }

    @Test
     void WhenInValidEmailAndPlaceExists_thenNotAddsToRentedHouses(){
        String email="wrong_email@email.com";
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(1L,"title1", 5.0, 5.0,features, 1,1,"type1", "city");
        Mockito.when(userRepository.findByEmail(email)).thenReturn(null);
        Mockito.when(placeService.getPlaceById(1L)).thenReturn(place);
        boolean saved=userService.addToRentedHouses(email, new PlaceId(place.getId()));
        assertThat(saved).isEqualTo(false);
        verifyInsertNewRentedHouseHouseIsNotCalled(email, 1L);

    }

    @Test
     void WhenValidEmailAndPlaceNotExists_thenNotAddsToRentedHouses(){
        String email="jose@email.com";
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(1L,"title1", 5.0, 5.0,features, 1,1,"type1", "city");
        Mockito.when(placeService.getPlaceById(1L)).thenReturn(null);
        boolean saved=userService.addToRentedHouses(email, new PlaceId(place.getId()));
        assertThat(saved).isEqualTo(false);
        verifyInsertNewRentedHouseHouseIsNotCalled(email, 1L);

    }

    //Verify if methods are called
    private void verifyFindByEmailIsCalledOnce(String email) {
        Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByEmail(email);
        Mockito.reset(userRepository);
    }


    private void verifyInsertNewPublishedHouseIsCalledOnce(String email, long placeId){
        Mockito.verify(userRepository, VerificationModeFactory.times(1)).insertPublishedHouse(email, placeId);
        Mockito.reset(userRepository);
    }

    private void verifyInsertNewPublishedHouseIsNotCalled(String email, long placeId){
        Mockito.verify(userRepository, VerificationModeFactory.times(0)).insertPublishedHouse(email, placeId);
        Mockito.reset(userRepository);
    }

    private void verifyInsertNewFavoriteHouseIsCalledOnce(String email, long placeId){
        Mockito.verify(userRepository, VerificationModeFactory.times(1)).insertFavoriteHouse(email, placeId);
        Mockito.reset(userRepository);
    }

    private void verifyInsertNewFavoriteHouseIsNotCalled(String email, long placeId){
        Mockito.verify(userRepository, VerificationModeFactory.times(0)).insertFavoriteHouse(email, placeId);
        Mockito.reset(userRepository);
    }

    private void verifyInsertNewRentedHouseHouseIsCalledOnce(String email, long placeId){
        Mockito.verify(userRepository, VerificationModeFactory.times(1)).insertRentedHouse(email, placeId);
        Mockito.reset(userRepository);
    }

    private void verifyInsertNewRentedHouseHouseIsNotCalled(String email, long placeId){
        Mockito.verify(userRepository, VerificationModeFactory.times(0)).insertRentedHouse(email, placeId);
        Mockito.reset(userRepository);
    }


}
