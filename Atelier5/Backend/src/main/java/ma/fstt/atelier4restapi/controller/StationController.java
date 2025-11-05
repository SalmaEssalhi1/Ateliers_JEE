package ma.fstt.atelier4restapi.controller;

import lombok.RequiredArgsConstructor;
import ma.fstt.atelier4restapi.entity.Station;
import ma.fstt.atelier4restapi.service.StationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import ma.fstt.atelier4restapi.dto.StationDTO;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {
    private final StationService service;

    @GetMapping
    public List<StationDTO> getAll() {
        return service.findAll().stream()
                .map(s -> new StationDTO(s.getId(), s.getNom(), s.getVille(), s.getAdresse()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Station get(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public Station create(@RequestBody Station s) { return service.save(s); }

    @PutMapping("/{id}")
    public Station update(@PathVariable Long id, @RequestBody Station s) {
        s.setId(id);
        return service.save(s);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}