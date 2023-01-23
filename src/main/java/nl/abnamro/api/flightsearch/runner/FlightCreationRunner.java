package nl.abnamro.api.flightsearch.runner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.abnamro.api.flightsearch.domain.Flight;
import nl.abnamro.api.flightsearch.repository.FlightSearchRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Profile("!test")
@RequiredArgsConstructor
@Component
public class FlightCreationRunner implements ApplicationRunner {
    private final FlightSearchRepository flightSearchRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        TypeReference<List<Flight>> typeReference = new TypeReference<>() {};

        List<Flight> flights = saveFlightDuration(mapper, typeReference);
        flightSearchRepository.saveAll(flights);
    }

    private List<Flight> saveFlightDuration(ObjectMapper mapper, TypeReference<List<Flight>> typeReference) throws IOException {
        InputStream inputStream = TypeReference.class.getResourceAsStream("/flights.json");
        return mapper.readValue(inputStream, typeReference)
                .stream()
                .map(flight -> {
                    flight.setDuration(Duration.between(flight.getDepartureTime(),
                            flight.getArrivalTime()).toHours());
                    return flight;
                }).collect(Collectors.toList());
    }
}
