package eetac.dsa.flota.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlanDeVuelo {
    private final String idDron;
    private final String idPiloto;
    private final LocalDateTime fechaHora;
    private final double duracionHoras;
    private final List<Posicion> posiciones;

    public PlanDeVuelo(String idDron, String idPiloto, LocalDateTime fechaHora,
                       double duracionHoras, List<Posicion> posiciones) {
        this.idDron = idDron;
        this.idPiloto = idPiloto;
        this.fechaHora = fechaHora;
        this.duracionHoras = duracionHoras;
        this.posiciones = new ArrayList<>(posiciones);
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

    public List<Posicion> getPosiciones() {
        return new ArrayList<>(posiciones);
    }

    public LocalDateTime getFechaFin() {
        return fechaHora.plusMinutes(Math.round(duracionHoras * 60));
    }

    public boolean seSolapaCon(LocalDateTime inicio, LocalDateTime fin) {
        return fechaHora.isBefore(fin) && getFechaFin().isAfter(inicio);
    }

    @Override
    public String toString() {
        return "Plan de vuelo: dron=" + idDron + ", piloto=" + idPiloto +
                ", inicio=" + fechaHora + ", duración=" + duracionHoras + "h, posiciones=" + posiciones;
    }
}
