package tqs.ua.pt.homies_marketplace.IntegrationTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import tqs.ua.pt.homies_marketplace.HomiesMarketplaceApplication;
import tqs.ua.pt.homies_marketplace.JsonUtil;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.Review;
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;
import tqs.ua.pt.homies_marketplace.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
class PlaceControllerITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @AfterEach
    public void resetDb() {
        placeRepository.deleteAll();
    }

    @Test
    void whenPostReview_thenReturnTrue() throws Exception{
        List<String> features= new ArrayList<>();
        List<Long> reviews=new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "city", reviews, "photo1");
        placeRepository.saveAndFlush(place);
        long placeId=place.getId();
        Review review= new Review("jose@email.com", 4.0, "comment1");
        mvc.perform(post("/api/places/"+placeId+"/reviews").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(review))).andReturn().getResponse().getContentAsString().equals(true);
    }

    @Test
    void whenGetReviews_thenReturnReviews() throws Exception{
        List<Long> reviews= new ArrayList<>();
        Review review= new Review("jose@email.com", 4.0, "comment1");

        Review saved=reviewRepository.saveAndFlush(review);
        reviews.add(saved.getId());
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "city", reviews, "photo1");
        placeRepository.saveAndFlush(place);
        long placeId=place.getId();
        mvc.perform(get("/api/places/"+placeId+"/reviews").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is(review.getEmail())));

    }

    @Test
    void whenPostPlace_thenCreatePlace() throws Exception {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "city", new ArrayList<>(), "photo1");
        placeRepository.saveAndFlush(place);

        mvc.perform(post("/api/places").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(place))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("title1")));
    }

    @Test
    void givenPlaces_whenGetPLaces_thenReturnJsonArray() throws Exception {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "city", new ArrayList<>(), "photo1");

        placeRepository.saveAndFlush(place);

        mvc.perform(get("/api/places").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(place.getTitle())));
    }

    @Test
    void givenPlaceId_whenGetPLaceDetails_thenReturnJsonArrayWithPLace() throws Exception {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "city", new ArrayList<>(), "photo1");

        Place saved=placeRepository.saveAndFlush(place);
        mvc.perform(get("/api/places/"+saved.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title", is("title1")));

    }

    @Test
    void givenPlaces_whenSearchByCity_thenReturnJsonArray() throws Exception {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "city", new ArrayList<>(), "photo1");

        placeRepository.saveAndFlush(place);

        mvc.perform(get("/api/search?city=city").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(place.getTitle())));

    }

    @Test
    void givenPlaces_whenSearchByCityAndPriceAndBedroomsAndBathroomsAndRatingAndType_thenReturnJsonArray() throws Exception {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "city", new ArrayList<>(), "photo1");

        placeRepository.saveAndFlush(place);

        mvc.perform(get("/api/search?city=city&price=5&rating=5&bathrooms=1&bedrooms=1&type=type1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(place.getTitle())));

    }

    @Test
    void givenPlaces_whenSearchByMinPriceAndMaxPrice_thenReturnJsonArray() throws Exception {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "city", new ArrayList<>(), "photo1");

        placeRepository.saveAndFlush(place);

        mvc.perform(get("/api/search?minPrice=0&maxPrice=5").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(place.getTitle())));

    }

    @Test
    void givenPlaces_whenSearchByMinPrice_thenReturnJsonArray() throws Exception {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "city", new ArrayList<>(), "photo1");

        placeRepository.saveAndFlush(place);

        mvc.perform(get("/api/search?minPrice=0").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(place.getTitle())));

    }

    @Test
    void givenPlaces_whenSearchByMaxPrice_thenReturnJsonArray() throws Exception {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(null,"title1", 5.0, 5.0,features, 1,1,"type1", "city", new ArrayList<>(), "photo1");

        placeRepository.saveAndFlush(place);

        mvc.perform(get("/api/search?maxPrice=5").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(place.getTitle())));

    }
}
