package tqs.ua.pt.homies_marketplace.models;

import tqs.ua.pt.homies_marketplace.dtos.UserDTO;

import javax.persistence.*;
import java.util.ArrayList;
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

    public User(String email, String password, String firstName, String lastName, String city) {
        this.email = email;
        this.favorites = new ArrayList<>();
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.publishedHouses = new ArrayList<>();
        this.rentedHouses = new ArrayList<>();
    }

    public User(UserDTO userDTO){
        this.email = userDTO.getEmail();
        this.favorites = userDTO.getFavorites();
        this.password = userDTO.getPassword();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.city = userDTO.getCity();
        this.publishedHouses = userDTO.getPublishedHouses();
        this.rentedHouses = userDTO.getRentedHouses();
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
