package ma.fstt.atelier4restapi.entity;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@IdClass(HistoCarbId.class)
public class HistoCarb {

    @Id
    private LocalDate date;

    @Id
    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @Id
    @ManyToOne
    @JoinColumn(name = "carburant_id")
    private Carburant carburant;

    private Double prix;
}