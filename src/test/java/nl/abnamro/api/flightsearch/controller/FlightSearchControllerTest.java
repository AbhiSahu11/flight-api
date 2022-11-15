package nl.abnamro.api.flightsearch.controller;

import nl.abnamro.api.flightsearch.configuration.security.jwt.JwtUtils;
import nl.abnamro.api.flightsearch.domain.Flight;
import nl.abnamro.api.flightsearch.service.FlightSearchService;
import nl.abnamro.api.flightsearch.service.InvalidInputException;
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
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
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

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "mockuser")
    public void givenListOfFlight_whenGetAllFlights_thenReturnFlightList() throws Exception{
        // given
        Flight flight= Flight.builder()
                .flightNumber("A101")
                .origin("AMS")
                .destination("DEL")
                .departureTime(new Date())
                .arrivalTime(new Date())
                .price(800)
                .build();

        List<Flight> listOfFlights = new ArrayList<>();
        listOfFlights.add(flight);
        listOfFlights.add(Flight.builder().flightNumber("A102").origin("AMS").destination("DEL").departureTime(new Date()).arrivalTime(new Date()).price(800).build());

        given(flightSearchService.findAllByParameters(anyString(),anyString(),anyString(),anyString())).willReturn(listOfFlights);

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("origin", "AMS");
        requestParams.add("destination", "DEL");
        requestParams.add("price", "700");
        requestParams.add("duration", "06:00");
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
        Flight flight= Flight.builder()
                .flightNumber("A101")
                .origin("AMS")
                .destination("DEL")
                .departureTime(new Date())
                .arrivalTime(new Date())
                .price(800)
                .build();

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("origin", "");
        requestParams.add("destination", "DEL");
        requestParams.add("price", "700");
        requestParams.add("duration", "06:00");
        // when
        given(flightSearchService.findAllByParameters(anyString(),anyString(),anyString(),anyString())).willThrow(new InvalidInputException("origin not valid"));

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
        Flight flight= Flight.builder()
                .flightNumber("A101")
                .origin("AMS")
                .destination("DEL")
                .departureTime(new Date())
                .arrivalTime(new Date())
                .price(800)
                .build();

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("origin", "AMS");
        requestParams.add("destination", "");
        requestParams.add("price", "700");
        requestParams.add("duration", "06:00");
        // when
        given(flightSearchService.findAllByParameters(anyString(),anyString(),anyString(),anyString())).willThrow(new InvalidInputException("destination not valid"));

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