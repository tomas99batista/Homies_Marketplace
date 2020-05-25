package tqs.ua.pt.homies_marketplace.models;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PlaceSpecification implements Specification<Place> {

    private Place filter;
    private double minPrice;
    private double maxPrice;
    public PlaceSpecification(Place filter) {
        super();
        this.filter = filter;
        minPrice=-1;
        maxPrice=-1;
    }

    public PlaceSpecification(Place filter, double minPrice, double maxPrice){
        super();
        this.filter=filter;
        this.minPrice=minPrice;
        this.maxPrice=maxPrice;
    }

    @Override
    public Predicate toPredicate(Root<Place> root, CriteriaQuery<?> cQ, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if(filter.getCity() != null) {
            predicates.add(cb.like(cb.lower(root.get("city")), "%"+filter.getCity().toLowerCase()+"%"));
        }
        if(filter.getPrice() != -1) {
            predicates.add(cb.equal(root.get("price"), filter.getPrice()));
        }
        if(filter.getNumberBedrooms() != -1) {
            predicates.add(cb.equal(root.get("numberBedrooms"), filter.getNumberBedrooms()));
        }
        if (filter.getRating() !=-1){
            predicates.add(cb.equal(root.get("rating"), filter.getRating()));
        }

        if (filter.getNumberBathrooms() != -1){
            predicates.add(cb.equal(root.get("numberBathrooms"), filter.getNumberBathrooms()));
        }

        if (filter.getType() !=null ){
            predicates.add(cb.equal(root.get("type"), filter.getType()));
        }

        if (minPrice!=-1) {
            if (maxPrice!=-1){
                //range query
                predicates.add(cb.between(root.get("price"), minPrice, maxPrice));
            }
            else{
                //query to see if price is bigger
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
        }
        else if (maxPrice!=-1){
            //query to see if price is lower
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        return cb.and(predicates.toArray(new Predicate[0]));

    }
}
