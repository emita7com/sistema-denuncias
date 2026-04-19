package com.sistemadenuncias.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistemadenuncias.models.entity.Denuncia;
import com.sistemadenuncias.models.entity.Psicologo;
import com.sistemadenuncias.models.entity.Usuario;
import com.sistemadenuncias.models.repository.DenunciaRepository;
import com.sistemadenuncias.models.repository.EstudianteRepository;
import com.sistemadenuncias.models.repository.PsicologoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/psicologo")
@CrossOrigin(origins = "*")
public class PsicologoController {

    @Autowired
    private DenunciaRepository denunciaRepository;

    @Autowired
    private PsicologoRepository psicologoRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    // ✔ Mostrar denuncias
    @GetMapping("/denuncias")
    public String verDenuncias(Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/auth/login";

        Psicologo psicologo = psicologoRepository.findByUsuario(usuario);
        if (psicologo == null) return "redirect:/auth/login";

        List<Denuncia> denuncias = denunciaRepository.findByColegio(psicologo.getColegio());
        model.addAttribute("denuncias", denuncias);

        model.addAttribute("psicologo", psicologo);

        return "psicologo/denuncias"; 
    }

    // ✔ Derivar denuncia
@PostMapping("/derivar/{id}")
public String derivarADirector(
        @PathVariable Long id,
        @RequestParam("mensaje") String mensaje,
        RedirectAttributes redirectAttributes) {

    Denuncia denuncia = denunciaRepository.findById(id).orElse(null);

    if (denuncia != null) {
        denuncia.setEstado("Derivada");
        denuncia.setDerivadoA("DIRECTOR");
        denuncia.setMensajeDerivacion(mensaje);
        denunciaRepository.save(denuncia);

        redirectAttributes.addFlashAttribute("mensaje", "Caso derivado con mensaje.");
    }

    return "redirect:/psicologo/denuncias";
}

    // ✔ Cerrar caso
    @PostMapping("/cerrar/{id}")
    public String cerrarCaso(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Denuncia denuncia = denunciaRepository.findById(id).orElse(null);
        if (denuncia != null) {
            denuncia.setEstado("Atendida");
            denuncia.setDerivadoA("PSICOLOGO");
            denunciaRepository.save(denuncia);
            redirectAttributes.addFlashAttribute("mensaje", "Denuncia marcada como atendida.");
        }
        return "redirect:/psicologo/denuncias";
    }

    @PostMapping("/subirFoto")
public ResponseEntity<String> subirFoto(
        @RequestParam("foto") MultipartFile foto,
        HttpSession session) {

    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    Psicologo psicologo = psicologoRepository.findByUsuario(usuario);

    try {
        String nombre = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
        Path ruta = Paths.get("uploads").resolve(nombre);
        Files.copy(foto.getInputStream(), ruta);

        psicologo.setFoto_perfil(nombre);
        psicologoRepository.save(psicologo);

        return ResponseEntity.ok("OK");
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error");
    }
}

}
