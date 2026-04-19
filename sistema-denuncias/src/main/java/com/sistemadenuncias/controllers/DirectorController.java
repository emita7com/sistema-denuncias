package com.sistemadenuncias.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.sistemadenuncias.models.entity.Colegio;
import com.sistemadenuncias.models.entity.Denuncia;
import com.sistemadenuncias.models.entity.Director;
import com.sistemadenuncias.models.entity.Usuario;
import com.sistemadenuncias.models.repository.ColegioRepository;
import com.sistemadenuncias.models.repository.DenunciaRepository;
import com.sistemadenuncias.models.repository.DirectorRepository;
import com.sistemadenuncias.models.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/director")
@CrossOrigin(origins = "*")
public class DirectorController {
    
    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private DenunciaRepository denunciaRepository;

    // 🔹 Agregado para registrar DIRECTOR
    @Autowired
    private ColegioRepository colegioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 🔹 Mostrar denuncias derivadas a la director
    @GetMapping("/denuncias")
    public String verDenuncias(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/auth/login";

        Director director = directorRepository.findByUsuario(usuario);
        if (director == null) return "redirect:/auth/login";

        List<Denuncia> denuncias = denunciaRepository.findByColegio(director.getColegio());


        model.addAttribute("director", director);
        model.addAttribute("denuncias", denuncias);

        return "director/denuncias";
    }

    // 🔹 Derivar una denuncia a la Defensoría
    @PostMapping("/derivar/{id}")
    public String derivarADefensoria(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Denuncia denuncia = denunciaRepository.findById(id).orElse(null);
        if (denuncia != null) {
            denuncia.setEstado("Enviado a Defensoría");
            denuncia.setDerivadoA("DEFENSORIA");
            denunciaRepository.save(denuncia);
            redirectAttributes.addFlashAttribute("mensaje", "Denuncia derivada a la Defensoría.");
        }
        return "redirect:/director/denuncias";
    }

    // 🔹 REGISTRAR DIRECTOR (nuevo método)
    @PostMapping("/registrar")
    public String registrarDirector(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String correo,
            @RequestParam String contrasena,
            @RequestParam Long colegioId,
            RedirectAttributes redirectAttributes) {

        Colegio colegio = colegioRepository.findById(colegioId)
                .orElseThrow(() -> new RuntimeException("Colegio no encontrado"));

        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setContrasena(contrasena);
        usuario.setRol("DIRECTOR");
        usuario.setEstado("Activo");
        usuarioRepository.save(usuario);

        // Guardar director
        Director director = new Director();
        director.setNombre(nombre);
        director.setApellido(apellido);
        director.setColegio(colegio);
        director.setUsuario(usuario);
        directorRepository.save(director);

        redirectAttributes.addFlashAttribute("exito", "Director registrado correctamente.");
        return "redirect:/admin/dashboard";
    }

@PostMapping("/subir-foto")
public String subirFoto(@RequestParam("foto") MultipartFile foto,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    Director director = directorRepository.findByUsuario(usuario);

    if (director == null) return "redirect:/auth/login";

    try {
        String nombre = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
        Path ruta = Paths.get("src/main/resources/static/uploads/" + nombre);
        Files.write(ruta, foto.getBytes());

        director.setFoto_perfil(nombre);
        directorRepository.save(director);

        redirectAttributes.addFlashAttribute("mensaje", "Foto actualizada correctamente.");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Error al subir la foto.");
    }

    return "redirect:/director/denuncias";
}


}
