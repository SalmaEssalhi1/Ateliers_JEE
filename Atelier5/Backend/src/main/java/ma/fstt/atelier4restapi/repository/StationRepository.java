package ma.fstt.atelier4restapi.repository;

import ma.fstt.atelier4restapi.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {}