package com.sistemadenuncias.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistemadenuncias.models.entity.Estudiante;
import com.sistemadenuncias.models.entity.Usuario;
import com.sistemadenuncias.models.repository.EstudianteRepository;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/estudiantes")
@CrossOrigin(origins = "*")

public class EstudianteController {
    
    @Autowired
    private EstudianteRepository estudianteRepository;

    // ✅ Listar todos los estudiantes
    @GetMapping
    public List<Estudiante> listar() {
        return estudianteRepository.findAll();
    }

    // ✅ Buscar estudiante por ID
    @GetMapping("/{id}")
    public Estudiante obtenerPorId(@PathVariable Long id) {
        return estudianteRepository.findById(id).orElse(null);
    }

    // ✅ Crear nuevo estudiante
    @PostMapping
    public Estudiante guardar(@RequestBody Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    // ✅ Actualizar datos de un estudiante
    @PutMapping("/{id}")
    public Estudiante actualizar(@PathVariable Long id, @RequestBody Estudiante datos) {
        Estudiante estudiante = estudianteRepository.findById(id).orElse(null);
        if (estudiante != null) {
            estudiante.setNombre(datos.getNombre());
            estudiante.setApellido(datos.getApellido());
            //estudiante.setCorreo(datos.getCorreo());
            //estudiante.setContrasena(datos.getContrasena());
            estudiante.setCurso(datos.getCurso());
            estudiante.setColegio(datos.getColegio());
            return estudianteRepository.save(estudiante);
        }
        return null;
    }

    // ✅ Eliminar estudiante
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        estudianteRepository.deleteById(id);
    }

    //SUBIR FOTO
    @PostMapping("/subir-foto")
public String subirFoto(@RequestParam("file") MultipartFile file,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

    try {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión.");
            return "redirect:/auth/login";
        }

        Estudiante estudiante = estudianteRepository.findByUsuario(usuario);

        if (estudiante == null) {
            redirectAttributes.addFlashAttribute("error", "No se encontró el perfil del estudiante.");
            return "redirect:/denuncias/nueva";
        }

        if (!file.isEmpty()) {

            String nombre = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path ruta = Paths.get("uploads/" + nombre);

            Files.copy(file.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);

            estudiante.setFoto(nombre);
            estudianteRepository.save(estudiante);
        }

        redirectAttributes.addFlashAttribute("exito", "Foto actualizada correctamente.");
        return "redirect:/denuncias/nueva";

    } catch (Exception e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("error", "Error al subir la foto.");
        return "redirect:/denuncias/nueva";
    }
}


}
