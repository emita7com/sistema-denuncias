package com.sistemadenuncias.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistemadenuncias.models.entity.Defensoria;
import com.sistemadenuncias.models.entity.Denuncia;
import com.sistemadenuncias.models.entity.Usuario;
import com.sistemadenuncias.models.repository.DefensoriaRepository;
import com.sistemadenuncias.models.repository.DenunciaRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/defensoria")
public class DefensoriaController {
    
    @Autowired
    private DenunciaRepository denunciaRepository;

    @Autowired
    private DefensoriaRepository defensoriaRepository;

    @GetMapping("/defensoria/denuncias")
    public String verDenunciasDefensoria(Model model) {
        model.addAttribute("denuncias", denunciaRepository.findAll());
        return "defensoria/denuncias";
    }

    // 🔹 Ver denuncias que fueron derivadas a la Defensoría
    @GetMapping("/denuncias")
    public String verDenuncias(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/auth/login";

        Defensoria defensoria = defensoriaRepository.findByUsuario(usuario);
        if (defensoria == null) return "redirect:/auth/login";

        // Mostrar solo denuncias derivadas a la defensoría
        List<Denuncia> denuncias = denunciaRepository.findByDerivadoA("DEFENSORIA");

        model.addAttribute("denuncias", denuncias);
        return "defensoria/denuncias";
    }

    // 🔹 Cambiar estado a "En proceso"
    @PostMapping("/enproceso/{id}")
    public String marcarEnProceso(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Denuncia denuncia = denunciaRepository.findById(id).orElse(null);
        if (denuncia != null) {
            denuncia.setEstado("En proceso");
            denunciaRepository.save(denuncia);
            redirectAttributes.addFlashAttribute("mensaje", "La denuncia fue marcada como 'En proceso'.");
        }
        return "redirect:/defensoria/denuncias";
    }

    // 🔹 Cambiar estado a "Cerrada"
    @PostMapping("/cerrar/{id}")
    public String cerrarDenuncia(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Denuncia denuncia = denunciaRepository.findById(id).orElse(null);
        if (denuncia != null) {
            denuncia.setEstado("Cerrada");
            denunciaRepository.save(denuncia);
            redirectAttributes.addFlashAttribute("mensaje", "La denuncia fue marcada como 'Cerrada'.");
        }
        return "redirect:/defensoria/denuncias";
    }
}
