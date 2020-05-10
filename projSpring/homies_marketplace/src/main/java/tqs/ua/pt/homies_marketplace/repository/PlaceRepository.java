package tqs.ua.pt.homies_marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.ua.pt.homies_marketplace.models.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Place findById(long id);
}
