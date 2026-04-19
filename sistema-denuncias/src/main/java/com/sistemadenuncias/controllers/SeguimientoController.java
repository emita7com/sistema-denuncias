package com.sistemadenuncias.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sistemadenuncias.models.entity.*;
import com.sistemadenuncias.models.repository.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/seguimiento")
public class SeguimientoController {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private DenunciaRepository denunciaRepository;

    // Mostrar historial de seguimiento
    @GetMapping("/{idDenuncia}")
    public String verSeguimiento(@PathVariable Long idDenuncia, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/auth/login";

        Denuncia denuncia = denunciaRepository.findById(idDenuncia).orElse(null);
        if (denuncia == null) {
            model.addAttribute("error", "No se encontró la denuncia.");
            return "redirect:/";
        }

        List<Seguimiento> seguimientos = seguimientoRepository.findByDenunciaIdDenuncia(idDenuncia);
        model.addAttribute("denuncia", denuncia);
        model.addAttribute("seguimientos", seguimientos);
        model.addAttribute("nuevoSeguimiento", new Seguimiento());

        return "seguimiento/seguimiento"; // Vista en templates/seguimiento/seguimiento.html
    }

    // Guardar nuevo seguimiento
    @PostMapping("/guardar/{idDenuncia}")
    public String guardarSeguimiento(@PathVariable Long idDenuncia,
                                     @ModelAttribute Seguimiento nuevoSeguimiento,
                                     HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/auth/login";

        Denuncia denuncia = denunciaRepository.findById(idDenuncia).orElse(null);
        if (denuncia == null) return "redirect:/";

        nuevoSeguimiento.setDenuncia(denuncia);
        nuevoSeguimiento.setAutorRol(usuario.getRol().toUpperCase());
        seguimientoRepository.save(nuevoSeguimiento);

        return "redirect:/seguimiento/" + idDenuncia;
    }
}
