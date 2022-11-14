package nl.abnamro.api.flightsearch.runner;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nl.abnamro.api.flightsearch.domain.Flight;
import nl.abnamro.api.flightsearch.properties.AppDataProperties;
import nl.abnamro.api.flightsearch.repository.FlightSearchRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;


@Slf4j
@Profile("!test")
@RequiredArgsConstructor
@Component
public class AppCreationRunner implements ApplicationRunner {

    private final FlightSearchRepository flightSearchRepository;

    private final AppDataProperties appDataProperties;

    @SneakyThrows
    @Override
    public void run(ApplicationArguments args) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
            dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
            for (AppDataProperties.Flight  newFlight : appDataProperties.getFlights()) {
                Flight  flight= Flight.builder()
                    .flightNumber(newFlight.getFlightNumber())
                    .origin(newFlight.getOrigin())
                    .destination(newFlight.getDestination())
                    .departureTime(dateFormat.parse(newFlight.getDepartureTime()))
                    .arrivalTime(dateFormat.parse(newFlight.getArrivalTime()))
                    .price(newFlight.getPrice())
                    .duration(dateFormat.format(dateFormat.parse(newFlight.getArrivalTime()).getTime() - dateFormat.parse(newFlight.getDepartureTime()).getTime()))
                    .build();
                if (! flightSearchRepository.existsByFlightNumber(flight.getFlightNumber())) {
                    flight = flightSearchRepository.save(flight);
                    log.info("Flight {} created for {}...", flight.getId() , flight.getFlightNumber());
                }

            }
        } catch (Exception ex) {
            log.error("Error running system init", ex);
            throw ex;
        }
    }

}
