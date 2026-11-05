package eetac.dsa.flota.dto;

import java.time.LocalDateTime;
import java.util.List;

/** Guarda solo los identificadores para evitar ciclos al serializar JSON. */
public class PlanDeVueloDTO {
    private String idDron;
    private String idPiloto;
    private LocalDateTime fechaHora;
    private double duracionHoras;
    private List<PosicionDTO> posiciones;

    public PlanDeVueloDTO() {
    }

    public PlanDeVueloDTO(String idDron, String idPiloto, LocalDateTime fechaHora,
                          double duracionHoras, List<PosicionDTO> posiciones) {
        this.idDron = idDron;
        this.idPiloto = idPiloto;
        this.fechaHora = fechaHora;
        this.duracionHoras = duracionHoras;
        this.posiciones = posiciones;
    }

    public String getIdDron() {
        return idDron;
    }

    public String getIdPiloto() {
        return idPiloto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public double getDuracionHoras() {
        return duracionHoras;
    }

    public List<PosicionDTO> getPosiciones() {
        return posiciones;
    }
}
