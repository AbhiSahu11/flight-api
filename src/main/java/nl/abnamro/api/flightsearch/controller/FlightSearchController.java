package nl.abnamro.api.flightsearch.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import nl.abnamro.api.flightsearch.domain.Flight;
import nl.abnamro.api.flightsearch.service.FlightSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Flight Search API", description = "Flight Search API based on arrival and destination & other search parameters")
@RequestMapping(value="/api/flight-search")
@RestController
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    public FlightSearchController(FlightSearchService flightSearchService) {
        this.flightSearchService = flightSearchService;
    }

    @Operation(summary = "Get Flight List based on search parameter(s)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK - Flight Details is retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - The request is not valid"),
    })
    @GetMapping
    public List<Flight> findFlightDetails( @RequestParam  (name = "origin",required = false) String origin,
                                                                   @RequestParam (name="destination",required = false ) String destination,
                                                                   @RequestParam (name="price",required = false) String price,
                                                                   @RequestParam (name="duration",required = false) String duration) {

        return flightSearchService.findAllByParameters(origin, destination, price, duration);
    }

}
