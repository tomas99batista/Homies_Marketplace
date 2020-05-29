package tqs.ua.pt.homies_marketplace.ControllerUnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.ua.pt.homies_marketplace.JsonUtil;
import tqs.ua.pt.homies_marketplace.controller.UserController;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.PlaceId;
import tqs.ua.pt.homies_marketplace.models.User;
import tqs.ua.pt.homies_marketplace.service.PlaceService;
import tqs.ua.pt.homies_marketplace.service.UserService;

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

@WebMvcTest(UserController.class)
 class UserControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @MockBean
    private PlaceService placeService;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    void whenPostFavorites_thenReturnTrue() throws Exception{
       PlaceId placeId= new PlaceId(1L);
       given(service.addToFavorites("jose@email.com", placeId)).willReturn(true);
       mvc.perform(post("/users/jose@email.com/favorites").contentType(MediaType.APPLICATION_JSON)
               .content(JsonUtil.toJson(placeId))).andReturn().getResponse().getContentAsString().equals("true");
       verify(service, VerificationModeFactory.times(1)).addToFavorites(Mockito.any(), Mockito.any());
       reset(service);
    }

   @Test
   void whenPostPublishedHouse_thenReturnTrue() throws Exception{
      List<String> features= new ArrayList<>();
      features.add("feature1");
      features.add("feature2");
      Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "cityTesting", new ArrayList<>(), "photo1");
      given(service.addPublishedHouse("jose@email.com", place)).willReturn(true);
      mvc.perform(post("/users/jose@email.com/publishedHouses").contentType(MediaType.APPLICATION_JSON)
              .content(JsonUtil.toJson(place))).andReturn().getResponse().getContentAsString().equals("true");
      verify(service, VerificationModeFactory.times(1)).addPublishedHouse(Mockito.any(), Mockito.any());
      reset(service);
   }

   @Test
   void whenPostRentedHouse_thenReturnTrue() throws Exception{
      PlaceId placeId= new PlaceId(1L);
      given(service.addToRentedHouses("jose@email.com", placeId)).willReturn(true);
      mvc.perform(post("/users/jose@email.com/booking").contentType(MediaType.APPLICATION_JSON)
              .content(JsonUtil.toJson(placeId))).andReturn().getResponse().getContentAsString().equals("true");
      verify(service, VerificationModeFactory.times(1)).addToRentedHouses(Mockito.any(), Mockito.any());
      reset(service);
   }

    @Test
     void whenPostUser_thenCreatePUser() throws Exception {
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
        given(service.save(Mockito.any())).willReturn(user);

        mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(user))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("josefrias@email.com")));
        verify(service, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(service);


    }

    @Test
     void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
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
        List<User> allUsers = Arrays.asList(user);

        given(service.getAllUsers()).willReturn(allUsers);

        mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is(user.getEmail())));
        verify(service, VerificationModeFactory.times(1)).getAllUsers();
        reset(service);
    }

    @Test
     void GivenUsersWithPublishedHouses_WhenGetPublishedHouses_thenReturnJsonArray() throws Exception{

        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "city");
        List<Place> allPlaces = Arrays.asList(place);


        given(placeService.getPublishedHouses("josefrias@email.com")).willReturn(allPlaces);

        mvc.perform(get("/users/josefrias@email.com/publishedHouses").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(place.getTitle())));
        verify(placeService, VerificationModeFactory.times(1)).getPublishedHouses("josefrias@email.com");
        reset(placeService);
    }

   @Test
   void GivenUsersWithFavoriteHouses_WhenGetFavoriteHouses_thenReturnJsonArray() throws Exception{

      List<String> features= new ArrayList<>();
      features.add("feature1");
      features.add("feature2");
      Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "city");
      List<Place> allPlaces = Arrays.asList(place);


      given(placeService.getFavoriteHouses("josefrias@email.com")).willReturn(allPlaces);

      mvc.perform(get("/users/josefrias@email.com/favorites").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(1)))
              .andExpect(jsonPath("$[0].title", is(place.getTitle())));
      verify(placeService, VerificationModeFactory.times(1)).getFavoriteHouses("josefrias@email.com");
      reset(placeService);
   }
}
