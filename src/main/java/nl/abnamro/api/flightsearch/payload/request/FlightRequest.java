package nl.abnamro.api.flightsearch.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FlightRequest {

    private String originAirport;
    private String destinationAirport;
    private double price;
    private int duration;

}
