package ma.fstt.atelier4restapi.repository;

import ma.fstt.atelier4restapi.entity.Carburant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarburantRepository extends JpaRepository<Carburant, Long> {}