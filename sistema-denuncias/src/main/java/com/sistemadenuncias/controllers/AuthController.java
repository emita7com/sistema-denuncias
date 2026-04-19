package com.sistemadenuncias.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistemadenuncias.models.entity.Colegio;
import com.sistemadenuncias.models.entity.Estudiante;
import com.sistemadenuncias.models.entity.Usuario;
import com.sistemadenuncias.models.repository.ColegioRepository;
import com.sistemadenuncias.models.repository.EstudianteRepository;
import com.sistemadenuncias.models.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private ColegioRepository colegioRepository;

    // 🟦 Mostrar formulario login
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    // 🟩 Validar login
    @PostMapping("/login")
    public String validarLogin(Usuario usuario, Model model, HttpSession session) {

        Usuario user = usuarioRepository.findByCorreo(usuario.getCorreo());

        if (user != null && user.getContrasena().equals(usuario.getContrasena())) {

            session.setAttribute("usuarioLogueado", user);

            switch (user.getRol().toUpperCase()) {
                case "ESTUDIANTE":
                    return "redirect:/denuncias/nueva";
                case "ADMIN":
                    return "redirect:/admin/dashboard";
                case "PSICOLOGO":
                    return "redirect:/psicologo/denuncias";
                case "DIRECTOR":
                    return "redirect:/director/denuncias";
                case "DEFENSORIA":
                    return "redirect:/defensoria/denuncias";
                default:
                    model.addAttribute("error", "Rol no reconocido");
                    return "login";
            }
        }

        model.addAttribute("error", "Correo o contraseña incorrectos");
        return "login";
    }

    // 🔴 Logout
    @PostMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    // 🟦 Mostrar registro
    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("colegios", colegioRepository.findAll());
        return "register";
    }

    // 🟨 Registrar estudiante (CORREGIDO)
    @PostMapping("/register")
    public String registrarEstudiante(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("curso") String curso,
            @RequestParam("correo") String correo,
            @RequestParam("contrasena") String contrasena,
            @RequestParam("idColegio") Long idColegio,
            RedirectAttributes redirectAttributes,
            Model model) {

        // validar si ya existe el correo
        if (usuarioRepository.findByCorreo(correo) != null) {
            redirectAttributes.addFlashAttribute("error", "El correo ya está registrado.");
            return "redirect:/auth/register";
        }

        // obtener colegio
        Colegio colegio = colegioRepository.findById(idColegio).orElse(null);
        if (colegio == null) {
            redirectAttributes.addFlashAttribute("error", "El colegio seleccionado no existe.");
            return "redirect:/auth/register";
        }

        // crear usuario
        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setContrasena(contrasena);
        usuario.setRol("ESTUDIANTE");
        usuario.setEstado("Activo");
        usuario.setColegio(colegio);
        usuarioRepository.save(usuario);

        // crear estudiante
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(nombre);
        estudiante.setApellido(apellido);
        estudiante.setCurso(curso);
        estudiante.setUsuario(usuario);
        estudiante.setColegio(colegio);
        estudianteRepository.save(estudiante);

        redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso, ahora puedes iniciar sesión.");
        return "redirect:/auth/login";
    }

}
