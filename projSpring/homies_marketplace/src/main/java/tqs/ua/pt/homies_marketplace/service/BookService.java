package tqs.ua.pt.homies_marketplace.service;

import tqs.ua.pt.homies_marketplace.models.Booking;
import tqs.ua.pt.homies_marketplace.models.Place;

import java.awt.print.Book;

public interface BookService {

    Booking createBooking(Booking booking);

    void deleteBooking(Booking booking);

    Booking getBooking(Place place);
}
