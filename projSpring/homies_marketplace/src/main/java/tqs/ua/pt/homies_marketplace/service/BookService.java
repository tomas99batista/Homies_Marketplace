package tqs.ua.pt.homies_marketplace.service;

import tqs.ua.pt.homies_marketplace.models.Booking;
import tqs.ua.pt.homies_marketplace.models.Place;
import tqs.ua.pt.homies_marketplace.models.User;

import java.awt.*;
import java.awt.print.Book;
import java.util.ArrayList;

public interface BookService {

    Booking createBooking(Booking booking);

    void deleteBooking(Booking booking);

    Booking getBooking(Place place);

    ArrayList<Booking> getAllBookingsByUser(User user);
}
