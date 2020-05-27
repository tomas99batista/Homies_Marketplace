package tqs.ua.pt.homies_marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tqs.ua.pt.homies_marketplace.models.Booking;
import tqs.ua.pt.homies_marketplace.repository.BookingRepository;

@Service
@Transactional
public class BookServiceImpl implements BookService{

    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Booking booking){
        return bookingRepository.save(booking);
    }
}
