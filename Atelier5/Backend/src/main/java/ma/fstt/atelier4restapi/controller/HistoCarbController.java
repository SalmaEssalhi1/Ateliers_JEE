package ma.fstt.atelier4restapi.controller;

import lombok.RequiredArgsConstructor;
import ma.fstt.atelier4restapi.entity.HistoCarb;
import ma.fstt.atelier4restapi.service.HistoCarbService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/prix")
@RequiredArgsConstructor
public class HistoCarbController {
    private final HistoCarbService service;

    // GET all - Récupérer tous les prix
    @GetMapping
    public List<HistoCarb> getAll() {
        return service.findAll();
    }

    // GET by ID (clé composite: date, stationId, carburantId)
    @GetMapping("/{date}/{stationId}/{carburantId}")
    public HistoCarb getById(
            @PathVariable LocalDate date,
            @PathVariable Long stationId,
            @PathVariable Long carburantId) {
        return service.findById(date, stationId, carburantId);
    }

    // GET by Station (avec date optionnelle)
    @GetMapping("/station/{stationId}")
    public List<HistoCarb> getByStation(
            @PathVariable Long stationId,
            @RequestParam(required = false) LocalDate date) {
        return service.findPrixJournaliers(stationId, date);
    }

    // POST - Créer un nouveau prix
    @PostMapping
    public HistoCarb create(@RequestBody HistoCarb h) {
        return service.save(h);
    }

    // PUT - Modifier un prix existant
    @PutMapping("/{date}/{stationId}/{carburantId}")
    public HistoCarb update(
            @PathVariable LocalDate date,
            @PathVariable Long stationId,
            @PathVariable Long carburantId,
            @RequestBody HistoCarb h) {
        return service.update(date, stationId, carburantId, h);
    }

    // DELETE - Supprimer un prix
    @DeleteMapping("/{date}/{stationId}/{carburantId}")
    public void delete(
            @PathVariable LocalDate date,
            @PathVariable Long stationId,
            @PathVariable Long carburantId) {
        service.delete(date, stationId, carburantId);
    }
}