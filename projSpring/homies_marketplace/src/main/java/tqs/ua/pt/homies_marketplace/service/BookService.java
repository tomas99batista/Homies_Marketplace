package tqs.ua.pt.homies_marketplace.service;

import tqs.ua.pt.homies_marketplace.models.Booking;

import java.util.List;

public interface BookService {

    Booking createBooking(Booking booking);

    void deleteBooking(Booking booking);

    Booking getBooking(long placeId);

    List<Booking> getAllBookingsByEmail(String email);
}
