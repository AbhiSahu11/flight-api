package nl.abnamro.api.flightsearch.service;

import nl.abnamro.api.flightsearch.domain.Flight;
import nl.abnamro.api.flightsearch.repository.FlightSearchRepository;
import nl.abnamro.api.flightsearch.repository.FlightSearchSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FlightSearchServiceTest {

    @Mock
    private FlightSearchRepository flightSearchRepository;

    @Mock
    private FlightSearchSpecification flightSearchSpecification;

    @InjectMocks
    private FlightSearchService flightSearchService;

    private Flight flight;

    @BeforeEach
    void setUp() {
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
    void findAllByParameters() {
        //given
        Specification<Flight> flightSpecification = (root, query, criteriaBuilder)->root.isNotNull();

        List<Flight> listOfFlights = new ArrayList<>();
        listOfFlights.add(flight);
        listOfFlights.add(Flight.builder().flightNumber("A102").origin("AMS").destination("DEL").departureTime(LocalTime.now()).arrivalTime(LocalTime.now()).price(800).build());
        //when

        given(flightSearchSpecification.getFlightList(any(),any(),any(),any())).willReturn(flightSpecification);
        given(flightSearchRepository.findAll(any(Specification.class))).willReturn(listOfFlights);

        //then
        List<Flight> flightList= flightSearchService.findAllByParameters("AMS","DEL","850","6");
        assertThat(flightList).isNotNull();
        assertThat(flightList.size()).isEqualTo(2);
    }
}