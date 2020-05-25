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

    public PlaceSpecification(Place filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Place> root, CriteriaQuery<?> cQ, CriteriaBuilder cb) {
        Predicate p = cb.conjunction();

        List<Predicate> predicates = new ArrayList<>();

        if(filter.getCity() != null) {
            predicates.add(cb.equal(root.get("city"), filter.getCity()));
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

        return cb.and(predicates.toArray(new Predicate[0]));

    }
}
