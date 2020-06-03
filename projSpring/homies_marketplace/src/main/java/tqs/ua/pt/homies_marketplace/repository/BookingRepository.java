package tqs.ua.pt.homies_marketplace.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tqs.ua.pt.homies_marketplace.models.Booking;

@Repository
@Transactional
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking getByPlaceId(long placeId);

}
