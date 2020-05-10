package tqs.ua.pt.homies_marketplace.ServiceUnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;
import tqs.ua.pt.homies_marketplace.service.PlaceServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PlaceServiceImplUnitTest {

    @Mock( lenient = true)
    private PlaceRepository placeRepository;


    @InjectMocks
    private PlaceServiceImpl placeService;

    @BeforeEach
    public void setUp(){

    }

    @Test
    public void whenPostPlace_thenPlaceShouldBeSaved() {
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(1L,"title1", 5.0, 5.0,features, 1,1,"type1", "city");
        Mockito.when(placeRepository.save(place)).thenReturn(place);
        Place saved = placeService.save(place);

        assertThat(saved.getTitle()).isEqualTo(place.getTitle());

    }
}
