package tqs.ua.pt.homies_marketplace.dtos;

public class ReviewDTO {

    private String email;

    private double rating;

    private String comment;

    public ReviewDTO(){

    }
    public ReviewDTO(String email, double rating, String comment) {
        this.email = email;
        this.rating = rating;
        this.comment = comment;
    }

    public String getEmail() {
        return email;
    }

    public double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
