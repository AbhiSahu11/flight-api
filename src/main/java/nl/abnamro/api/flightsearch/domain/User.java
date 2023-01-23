package nl.abnamro.api.flightsearch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    private UUID id=UUID.randomUUID();
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
