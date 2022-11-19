package nl.abnamro.api.flightsearch.repository.querydsl;

import nl.abnamro.api.flightsearch.domain.Flight;
import java.util.List;

public interface FlightRepository {

    public List<Flight> findFlightByPriceQueryDSL(String firstname);
    public List<Flight> findFlightByOriginAndDestinationQueryDSL(String firstname, String surname);

}
