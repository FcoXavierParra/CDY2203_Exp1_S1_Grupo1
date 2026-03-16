// Ubicado en el paquete controller para organizar el proyecto por capas.
package com.duoc.seguridadcalidad.controller;

import com.duoc.seguridadcalidad.model.Paciente;
import com.duoc.seguridadcalidad.store.PacienteMemoryStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Modulo privado de pacientes.
 * Se movio a controller para separar capa web de modelos y almacenamiento.
 */
@Controller
@RequestMapping("/pacientes")
public class PacientesController {

    private final PacienteMemoryStore pacienteStore;

    public PacientesController(PacienteMemoryStore pacienteStore) {
        this.pacienteStore = pacienteStore;
    }

    @GetMapping
    public String index(Model model) {
        // Se expone listado actual para registrar y revisar datos en una sola pantalla.
        model.addAttribute("pacientes", pacienteStore.findAll());
        return "pacientes";
    }

    @PostMapping
    public String crearPaciente(
            @RequestParam String nombre,
            @RequestParam String especie,
            @RequestParam String raza,
            @RequestParam Integer edad,
            @RequestParam String dueno,
            Model model) {

        // Validacion minima para mantener integridad de datos en almacenamiento en memoria.
        if (nombre.isBlank() || especie.isBlank() || raza.isBlank() || dueno.isBlank()) {
            model.addAttribute("error", "Todos los campos son obligatorios.");
            model.addAttribute("pacientes", pacienteStore.findAll());
            return "pacientes";
        }

        Paciente paciente = new Paciente(null, nombre, especie, raza, edad, dueno);
        pacienteStore.save(paciente);
        // Redirect evita reenvio del formulario al refrescar la pagina.
        return "redirect:/pacientes";
    }
}
