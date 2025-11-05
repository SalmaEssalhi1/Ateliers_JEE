package ma.fstt.atelier4restapi.repository;

import ma.fstt.atelier4restapi.entity.HistoCarb;
import ma.fstt.atelier4restapi.entity.HistoCarbId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistoCarbRepository extends JpaRepository<HistoCarb, HistoCarbId> {
    List<HistoCarb> findByStation_Id(Long stationId);
    List<HistoCarb> findByStation_IdAndDate(Long stationId, LocalDate date);
}