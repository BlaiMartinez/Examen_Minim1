package eetac.dsa.flota.dto;

public class PosicionDTO {
    private double latitud;
    private double longitud;

    public PosicionDTO() {
    }

    public PosicionDTO(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
