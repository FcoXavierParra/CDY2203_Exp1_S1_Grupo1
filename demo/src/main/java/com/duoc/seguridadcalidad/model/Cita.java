// Ubicado en el paquete model para organizar el proyecto por capas.
package com.duoc.seguridadcalidad.model;

/**
 * Modelo de cita para programacion de atenciones.
 * Incluye referencia al paciente para simplificar visualizacion en la vista.
 */
public class Cita {
    private Long id;
    private Long pacienteId;
    private String pacienteNombre;
    private String fecha;
    private String hora;
    private String motivo;
    private String veterinario;

    public Cita() {
    }

    public Cita(Long id, Long pacienteId, String pacienteNombre, String fecha, String hora, String motivo, String veterinario) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.pacienteNombre = pacienteNombre;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.veterinario = veterinario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }
}
