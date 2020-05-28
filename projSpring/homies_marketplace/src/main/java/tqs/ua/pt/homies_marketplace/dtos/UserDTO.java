package tqs.ua.pt.homies_marketplace.dtos;

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
    public UserDTO(String email, List<Long> favorites, String password, String firstName, String lastName, String city,List<Long> publishedHouses, List<Long> rentedHouses){
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
