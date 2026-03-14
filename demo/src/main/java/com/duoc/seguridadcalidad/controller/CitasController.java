// Ubicado en el paquete controller para organizar el proyecto por capas.
package com.duoc.seguridadcalidad.controller;

import com.duoc.seguridadcalidad.model.Cita;
import com.duoc.seguridadcalidad.model.Paciente;
import com.duoc.seguridadcalidad.store.CitaMemoryStore;
import com.duoc.seguridadcalidad.store.PacienteMemoryStore;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Modulo privado de citas.
 * Se ubica en controller para mantener la capa web separada de store/model.
 */
@Controller
@RequestMapping("/citas")
public class CitasController {

    private final CitaMemoryStore citaStore;
    private final PacienteMemoryStore pacienteStore;

    public CitasController(CitaMemoryStore citaStore, PacienteMemoryStore pacienteStore) {
        this.citaStore = citaStore;
        this.pacienteStore = pacienteStore;
    }

    @GetMapping
    public String index(Model model) {
        // Se cargan citas y pacientes porque el formulario de citas depende de ambos.
        model.addAttribute("citas", citaStore.findAll());
        model.addAttribute("pacientes", pacienteStore.findAll());
        return "citas";
    }

    @PostMapping
    public String crearCita(
            @RequestParam Long pacienteId,
            @RequestParam String fecha,
            @RequestParam String hora,
            @RequestParam String motivo,
            @RequestParam String veterinario,
            Model model) {

        // Validacion basica de campos obligatorios antes de persistir en memoria.
        if (fecha.isBlank() || hora.isBlank() || motivo.isBlank() || veterinario.isBlank()) {
            model.addAttribute("error", "Todos los campos son obligatorios.");
            model.addAttribute("citas", citaStore.findAll());
            model.addAttribute("pacientes", pacienteStore.findAll());
            return "citas";
        }

        // Se valida que el paciente exista para evitar citas huerfanas.
        Optional<Paciente> paciente = pacienteStore.findById(pacienteId);
        if (paciente.isEmpty()) {
            model.addAttribute("error", "Debe seleccionar un paciente valido.");
            model.addAttribute("citas", citaStore.findAll());
            model.addAttribute("pacientes", pacienteStore.findAll());
            return "citas";
        }

        Cita cita = new Cita(
                null,
                pacienteId,
                paciente.get().getNombre(),
                fecha,
                hora,
                motivo,
                veterinario);

        citaStore.save(cita);
        // Redirect evita duplicar la cita al refrescar tras POST.
        return "redirect:/citas";
    }
}
