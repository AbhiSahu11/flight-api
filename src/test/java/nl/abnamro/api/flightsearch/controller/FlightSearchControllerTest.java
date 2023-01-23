package nl.abnamro.api.flightsearch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.abnamro.api.flightsearch.configuration.security.jwt.JwtUtils;
import nl.abnamro.api.flightsearch.domain.Flight;
import nl.abnamro.api.flightsearch.service.FlightSearchService;
import nl.abnamro.api.flightsearch.error.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
class FlightSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FlightSearchService flightSearchService;

    @MockBean
    private JwtUtils jwtUtil;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private WebApplicationContext context;

    private Flight flight;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        flight= Flight.builder()
                .flightNumber("A101")
                .origin("AMS")
                .destination("DEL")
                .departureTime(LocalTime.now())
                .arrivalTime(LocalTime.now())
                .price(800)
                .build();
    }

    @Test
    @WithMockUser(username = "mockuser")
    public void givenListOfFlight_whenGetAllFlights_thenReturnFlightList() throws Exception{
        // given

        List<Flight> listOfFlights = new ArrayList<>();
        listOfFlights.add(flight);
        listOfFlights.add(Flight.builder().flightNumber("A102").origin("AMS").destination("DEL").departureTime(LocalTime.now()).arrivalTime(LocalTime.now()).price(800).build());

        given(flightSearchService.findAllByParameters(any(),any(),any(),any())).willReturn(listOfFlights);

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("origin", "AMS");
        requestParams.add("destination", "DEL");

        // when
        ResultActions action = mockMvc.perform(get("/api/flight-search").queryParams(requestParams) );
        // then

        action.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()",is(listOfFlights.size())))
                .andExpect(jsonPath("$.[0].flightNumber").value(flight.getFlightNumber()))
                .andExpect(jsonPath("$.[0].origin").value(flight.getOrigin()))
                .andExpect(jsonPath("$.[0].destination").value(flight.getDestination()))
                .andExpect(jsonPath("$.[0].price").value(flight.getPrice()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "mockuser")
    public void shouldGiveBadRequest_whenGetAllFlights_ForEmptyOrigin() throws Exception{
        // given

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("origin", "");
        requestParams.add("destination", "DEL");
        // when
        given(flightSearchService.findAllByParameters(any(),any(),any(),any())).willThrow(new InvalidInputException("origin not valid"));

        ResultActions action = mockMvc.perform(get("/api/flight-search").queryParams(requestParams) );
        // then
        action
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "mockuser")
    public void shouldGiveBadRequest_whenGetAllFlights_ForEmptyDestination() throws Exception{
        // given
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("origin", "AMS");
        requestParams.add("destination", "");
        // when
        given(flightSearchService.findAllByParameters(any(),any(),any(),any())).willThrow(new InvalidInputException("destination not valid"));

        ResultActions action = mockMvc.perform(get("/api/flight-search").queryParams(requestParams) );
        // then
        action
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andDo(print());


    }


}