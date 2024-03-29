package nl.abnamro.api.flightsearch.repository;

import nl.abnamro.api.flightsearch.domain.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class FlightSearchRepositoryTest {

    @Autowired
    private FlightSearchRepository flightSearchRepository;

    private Flight flight;

    @BeforeEach
    void setUp() {
        flight= Flight.builder()
                .id(UUID.randomUUID())
                .flightNumber("A101")
                .origin("AMS")
                .destination("DEL")
                .departureTime(LocalTime.now())
                .arrivalTime(LocalTime.now())
                .duration(8)
                .price(800)
                .build();
    }

    @Test
    public void givenFlightObject_whenSave_thenReturnSavedFlight(){

        // when
        Flight savedFlight = flightSearchRepository.save(flight);
        // then
        assertThat(savedFlight).isNotNull();
        assertThat(savedFlight.getId()).isNotNull();
    }

    @Test
    public void givenFlightList_whenFindAll_thenFlightList(){
        //given
        Flight newFlight= Flight.builder()
                .flightNumber("A101")
                .id(UUID.randomUUID())
                .origin("AMS")
                .destination("DEL")
                .departureTime(LocalTime.now())
                .arrivalTime(LocalTime.now())
                .duration(9)
                .price(800)
                .build();

        flightSearchRepository.save(flight);
        flightSearchRepository.save(newFlight);

        // when
        List<Flight> flightList = flightSearchRepository.findAll();
        // then
        assertThat(flightList).isNotNull();
        assertThat(flightList.size()).isEqualTo(2);

    }

    @Test
    public void givenFlightPrice_whenFindByPrice_thenReturnFlightObject(){
        //given
        flightSearchRepository.save(flight);

        // when
        Flight flightFromDataBase =flightSearchRepository.findFlightByPrice(flight.getPrice());
        // then
        assertThat(flightFromDataBase).isNotNull();
        assertThat(flightFromDataBase.getId()).isEqualTo(flight.getId());
    }




}