package nl.abnamro.api.flightsearch.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI FlightSearchAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Flight Search API")
                        .description("This is a API Implementation for the demonstration of Flight Search Using Spring Boot 2 .")
                        .version("v1.0"));

    }

}
