package nl.abnamro.api.flightsearch.service;

import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import nl.abnamro.api.flightsearch.domain.Flight;
import nl.abnamro.api.flightsearch.domain.QFlight;
import nl.abnamro.api.flightsearch.repository.FlightSearchRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class FlightSearchService {

    private final FlightSearchRepository flightSearchRepository;

    public FlightSearchService(FlightSearchRepository flightSearchRepository) {
        this.flightSearchRepository = flightSearchRepository;
    }

    public List<Flight> findAllByParameters(String origin, String destination, String price, String duration) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(origin.isEmpty() || origin.isBlank()) {
            throw new InvalidInputException("origin can not be blank");
        }
        if(destination.isEmpty() || destination.isBlank()) {
            throw new InvalidInputException("destination can not be blank");
        }
        booleanBuilder.and(QFlight.flight.origin.eq(origin));
        booleanBuilder.and(QFlight.flight.destination.eq(destination));

        if(price!=null){
            booleanBuilder.and(QFlight.flight.price.eq(Double.valueOf(price)));
        }
        if(duration!=null){
            booleanBuilder.and(QFlight.flight.duration.eq(duration));
        }

        return booleanBuilder.getValue() == null ?
                flightSearchRepository.findAll() :
                StreamSupport.stream(flightSearchRepository.findAll(booleanBuilder.getValue()).spliterator(), false)
                        .collect(Collectors.toList());
    }
}
