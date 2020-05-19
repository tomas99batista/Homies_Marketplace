package tqs.ua.pt.homies_marketplace.IntegrationTests;

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
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.service.PlaceService;

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
public class PlaceControllerITest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlaceService service;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    public void whenPostPlace_thenCreatePlace() throws Exception {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "city");
        given(service.save(Mockito.any())).willReturn(place);

        mvc.perform(post("/places").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(place))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("title1")));
        verify(service, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(service);


    }

    @Test
    public void givenPlaces_whenGetPLaces_thenReturnJsonArray() throws Exception {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "city");
        List<Place> allPlaces = Arrays.asList(place);

        given(service.getAllPlaces()).willReturn(allPlaces);

        mvc.perform(get("/places").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(place.getTitle())));
        verify(service, VerificationModeFactory.times(1)).getAllPlaces();
        reset(service);
    }

    @Test
    public void givenPlaceId_whenGetPLaceDetails_thenReturnJsonArrayWithPLace() throws Exception {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(1L,"title1", 5.0, 5.0,features, 1,1,"type1", "city");

        given(service.getPlaceById(1L)).willReturn(place);

        mvc.perform(get("/places/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title", is("title1")));
        verify(service, VerificationModeFactory.times(1)).getPlaceById(1L);
        reset(service);
    }

    @Test
    public void givenPlaces_whenSearchByCity_thenReturnJsonArray() throws Exception {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "city");
        List<Place> allPlaces = Arrays.asList(place);

        given(service.searchByCity("city")).willReturn(allPlaces);

        mvc.perform(get("/search?city=city").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(place.getTitle())));
        verify(service, VerificationModeFactory.times(1)).searchByCity("city");
        reset(service);
    }


}