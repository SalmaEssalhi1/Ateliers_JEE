package ma.fstt.atelier4restapi.service;

import lombok.RequiredArgsConstructor;
import ma.fstt.atelier4restapi.entity.Station;
import ma.fstt.atelier4restapi.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService {
    private final StationRepository repo;

    public List<Station> findAll() { return repo.findAll(); }
    public Station findById(Long id) { return repo.findById(id).orElse(null); }
    public Station save(Station s) { return repo.save(s); }
    public void delete(Long id) { repo.deleteById(id); }
}