package eetac.dsa.flota.modelo;

public class Posicion {
    private final double latitud;
    private final double longitud;

    public Posicion(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    @Override
    public String toString() {
        return "(" + latitud + ", " + longitud + ")";
    }
}
