package ma.fstt.atelier4restapi.service;

import lombok.RequiredArgsConstructor;
import ma.fstt.atelier4restapi.entity.Carburant;
import ma.fstt.atelier4restapi.repository.CarburantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarburantService {
    private final CarburantRepository repo;

    public List<Carburant> findAll() { return repo.findAll(); }
    public Carburant findById(Long id) { return repo.findById(id).orElse(null); }
    public Carburant save(Carburant c) { return repo.save(c); }
    public void delete(Long id) { repo.deleteById(id); }
}