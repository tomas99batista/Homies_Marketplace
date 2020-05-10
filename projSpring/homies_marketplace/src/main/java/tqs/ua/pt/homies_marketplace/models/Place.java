package tqs.ua.pt.homies_marketplace.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="place")
@Table(name="place")
public class Place {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false, unique=false)
    private String title;

    @Column(nullable=false, unique=false)
    private Double price;

    @Column(nullable=false, unique=false)
    private Double rating;

    @ElementCollection
    private List<String> features;

    @Column(nullable=false, unique=false)
    private Double number_bathrooms;

    @Column(nullable=false, unique=false)
    private Double number_bedrooms;

    @Column(nullable=false, unique=false)
    private String type;

    //@Column(nullable=false, unique=false)
    //private String photos;
}
