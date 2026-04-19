package com.sistemadenuncias.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistemadenuncias.models.entity.*;
import com.sistemadenuncias.models.repository.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ColegioRepository colegioRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private PsicologoRepository psicologoRepository;

    @Autowired
    private DefensoriaRepository defensoriaRepository;

    @Autowired
    private DenunciaRepository denunciaRepository;


    // 🟢 PANEL PRINCIPAL DEL ADMIN --------------------------
    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {

        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("denuncias", denunciaRepository.findAll());
        model.addAttribute("colegios", colegioRepository.findAll());

        // Objetos vacíos para los formularios
        model.addAttribute("nuevoDirector", new Director());
        model.addAttribute("nuevoPsicologo", new Psicologo());
        model.addAttribute("nuevaDefensoria", new Defensoria());

        long ciberacoso = denunciaRepository.contarPorTipo("Ciberacoso");
    long bullying = denunciaRepository.contarPorTipo("Bullying");
    long discriminacion = denunciaRepository.contarPorTipo("Discriminación");

    model.addAttribute("ciberacoso", ciberacoso);
    model.addAttribute("bullying", bullying);
    model.addAttribute("discriminacion", discriminacion);

        return "admin/dashboard";
    }

    // 🟣 REGISTRAR DIRECTOR -----------------------------
    @PostMapping("/registrar/director")
    public String registrarDirector(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String correo,
            @RequestParam String contrasena,
            @RequestParam Long colegioId,
            RedirectAttributes redirectAttributes
    ) {

        // Crear usuario
        Usuario u = new Usuario();
        u.setCorreo(correo);
        u.setContrasena(contrasena);
        u.setEstado("Activo");
        u.setRol("DIRECTOR");
        usuarioRepository.save(u);

        // Crear director
        Director d = new Director();
        d.setNombre(nombre);
        d.setApellido(apellido);
        d.setUsuario(u);
        d.setColegio(colegioRepository.findById(colegioId).orElse(null));
        directorRepository.save(d);

        redirectAttributes.addFlashAttribute("exito", "Director registrado con éxito");

        return "redirect:/admin/dashboard";
    }


    // 🟣 REGISTRAR PSICÓLOGO -----------------------------
    @PostMapping("/registrar/psicologo")
    public String registrarPsicologo(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String correo,
            @RequestParam String contrasena,
            @RequestParam Long colegioId,
            RedirectAttributes redirectAttributes
    ) {

        Usuario u = new Usuario();
        u.setCorreo(correo);
        u.setContrasena(contrasena);
        u.setEstado("Activo");
        u.setRol("PSICOLOGO");
        usuarioRepository.save(u);

        Psicologo p = new Psicologo();
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setUsuario(u);
        p.setColegio(colegioRepository.findById(colegioId).orElse(null));
        psicologoRepository.save(p);

        redirectAttributes.addFlashAttribute("exito", "Psicólogo registrado con éxito");

        return "redirect:/admin/dashboard";
    }


    // 🟣 REGISTRAR DEFENSORÍA -----------------------------
    @PostMapping("/registrar/defensoria")
public String registrarDefensoria(
        @RequestParam String nombre,
        @RequestParam String direccion,
        @RequestParam String telefono,
        @RequestParam String correo,
        @RequestParam String contrasena,
        @RequestParam(required = false) String responsable,
        RedirectAttributes flash
) {

    // 1️⃣ Crear usuario para defensoría
    Usuario u = new Usuario();
    u.setCorreo(correo);
    u.setContrasena(contrasena);
    u.setEstado("Activo");
    u.setRol("DEFENSORIA");
    usuarioRepository.save(u);

    // 2️⃣ Crear defensoría
    Defensoria d = new Defensoria();
    d.setNombre(nombre);
    d.setDireccion(direccion);
    d.setTelefono(telefono);
    d.setEstado("Activo");
    d.setResponsable(responsable);
    d.setUsuario(u);

    defensoriaRepository.save(d);

    flash.addFlashAttribute("exito", "Defensoría registrada correctamente");

    return "redirect:/admin/dashboard";
}

    // 🔵 ACTIVAR USUARIO -----------------------------
    @PostMapping("/activar/{id}")
    public String activarUsuario(@PathVariable Long id) {
        Usuario u = usuarioRepository.findById(id).orElse(null);
        if (u != null) {
            u.setEstado("Activo");
            usuarioRepository.save(u);
        }
        return "redirect:/admin/dashboard";
    }

    // 🔵 DESACTIVAR USUARIO -----------------------------
    @PostMapping("/desactivar/{id}")
    public String desactivarUsuario(@PathVariable Long id) {
        Usuario u = usuarioRepository.findById(id).orElse(null);
        if (u != null) {
            u.setEstado("Inactivo");
            usuarioRepository.save(u);
        }
        return "redirect:/admin/dashboard";
    }

/*@GetMapping("/estadisticas")
public String estadisticas(Model model) {

    long ciberacoso = denunciaRepository.contarPorTipo("Ciberacoso");
long bullying = denunciaRepository.contarPorTipo("Bullying");
long discriminacion = denunciaRepository.contarPorTipo("Discriminación");


    model.addAttribute("ciberacoso", ciberacoso);
    model.addAttribute("bullying", bullying);
    model.addAttribute("discriminacion", discriminacion);

    return "redirect:/admin/dashboard";
}*/


}
