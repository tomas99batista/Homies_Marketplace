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
import tqs.ua.pt.homies_marketplace.controller.PlaceController;
import tqs.ua.pt.homies_marketplace.dtos.PlaceDTO;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.Review;
import tqs.ua.pt.homies_marketplace.service.PlaceService;
import tqs.ua.pt.homies_marketplace.service.PlaceServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.reset;

@WebMvcTest(PlaceController.class)
 class PlaceControllerUnitTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlaceServiceImpl service;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    void whenPostReview_thenReturnTrue() throws Exception{
       Review review= new Review("jose@email.com", 4.0, "comment1");
       given(service.addReview(Mockito.anyLong(), Mockito.any())).willReturn(true);
       mvc.perform(post("/api/places/1/reviews").contentType(MediaType.APPLICATION_JSON)
               .content(JsonUtil.toJson(review)))
               .andExpect(jsonPath("$.success", is(true)));
       verify(service, VerificationModeFactory.times(1)).addReview(Mockito.anyLong(), Mockito.any());
       reset(service);
    }
    @Test
    void whenGetReviews_thenReturnReviews() throws Exception{
       List<Review> reviews= new ArrayList<>();
       Review review= new Review("jose@email.com", 4.0, "comment1");
       reviews.add(review);

       given(service.getReviews(1L)).willReturn(reviews);

       mvc.perform(get("/api/places/1/reviews").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].email", is(review.getEmail())));
       verify(service, VerificationModeFactory.times(1)).getReviews(1L);
       reset(service);
    }



    @Test
     void whenPostPlace_thenCreatePlace() throws Exception {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place("title1", 5.0, features, 1,1,"type1", "city", "photo1");
        given(service.save(Mockito.any())).willReturn(place);

        mvc.perform(post("/api/places").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(place))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("title1")));
        verify(service, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(service);


    }


    @Test
     void givenPlaces_whenGetPLaces_thenReturnJsonArray() throws Exception {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place("title1", 5.0, features, 1,1,"type1", "city", "photo1");
        List<Place> allPlaces = Arrays.asList(place);

        given(service.getAllPlaces()).willReturn(allPlaces);

        mvc.perform(get("/api/places").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(place.getTitle())));
        verify(service, VerificationModeFactory.times(1)).getAllPlaces();
        reset(service);
    }

    @Test
     void givenPlaceId_whenGetPLaceDetails_thenReturnJsonArrayWithPLace() throws Exception {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place("title1", 5.0, features, 1,1,"type1", "city", "photo1");

        given(service.getPlaceById(Mockito.anyLong())).willReturn(place);

        mvc.perform(get("/api/places/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title", is("title1")));
        verify(service, VerificationModeFactory.times(1)).getPlaceById(1L);
        reset(service);
    }

    @Test
     void givenPlaces_whenSearchByCity_thenReturnJsonArray() throws Exception {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place("title1", 5.0, features, 1,1,"type1", "aveiro", "photo1");
        List<Place> allPlaces = Arrays.asList(place);
        PlaceDTO placeDTO= new PlaceDTO(place.getCity(), null, null, null, null, null);
        given(service.search(Mockito.any(), Mockito.anyString(), Mockito.anyString())).willReturn(allPlaces);
        mvc.perform(get("/api/search?city=aveiro&minPrice=0&maxPrice=5").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(place.getTitle())));
        verify(service, VerificationModeFactory.times(1)).search(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        reset(service);
    }


}
