package nl.abnamro.api.flightsearch.repository.querydsl;


import com.querydsl.jpa.impl.JPAQuery;
import nl.abnamro.api.flightsearch.domain.Flight;
import nl.abnamro.api.flightsearch.domain.QFlight;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FlightRepositoryImpl implements FlightRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Flight> findFlightByPriceQueryDSL(String price) {
        final JPAQuery<Flight> query = new JPAQuery<>(entityManager);
        final QFlight flight = QFlight.flight;

        return query.from(flight).
                where(flight.price.eq(Double.valueOf(price))).
                fetch();
    }

    @Override
    public List<Flight> findFlightByOriginAndDestinationQueryDSL(String origin, String destination) {
        final JPAQuery<Flight> query = new JPAQuery<>(entityManager);
        final QFlight flight = QFlight.flight;

        return query.from(flight).
                where(flight.origin.eq(origin)
                        .and(flight.destination.eq(destination))).
                fetch();
    }
}
