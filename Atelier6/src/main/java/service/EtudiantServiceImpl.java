package service;

import entities.Etudiant;
import repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
@Transactional
public class EtudiantServiceImpl implements EtudiantService {


    @Autowired
    private EtudiantRepository etudiantRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Etudiant> findAll() {
        return etudiantRepository.findAllWithAbsences();
    }


    @Override
    public Etudiant findById(Long id) {
        return etudiantRepository.findById(id).orElse(null);
    }


    @Override
    public Etudiant save(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }


    @Override
    public void deleteById(Long id) {
        etudiantRepository.deleteById(id);
    }
}