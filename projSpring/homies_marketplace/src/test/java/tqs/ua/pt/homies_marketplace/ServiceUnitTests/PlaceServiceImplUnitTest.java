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
import tqs.ua.pt.homies_marketplace.repository.PlaceRepository;
import tqs.ua.pt.homies_marketplace.service.PlaceServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PlaceServiceImplUnitTest {

    @Mock( lenient = true)
    private PlaceRepository placeRepository;


    @InjectMocks
    private PlaceServiceImpl placeService;

    @BeforeEach
    public void setUp(){
        List<String> features= new ArrayList<>();
        features.add("feature1");
        features.add("feature2");
        Place place= new Place(1L,"title1", 5.0, 5.0,features, 1,1,"type1", "city");
        List<Place> places=new ArrayList<>();
        places.add(place);


        Mockito.when(placeRepository.findByCity("city")).thenReturn(places);
        Mockito.when(placeRepository.findByCity("aveiro")).thenReturn(null);
        Mockito.when(placeRepository.findById(1L)).thenReturn(place);
        Mockito.when(placeRepository.findById(-1L)).thenReturn(null);
    }

    @Test
    public void whenValidPlaceId_thenPlaceShouldBeFound(){
        Place fromDb=placeService.getPlaceById(1L);
        assertThat(fromDb.getTitle()).isEqualTo("title1");

        verifyFindByIdIsCalledOnce();
    }

    @Test
    public void whenInvalidPlaceId_thenPlaceShouldNotBeFound() {
        Place fromDb = placeService.getPlaceById(-1L);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
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

    @Test
    public void WhenSearchByExistingCity_thenPlaceShouldBeFound(){
        List<Place> fromDb=placeService.searchByCity("city");
        verifyfindByCityIsCalledOnce("city");
        assertThat(fromDb).hasSize(1).extracting(Place::getId).contains(1L);

    }

    @Test
    public void WhenSearchByNonExistingCity_thenPlaceShouldNotBeFound(){
        List<Place> fromDb=placeService.searchByCity("aveiro");
        verifyfindByCityIsCalledOnce("aveiro");
        assertThat(fromDb).isNull();

    }



    private void verifyfindByCityIsCalledOnce(String city) {
        Mockito.verify(placeRepository, VerificationModeFactory.times(1)).findByCity(city);
        Mockito.reset(placeRepository);
    }

    private void verifyFindByIdIsCalledOnce(){
        Mockito.verify(placeRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
        Mockito.reset(placeRepository);
    }
}
