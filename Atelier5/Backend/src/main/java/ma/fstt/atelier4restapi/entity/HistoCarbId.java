package ma.fstt.atelier4restapi.entity;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Clé composite pour l'entité HistoCarb.
 * Les champs doivent correspondre exactement à ceux annotés @Id dans HistoCarb.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode   // indispensable pour les comparaisons JPA
public class HistoCarbId implements Serializable {

    private LocalDate date;      // même type que @Id dans HistoCarb
    private Long      station;   // même type que Station.id
    private Long      carburant; // même type que Carburant.id
}