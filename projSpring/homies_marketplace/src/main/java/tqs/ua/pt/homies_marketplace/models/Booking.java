package tqs.ua.pt.homies_marketplace.models;

import javax.persistence.*;

@Entity(name="booking")
@Table(name="booking")
public class Booking {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String owner;

    @Column
    private String requester;

    @Column
    private long placeId;

    public Booking(String owner, String requester, long placeId) {
        this.owner = owner;
        this.requester = requester;
        this.placeId = placeId;
    }

    public Long getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getRequester() {
        return requester;
    }

    public long getPlaceId() {
        return placeId;
    }
}
