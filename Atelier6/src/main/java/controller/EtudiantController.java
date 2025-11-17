package controller;

import entities.Etudiant;
import service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/etudiants")
public class EtudiantController {


    @Autowired
    private EtudiantService etudiantService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("etudiants", etudiantService.findAll());
        return "etudiants/list";
    }


    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("etudiant", new Etudiant());
        return "etudiants/form";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute Etudiant etudiant) {
        etudiantService.save(etudiant);
        return "redirect:/etudiants";
    }


    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Etudiant etudiant = etudiantService.findById(id);
        if (etudiant == null) {
            return "redirect:/etudiants";
        }
        model.addAttribute("etudiant", etudiant);
        return "etudiants/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        etudiantService.deleteById(id);
        return "redirect:/etudiants";
    }
}
