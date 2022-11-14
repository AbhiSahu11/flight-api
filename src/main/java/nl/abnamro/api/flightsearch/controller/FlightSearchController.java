package nl.abnamro.api.flightsearch.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import nl.abnamro.api.flightsearch.domain.Flight;
import nl.abnamro.api.flightsearch.payload.request.FlightRequest;
import nl.abnamro.api.flightsearch.payload.response.FlightResponse;
import nl.abnamro.api.flightsearch.service.FlightSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<FlightResponse>> findFlightDetails( @RequestParam  (value = "origin") String origin,
                                                                   @RequestParam ("destination" ) String destination,
                                                                   @RequestParam (name="price",required = false) String price,
                                                                   @RequestParam (name="duration",required = false) String duration) {

        List<Flight> allFlightsList = flightSearchService.findAllByParameters(origin, destination, price, duration);
        return ResponseEntity.ok().body(allFlightsList.stream()
                .map(this::flightResponse)
                .collect(Collectors.toList()));
    }


    @PostMapping
    public ResponseEntity<String> createFlightRoute(@RequestBody FlightRequest flightRequest){
        // TODO: Not implemented because it is not required as per use case.
        return  ResponseEntity.ok(HttpStatus.METHOD_NOT_ALLOWED.toString());
    }


    @PutMapping
    public ResponseEntity<String> updateFlightRoute(@RequestBody FlightRequest flightRequest){
        // TODO: Not implemented because it is not required as per use case.
        return  ResponseEntity.ok(HttpStatus.METHOD_NOT_ALLOWED.toString());
    }


    @DeleteMapping
    public ResponseEntity<String> deleteFlightRoute(@RequestBody FlightRequest flightRequest){
        // TODO: Not implemented because it is not required as per use case.
        return  ResponseEntity.ok(HttpStatus.METHOD_NOT_ALLOWED.toString());
    }



    private FlightResponse flightResponse(Flight flight) {
        return FlightResponse.map(flight);
    }

}
