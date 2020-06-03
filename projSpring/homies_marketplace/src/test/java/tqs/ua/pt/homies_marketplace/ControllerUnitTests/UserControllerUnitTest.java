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
import tqs.ua.pt.homies_marketplace.dtos.UserDTO;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.PlaceId;
import tqs.ua.pt.homies_marketplace.models.User;
import tqs.ua.pt.homies_marketplace.service.PlaceService;
import tqs.ua.pt.homies_marketplace.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void WhenGetUserByEmail_IfUserExists_thenReturnUser() throws Exception{


       String email="josefrias@email.com";
       String password="password";
       String firstName="Jose";
       String lastName="Frias";
       String city="Aveiro";

       User user= new User(email, password, firstName, lastName, city);
       given(service.getUserByEmail("jose@email.com")).willReturn(user);
       mvc.perform(get("/api/users/jose@email.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
               .andExpect(jsonPath("$.email", is(user.getEmail())));
       verify(service, VerificationModeFactory.times(1)).getUserByEmail("jose@email.com");
       reset(service);
    }

   @Test
   void whenPostLogin_IfPasswordIsNotEqual_thenReturnNull() throws Exception{

      String email="josefrias@email.com";
      String password="password";
      String firstName="Jose";
      String lastName="Frias";
      String city="Aveiro";

      User user= new User(email, password, firstName, lastName, city);
      UserDTO userDTO=new UserDTO(email, "wrong_password", firstName, lastName, city);
      given(service.exists("josefrias@email.com")).willReturn(true);
      given(service.getUserByEmail("josefrias@email.com")).willReturn(user);
      mvc.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
              .content(JsonUtil.toJson(userDTO))).andReturn().getResponse().getContentAsString().equals(null);;
      verify(service, VerificationModeFactory.times(1)).exists(Mockito.anyString());
      reset(service);
   }

   @Test
   void whenPostLogin_IfEmailNotExists_thenReturnNull() throws Exception{

      String email="josefrias@email.com";
      String password="password";
      String firstName="Jose";
      String lastName="Frias";
      String city="Aveiro";

      User user= new User(email,  password, firstName, lastName, city);
      UserDTO userDTO=new UserDTO(email,  password, firstName, lastName, city);
      given(service.exists("josefrias@email.com")).willReturn(false);
      given(service.getUserByEmail("josefrias@email.com")).willReturn(user);
      mvc.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
              .content(JsonUtil.toJson(userDTO))).andReturn().getResponse().getContentAsString().equals(null);;
      verify(service, VerificationModeFactory.times(1)).exists(Mockito.anyString());
      reset(service);
   }


    @Test
    void whenPostLogin_IfValidCredentials_thenReturnUser() throws Exception{

       String email="josefrias@email.com";
       String password="password";
       String firstName="Jose";
       String lastName="Frias";
       String city="Aveiro";

       User user= new User(email, password, firstName, lastName, city);
       UserDTO userDTO=new UserDTO(email,  password, firstName, lastName, city);
       given(service.exists("josefrias@email.com")).willReturn(true);
       given(service.getUserByEmail("josefrias@email.com")).willReturn(user);
       mvc.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
               .content(JsonUtil.toJson(userDTO))).andExpect(jsonPath("$.email", is("josefrias@email.com")));
       verify(service, VerificationModeFactory.times(1)).exists(Mockito.anyString());
       reset(service);
    }




    @Test
    void whenPostFavorites_thenReturnTrue() throws Exception{
       PlaceId placeId= new PlaceId(1L);
       given(service.addToFavorites(Mockito.anyString(), Mockito.any())).willReturn(true);
       mvc.perform(post("/api/users/jose@email.com/favorites").contentType(MediaType.APPLICATION_JSON)
               .content(JsonUtil.toJson(placeId))).andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
       verify(service, VerificationModeFactory.times(1)).addToFavorites(Mockito.any(), Mockito.any());
       reset(service);
    }

   @Test
   void whenPostPublishedHouse_thenReturnTrue() throws Exception{
      List<String> features= new ArrayList<>();
      features.add("feature1");
      features.add("feature2");
      Place place= new Place("title1", 5.0, features, 1,1,"type1", "cityTesting","photo1");
      given(service.addPublishedHouse("jose@email.com", place)).willReturn(true);
      mvc.perform(post("/api/users/jose@email.com/publishedHouses").contentType(MediaType.APPLICATION_JSON)
              .content(JsonUtil.toJson(place))).andReturn().getResponse().getContentAsString().equals("true");
      verify(service, VerificationModeFactory.times(1)).addPublishedHouse(Mockito.any(), Mockito.any());
      reset(service);
   }

   @Test
   void whenPostRentedHouse_thenReturnTrue() throws Exception{
      PlaceId placeId= new PlaceId(1L);
      given(service.addToRentedHouses("jose@email.com", placeId)).willReturn(true);
      mvc.perform(post("/api/users/jose@email.com/booking").contentType(MediaType.APPLICATION_JSON)
              .content(JsonUtil.toJson(placeId))).andReturn().getResponse().getContentAsString().equals("true");
      verify(service, VerificationModeFactory.times(1)).addToRentedHouses(Mockito.any(), Mockito.any());
      reset(service);
   }

    @Test
     void whenPostUser_thenCreatePUser() throws Exception {

        String email="josefrias@email.com";
        String password="password";
        String firstName="Jose";
        String lastName="Frias";
        String city="Aveiro";

        User user= new User(email, password, firstName, lastName, city);
        given(service.save(Mockito.any())).willReturn(user);

        mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(user))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("josefrias@email.com")));
        verify(service, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(service);


    }

    @Test
     void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {


        String email="josefrias@email.com";
        String password="password";
        String firstName="Jose";
        String lastName="Frias";
        String city="Aveiro";

        User user= new User(email, password, firstName, lastName, city);
        List<User> allUsers = Arrays.asList(user);

        given(service.getAllUsers()).willReturn(allUsers);

        mvc.perform(get("/api/users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
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
        Place place= new Place("title1", 5.0, features, 1,1,"type1", "city", "photo1");
        List<Place> allPlaces = Arrays.asList(place);


        given(placeService.getPublishedHouses("josefrias@email.com")).willReturn(allPlaces);

        mvc.perform(get("/api/users/josefrias@email.com/publishedHouses").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
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
      Place place= new Place("title1", 5.0,features, 1,1,"type1", "city", "photo1");
      List<Place> allPlaces = Arrays.asList(place);


      given(placeService.getFavoriteHouses("josefrias@email.com")).willReturn(allPlaces);

      mvc.perform(get("/api/users/josefrias@email.com/favorites").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(1)))
              .andExpect(jsonPath("$[0].title", is(place.getTitle())));
      verify(placeService, VerificationModeFactory.times(1)).getFavoriteHouses("josefrias@email.com");
      reset(placeService);
   }

    @Test
    void GivenUsersWithFavoriteHouses_WhenDeleteFavoriteHouses_thenReturnTrue() throws Exception{

        PlaceId placeId= new PlaceId(1L);
        Mockito.when(service.removeFavoritePlace(Mockito.anyString(),Mockito.any())).thenReturn(true);
        mvc.perform(delete("/api/users/josefrias@email.com/favorites").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(placeId))).andExpect(jsonPath("$.success", is(true)));

    }
}