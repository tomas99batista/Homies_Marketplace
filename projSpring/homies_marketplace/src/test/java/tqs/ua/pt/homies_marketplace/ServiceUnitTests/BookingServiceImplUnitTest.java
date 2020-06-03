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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

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


    @Test
    void whenGetBookingByPlace_IfPlaceInBookingExists_thenReturnBooking(){
        Booking booking= new Booking("owner@email.com", "requester@email.com", 1L);
        Mockito.when(bookingRepository.getByPlaceId(Mockito.anyLong())).thenReturn(booking);
        Booking fromDb=bookService.getBooking(1L);
        assertThat(fromDb.getRequester()).isEqualTo(booking.getRequester());
        assertThat(fromDb.getOwner()).isEqualTo(booking.getOwner());
        assertThat(fromDb.getPlaceId()).isEqualTo(booking.getPlaceId());
    }

    @Test
    void whenGetBookingByPlace_IfPlaceInBookingNotExists_thenReturnNull(){
        Booking booking= new Booking("owner@email.com", "requester@email.com", 1L);
        Mockito.when(bookingRepository.getByPlaceId(Mockito.anyLong())).thenReturn(null);
        Booking fromDb=bookService.getBooking(1L);
        assertThat(fromDb).isEqualTo(null);
    }

    @Test
    void whenGetBookingsByEmail_IfEmailInBookingExists_thenReturnBookings(){
        List<Booking> bookings = new ArrayList<>();
        Booking booking= new Booking("owner@email.com", "requester@email.com", 1L);
        bookings.add(booking);
        Mockito.when(bookingRepository.getAllByOwner("owner@email.com")).thenReturn(bookings);
        List<Booking> fromDb=bookService.getAllBookingsByEmail("owner@email.com");
        assertThat(fromDb).hasSize(1).extracting(Booking::getOwner).contains("owner@email.com");
    }

    @Test
    void whenGetBookingsByEmail_IfEmailNotInBookingExists_thenReturnEmptyBookings(){
        List<Booking> bookings = new ArrayList<>();
        Mockito.when(bookingRepository.getAllByOwner("owner@email.com")).thenReturn(bookings);
        List<Booking> fromDb=bookService.getAllBookingsByEmail("owner@email.com");
        assertThat(fromDb).hasSize(0);
    }




}
