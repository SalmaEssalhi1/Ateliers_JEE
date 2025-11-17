package repository;

import entities.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByEtudiantId(Long etudiantId);
    
    @Query("SELECT a FROM Absence a LEFT JOIN FETCH a.etudiant WHERE a.id = :id")
    Optional<Absence> findByIdWithEtudiant(Long id);
}
