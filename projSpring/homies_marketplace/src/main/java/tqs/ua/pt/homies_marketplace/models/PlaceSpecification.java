package tqs.ua.pt.homies_marketplace.models;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PlaceSpecification implements Specification<Place> {

    private Place filter;

    public PlaceSpecification(Place filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Place> root, CriteriaQuery<?> cQ, CriteriaBuilder cb) {
        Predicate p = cb.conjunction();

        if (filter.getCity()!=null){
            if (filter.getPrice()!=-1){
                p.getExpressions().add(cb.and(cb.equal(root.get("city"), filter.getCity()), cb.equal(root.get("price"), filter.getPrice())));
            }

            else{
                p.getExpressions().add(cb.equal(root.get("city"), filter.getCity()));
            }

        }

        if (filter.getPrice()!=-1){
            p.getExpressions().add(cb.equal(root.get("price"), filter.getPrice()));
        }

        return p;

    }
}
