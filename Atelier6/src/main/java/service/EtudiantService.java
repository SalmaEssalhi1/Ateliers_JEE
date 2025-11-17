package service;

import entities.Etudiant;
import java.util.List;

public interface EtudiantService {
    List<Etudiant> findAll();
    Etudiant findById(Long id);
    Etudiant save(Etudiant etudiant);
    void deleteById(Long id);
}