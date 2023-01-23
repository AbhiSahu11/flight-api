package nl.abnamro.api.flightsearch.repository;

import nl.abnamro.api.flightsearch.domain.Flight;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FlightSearchRepository extends JpaRepository<Flight,Integer>, JpaSpecificationExecutor<Flight> {

    Flight findFlightByPrice(double price);
    List<Flight> findAll(Specification<Flight> specification);
}
