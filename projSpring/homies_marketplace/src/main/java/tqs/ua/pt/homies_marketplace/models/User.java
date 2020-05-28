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
    private List<Long> favorites; // Vai ser uma lista c/ os ids probably, qql coisa muda-se mais logo dont worry

    @Column(nullable=false, unique=false)
    private String password; // Isto ñ vai ser assim, vai ter de se guardar salt e hashed PWD

    @Column(name="first_name",nullable=false, unique=false)
    private String firstName;

    @Column(name="last_name",nullable=false, unique=false)
    private String lastName;

   // @Column(name="city",nullable=false, unique=false)
    private String city;

    @ElementCollection
    private List<Long> publishedHouses;

    @ElementCollection
    private List<Long> rentedHouses;

    //@Column(nullable=false, unique=false)
    //private String profile_photo;

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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFavorites(List<Long> favorites) {
        this.favorites = favorites;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPublishedHouses(List<Long> publishedHouses) {
        this.publishedHouses = publishedHouses;
    }

    public void setRentedHouses(List<Long> rentedHouses) {
        this.rentedHouses = rentedHouses;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", favorites=" + favorites +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", publishedHouses=" + publishedHouses +
                ", rentedHouses=" + rentedHouses +
                '}';
    }
}
