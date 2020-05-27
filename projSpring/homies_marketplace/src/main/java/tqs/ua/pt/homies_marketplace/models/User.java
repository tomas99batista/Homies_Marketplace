package tqs.ua.pt.homies_marketplace.models;

import javax.persistence.*;
import java.util.List;

@Entity(name="users")
@Table(name="users")
public class User {
    @Id
    @Column(nullable=false, unique=false)
    private String email;

    @ElementCollection
    private List<Long> favorites;

    @Column(nullable=false, unique=false)
    private String password;

    @Column(name="first_name",nullable=false, unique=false)
    private String firstName;

    @Column(name="last_name",nullable=false, unique=false)
    private String lastName;

    private String city;

    @ElementCollection
    private List<Long> publishedHouses;

    @ElementCollection
    private List<Long> rentedHouses;



    //needed for hibernate
    public User() {
    }

    public User(String email, List<Long> favorites, String password, String firstName, String lastName, String city, List<Long> publishedHouses, List<Long> rentedHouses) {
        this.email = email;
        this.favorites = favorites;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.publishedHouses = publishedHouses;
        this.rentedHouses = rentedHouses;
    }

    public String getEmail() {
        return email;
    }

    public List<Long> getFavorites() {
        return favorites;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public List<Long> getPublishedHouses() {
        return publishedHouses;
    }

    public List<Long> getRentedHouses() {
        return rentedHouses;
    }
}
