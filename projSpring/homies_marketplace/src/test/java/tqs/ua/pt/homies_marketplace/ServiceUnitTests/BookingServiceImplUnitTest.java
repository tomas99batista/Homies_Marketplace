package tqs.ua.pt.homies_marketplace.ServiceUnitTests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.ua.pt.homies_marketplace.models.Booking;
import tqs.ua.pt.homies_marketplace.repository.BookingRepository;
import tqs.ua.pt.homies_marketplace.service.BookServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplUnitTest {

    @Mock(lenient = true)
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp(){

        Booking booking= new Booking("owner@email.com", "requester@email.com", 1L);
        Mockito.when(bookingRepository.save(booking)).thenReturn(booking);
    }


    @Test
    void whenSaveBooking_thenBookingShouldBeSaved(){
        Booking booking= new Booking("owner@email.com", "requester@email.com", 1L);
        Mockito.when(bookingRepository.save(booking)).thenReturn(booking);
        Booking saved=bookService.createBooking(booking);
        assertThat(saved.getOwner()).isEqualTo(booking.getOwner());
        assertThat(saved.getId()).isEqualTo(booking.getId());
        assertThat(saved.getPlaceId()).isEqualTo(booking.getPlaceId());
        assertThat(saved.getRequester()).isEqualTo(booking.getRequester());
    }



}
