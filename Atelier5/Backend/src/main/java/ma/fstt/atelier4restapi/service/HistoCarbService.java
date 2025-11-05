package ma.fstt.atelier4restapi.service;

import lombok.RequiredArgsConstructor;
import ma.fstt.atelier4restapi.entity.HistoCarb;
import ma.fstt.atelier4restapi.entity.HistoCarbId;
import ma.fstt.atelier4restapi.repository.HistoCarbRepository;
import ma.fstt.atelier4restapi.repository.StationRepository;
import ma.fstt.atelier4restapi.repository.CarburantRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoCarbService {
    private final HistoCarbRepository repo;
    private final StationRepository stationRepo;
    private final CarburantRepository carbRepo;

    public List<HistoCarb> findAll() {
        return repo.findAll();
    }

    public HistoCarb findById(LocalDate date, Long stationId, Long carburantId) {
        HistoCarbId id = new HistoCarbId(date, stationId, carburantId);
        return repo.findById(id).orElse(null);
    }

    public List<HistoCarb> findByStation(Long stationId) {
        return repo.findByStation_Id(stationId);
    }

    public HistoCarb save(HistoCarb h) {
        return repo.save(h);
    }

    public HistoCarb update(LocalDate date, Long stationId, Long carburantId, HistoCarb updated) {
        HistoCarbId id = new HistoCarbId(date, stationId, carburantId);
        if (repo.existsById(id)) {
            updated.setDate(date);
            updated.setStation(stationRepo.findById(stationId).orElse(null));
            updated.setCarburant(carbRepo.findById(carburantId).orElse(null));
            return repo.save(updated);
        }
        return null;
    }

    public void delete(LocalDate date, Long stationId, Long carburantId) {
        HistoCarbId id = new HistoCarbId(date, stationId, carburantId);
        repo.deleteById(id);
    }

    public List<HistoCarb> findPrixJournaliers(Long stationId, LocalDate date) {
        return date != null ? repo.findByStation_IdAndDate(stationId, date)
                : repo.findByStation_Id(stationId);
    }
}