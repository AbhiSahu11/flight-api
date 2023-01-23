package nl.abnamro.api.flightsearch.service;

import lombok.extern.slf4j.Slf4j;
import nl.abnamro.api.flightsearch.domain.Flight;
import nl.abnamro.api.flightsearch.repository.FlightSearchRepository;
import nl.abnamro.api.flightsearch.repository.FlightSearchSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FlightSearchService {

    private final FlightSearchRepository flightSearchRepository;

    private final FlightSearchSpecification flightSearchSpecification;

    public FlightSearchService(FlightSearchRepository flightSearchRepository, FlightSearchSpecification flightSearchSpecification) {
        this.flightSearchRepository = flightSearchRepository;
        this.flightSearchSpecification = flightSearchSpecification;
    }

    public List<Flight> findAllByParameters(String origin, String destination, String price, String duration) {
       return flightSearchRepository.findAll(flightSearchSpecification.getFlightList(origin,destination,price,duration));
    }


}
