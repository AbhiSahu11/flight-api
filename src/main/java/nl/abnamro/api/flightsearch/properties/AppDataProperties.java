package nl.abnamro.api.flightsearch.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "app.data")
@Getter
@Setter
public class AppDataProperties {

    private List<Flight> flights = new ArrayList<>();

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Flight {

        private String flightNumber;
        private String origin;
        private String destination;
        private String departureTime;
        private String arrivalTime;
        private double price;

    }

}
