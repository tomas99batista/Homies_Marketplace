package tqs.ua.pt.homies_marketplace.dtos;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private String email;
    private List<Long> favorites;
    private String password;
    private String firstName;
    private String lastName;
    private String city;
    private List<Long> publishedHouses;
    private List<Long> rentedHouses;

    public UserDTO(){

    }
    public UserDTO(String email, String password, String firstName, String lastName, String city){
        this.email = email;
        this.favorites = new ArrayList<>();
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.publishedHouses = new ArrayList<>();
        this.rentedHouses = new ArrayList<>();
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