package ma.fstt.atelier4restapi.controller;

import lombok.RequiredArgsConstructor;
import ma.fstt.atelier4restapi.entity.Carburant;
import ma.fstt.atelier4restapi.service.CarburantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import ma.fstt.atelier4restapi.dto.CarburantDTO;

@RestController
@RequestMapping("/api/carburants")
@RequiredArgsConstructor
public class CarburantController {
    private final CarburantService service;

    @GetMapping
    public List<CarburantDTO> getAll() {
        return service.findAll().stream()
                .map(c -> new CarburantDTO(c.getId(), c.getNom(), c.getDescription()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Carburant get(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public Carburant create(@RequestBody Carburant c) { return service.save(c); }

    @PutMapping("/{id}")
    public Carburant update(@PathVariable Long id, @RequestBody Carburant c) {
        c.setId(id);
        return service.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}