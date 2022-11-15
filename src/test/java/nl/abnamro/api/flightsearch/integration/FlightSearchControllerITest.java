package nl.abnamro.api.flightsearch.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.abnamro.api.flightsearch.domain.Flight;
import nl.abnamro.api.flightsearch.payload.request.LoginRequest;
import nl.abnamro.api.flightsearch.properties.AppDataProperties;
import nl.abnamro.api.flightsearch.repository.FlightSearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FlightSearchControllerITest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FlightSearchRepository flightSearchRepository;

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
        flightSearchRepository.saveAll(listOfFlights);

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