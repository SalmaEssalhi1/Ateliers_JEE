package repository;

import entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    @Query("SELECT DISTINCT e FROM Etudiant e LEFT JOIN FETCH e.absences")
    List<Etudiant> findAllWithAbsences();
}