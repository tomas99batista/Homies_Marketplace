package tqs.ua.pt.homies_marketplace.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity(name="users")
@Table(name="users")
public class User {
    @Id
    @Column(nullable=false, unique=false)
    private String email;

    @ElementCollection
    private List<Long> favourites; // Vai ser uma lista c/ os ids probably, qql coisa muda-se mais logo dont worry

    @Column(nullable=false, unique=false)
    private String password; // Isto ñ vai ser assim, vai ter de se guardar salt e hashed PWD

    @Column(nullable=false, unique=false)
    private String first_name;

    @Column(nullable=false, unique=false)
    private String last_name;

    @Column(nullable=false, unique=false)
    private String city;

    @ElementCollection
    private List<Long> published_houses;

    @ElementCollection
    private List<Long> rented_houses;

    //@Column(nullable=false, unique=false)
    //private String profile_photo;
}
