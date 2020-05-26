package tqs.ua.pt.homies_marketplace.IntegrationTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.ua.pt.homies_marketplace.HomiesMarketplaceApplication;
import tqs.ua.pt.homies_marketplace.JsonUtil;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.PlaceId;
import tqs.ua.pt.homies_marketplace.models.User;
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;
import tqs.ua.pt.homies_marketplace.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = HomiesMarketplaceApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class UserControllerITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;
    @AfterEach
    public void resetDb() {
        userRepository.deleteAll();
    }

    @Test
    void whenPostFavorites_thenReturnTrue() throws Exception{
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "cityTesting", new ArrayList<>(), "photo1");
        Place saved=placeRepository.saveAndFlush(place);
        List<Long> favorites= new ArrayList<>();
        List<Long> publishedHouses= new ArrayList<>();
        List<Long> rentedHouses= new ArrayList<>();
        String email="jose@email.com";
        String password="password";
        String firstName="Jose";
        String lastName="Frias";
        String city="Aveiro";
        User user= new User(email, favorites, password, firstName, lastName, city, publishedHouses, rentedHouses);
        PlaceId placeId= new PlaceId(saved.getId());
        userRepository.saveAndFlush(user);
        mvc.perform(post("/users/jose@email.com/favorites").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(placeId))).andReturn().getResponse().getContentAsString().equals("true");
    }

    @Test
    void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
        List<Long> favorites= new ArrayList<>();
        List<Long> publishedHouses= new ArrayList<>();
        List<Long> rentedHouses= new ArrayList<>();
        String email="josefrias@email.com";
        String password="password";
        String firstName="Jose";
        String lastName="Frias";
        String city="Aveiro";

        User user= new User(email, favorites, password, firstName, lastName, city, publishedHouses, rentedHouses);
        userRepository.saveAndFlush(user);

        mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is(user.getEmail())));
    }

    @Test
    void GivenUsersWithPublishedHouses_WhenGetPublishedHouses_thenReturnJsonArray() throws Exception{
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "cityTesting", new ArrayList<>(), "photo1");
        Place saved=placeRepository.saveAndFlush(place);
        List<Long> favorites= new ArrayList<>();
        List<Long> publishedHouses= new ArrayList<>();
        publishedHouses.add(saved.getId());
        List<Long> rentedHouses= new ArrayList<>();

        String email="josefrias@email.com";
        String password="password";
        String firstName="Jose";
        String lastName="Frias";
        String city="Aveiro";
        User user= new User(email, favorites, password, firstName, lastName, city, publishedHouses, rentedHouses);
        userRepository.saveAndFlush(user);
        mvc.perform(get("/users/josefrias@email.com/publishedHouses").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(place.getTitle())));

    }

    @Test
    void GivenUsersWithFavoriteHouses_WhenGetFavoriteHouses_thenReturnJsonArray() throws Exception{
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "cityTesting", new ArrayList<>(), "photo1");
        Place saved=placeRepository.saveAndFlush(place);
        List<Long> favorites= new ArrayList<>();
        List<Long> publishedHouses= new ArrayList<>();
        favorites.add(saved.getId());
        List<Long> rentedHouses= new ArrayList<>();

        String email="josefrias@email.com";
        String password="password";
        String firstName="Jose";
        String lastName="Frias";
        String city="Aveiro";
        User user= new User(email, favorites, password, firstName, lastName, city, publishedHouses, rentedHouses);
        userRepository.saveAndFlush(user);
        mvc.perform(get("/users/josefrias@email.com/favorites").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(place.getTitle())));
    }
}

