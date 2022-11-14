package nl.abnamro.api.flightsearch.payload.response;

import lombok.*;
import nl.abnamro.api.flightsearch.domain.Flight;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Getter
@Builder
public class FlightResponse {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final String departureTime;
    private final String arrivalTime;
    private final double price;
    private final String duration;

    public static String getArrivalTime(Flight flight) throws ParseException {
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(flight.getArrivalTime()) ;
    }

    public static String getDepartureTime(Flight flight) throws ParseException {
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(flight.getDepartureTime());
    }

    @SneakyThrows
    public static FlightResponse map(Flight flight){
        return FlightResponse.builder()
                .flightNumber(flight.getFlightNumber())
                .origin(flight.getOrigin())
                .destination(flight.getDestination())
                .arrivalTime(getArrivalTime(flight))
                .departureTime(getDepartureTime(flight))
                .duration(flight.getDuration())
                .price(flight.getPrice())
                .build();
    }




}
