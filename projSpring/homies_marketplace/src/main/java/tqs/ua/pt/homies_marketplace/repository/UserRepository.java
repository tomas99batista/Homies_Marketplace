package tqs.ua.pt.homies_marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.ua.pt.homies_marketplace.models.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
