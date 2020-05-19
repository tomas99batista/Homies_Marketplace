package tqs.ua.pt.homies_marketplace.models;

import javax.persistence.*;

@Entity(name="reviews")
@Table(name="reviews")
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="email")
    public String email;

    @Column(name="rating")
    public double rating;

    @Column(name="comment")
    public String comment;

    public Review(){

    }

    public Review(String email, double rating, String comment){
        this.email=email;
        this.rating=rating;
        this.comment=comment;
    }

    public Review(Long id, String email, double rating, String comment) {
        this.id = id;
        this.email = email;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getId(){
        return id;
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
