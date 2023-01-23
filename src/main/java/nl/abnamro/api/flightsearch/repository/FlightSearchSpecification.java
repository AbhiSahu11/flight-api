package nl.abnamro.api.flightsearch.repository;

import lombok.AllArgsConstructor;
import nl.abnamro.api.flightsearch.domain.Flight;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

import static nl.abnamro.api.flightsearch.util.Utlity.isValidInput;

@Component
@AllArgsConstructor
public class FlightSearchSpecification  {

    public Specification<Flight> getFlightList(String origin, String destination,String price,String duration){
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (isValidInput(origin)) {
                predicates.add(criteriaBuilder.equal(root.get("origin"), origin));
            }
            if (isValidInput(destination)) {
                predicates.add(criteriaBuilder.equal(root.get("destination"), destination));
            }
            if (isValidInput(price)) {
                predicates.add(criteriaBuilder.equal(root.get("price"), price));
            }
            if (isValidInput(duration)) {
                predicates.add(criteriaBuilder.equal(root.get("duration"), duration));
              //  predicates.add(criteriaBuilder.notEqual(root.get("duration"), duration));
              //  predicates.add(criteriaBuilder.like(root.get("duration"),"%"+duration+"%"));
            }
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
