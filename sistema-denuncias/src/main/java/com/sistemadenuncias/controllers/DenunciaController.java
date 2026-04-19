package com.sistemadenuncias.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistemadenuncias.models.entity.Denuncia;
import com.sistemadenuncias.models.entity.Estudiante;
import com.sistemadenuncias.models.entity.Usuario;
import com.sistemadenuncias.models.repository.DenunciaRepository;
import com.sistemadenuncias.models.repository.EstudianteRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/denuncias")
@CrossOrigin(origins = "*")
public class DenunciaController {

    @Autowired
    private DenunciaRepository denunciaRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    // 🔹 Listar todas las denuncias (API REST)
    @GetMapping
    @ResponseBody
    public List<Denuncia> listar() {
        return denunciaRepository.findAll();
    }

    // 🔹 Mostrar formulario visual de denuncia (para estudiante)
    @GetMapping("/nueva")
public String mostrarFormularioDenuncia(Model model, HttpSession session) {

    // Obtener usuario logueado
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        return "redirect:/auth/login";
    }

    // Obtener estudiante asociado
    Estudiante estudiante = estudianteRepository.findByUsuario(usuario);

    // Enviar el estudiante al HTML
    model.addAttribute("estudiante", estudiante);

    // Enviar nueva denuncia
    model.addAttribute("denuncia", new Denuncia());

    return "estudiante/denuncia";
}

    // 🔹 Guardar denuncia desde el formulario con archivo
@PostMapping("/guardar")
public String guardarDenuncia(@ModelAttribute Denuncia denuncia,
                              @RequestParam(value = "archivoEvidencia", required = false) MultipartFile archivo,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
    try {
        // 🧩 Recuperar el usuario en sesión
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión antes de registrar una denuncia.");
            return "redirect:/auth/login";
        }

        // 🧩 Buscar el estudiante asociado a este usuario
        Estudiante estudiante = estudianteRepository.findByUsuario(usuario);
        if (estudiante == null) {
            redirectAttributes.addFlashAttribute("error", "No se encontró el perfil del estudiante.");
            return "redirect:/auth/login";
        }

        // 📂 Guardar archivo de evidencia si se subió
        if (archivo != null && !archivo.isEmpty()) {
            String nombreArchivo = archivo.getOriginalFilename();
            Path rutaDestino = Paths.get("uploads/" + nombreArchivo);
            Files.copy(archivo.getInputStream(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);
            denuncia.setEvidencia(nombreArchivo);
        }

        // ⚙️ Asignar datos automáticos
        denuncia.setFecha(LocalDateTime.now());
        denuncia.setEstado("En revisión");
        denuncia.setEstudiante(estudiante);
        denuncia.setColegio(estudiante.getColegio());

        // 💾 Guardar denuncia
        denunciaRepository.save(denuncia);

        redirectAttributes.addFlashAttribute("exito", "Tu denuncia fue registrada correctamente.");
        return "redirect:/denuncias/nueva";
    } catch (Exception e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("error", "Ocurrió un error al guardar la denuncia.");
        return "redirect:/denuncias/nueva";
    }
}


    // 🔹 Buscar denuncia por ID (API REST)
    @GetMapping("/{id}")
    @ResponseBody
    public Denuncia obtenerPorId(@PathVariable Long id) {
        return denunciaRepository.findById(id).orElse(null);
    }

    // 🔹 Eliminar denuncia (API REST)
    @DeleteMapping("/{id}")
    @ResponseBody
    public void eliminar(@PathVariable Long id) {
        denunciaRepository.deleteById(id);
    }
}
