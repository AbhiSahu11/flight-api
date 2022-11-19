package nl.abnamro.api.flightsearch.repository;

import nl.abnamro.api.flightsearch.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FlightSearchRepository extends JpaRepository<Flight,Integer> , QuerydslPredicateExecutor<Flight> {
    boolean existsByFlightNumber(String flightNumber);
    Flight findFlightByPrice(double price);
}
