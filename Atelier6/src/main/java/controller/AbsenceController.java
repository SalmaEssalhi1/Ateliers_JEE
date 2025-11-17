package controller;

import entities.Absence;
import entities.Etudiant;
import repository.AbsenceRepository;
import repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;


@Controller
@RequestMapping("/absences")
public class AbsenceController {


    @Autowired
    private AbsenceRepository absenceRepository;


    @Autowired
    private EtudiantRepository etudiantRepository;


    @GetMapping
    public String list(Model model) {
        model.addAttribute("absences", absenceRepository.findAll());
        return "absences/list";
    }


    @GetMapping("/add")
    public String addForm(Model model, @RequestParam(required = false) Long etudiantId) {
        Absence absence = new Absence();
        if (etudiantId != null) {
            Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
            if (etudiant != null) {
                absence.setEtudiant(etudiant);
            }
        }
        model.addAttribute("absence", absence);
        model.addAttribute("etudiants", etudiantRepository.findAll());
        return "absences/form";
    }

    @GetMapping("/etudiant/{etudiantId}")
    public String listByEtudiant(@PathVariable Long etudiantId, Model model) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
        if (etudiant == null) {
            return "redirect:/etudiants";
        }
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("absences", absenceRepository.findByEtudiantId(etudiantId));
        return "absences/list-etudiant";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Absence absence, @RequestParam Long etudiantId) {
        Etudiant e = etudiantRepository.findById(etudiantId).orElse(null);
        if (e != null) {
            absence.setEtudiant(e);
            if (absence.getDateAbs() == null) absence.setDateAbs(LocalDate.now());
            absenceRepository.save(absence);
        }
        Long etudiantIdRedirect = absence.getEtudiant() != null ? absence.getEtudiant().getId() : null;
        if (etudiantIdRedirect != null) {
            return "redirect:/absences/etudiant/" + etudiantIdRedirect;
        }
        return "redirect:/absences";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Absence absence = absenceRepository.findByIdWithEtudiant(id).orElse(null);
        if (absence == null) {
            return "redirect:/absences";
        }
        model.addAttribute("absence", absence);
        model.addAttribute("etudiants", etudiantRepository.findAll());
        return "absences/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Absence absence = absenceRepository.findById(id).orElse(null);
        Long etudiantId = null;
        if (absence != null && absence.getEtudiant() != null) {
            etudiantId = absence.getEtudiant().getId();
        }
        absenceRepository.deleteById(id);
        if (etudiantId != null) {
            return "redirect:/absences/etudiant/" + etudiantId;
        }
        return "redirect:/absences";
    }
}