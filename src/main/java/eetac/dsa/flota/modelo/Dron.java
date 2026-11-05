package eetac.dsa.flota.modelo;

public class Dron {
    private final String id;
    private final String nombre;
    private final String fabricante;
    private final String modelo;
    private final double horasVuelo;

    public Dron(String id, String nombre, String fabricante, String modelo, double horasVuelo) {
        this.id = id;
        this.nombre = nombre;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.horasVuelo = horasVuelo;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFabricante() {
        return fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public double getHorasVuelo() {
        return horasVuelo;
    }

    @Override
    public String toString() {
        return "Dron " + nombre + " (id=" + id + ", fabricante=" + fabricante +
                ", modelo=" + modelo + ", horasVuelo=" + horasVuelo + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dron)) return false;
        return id.equals(((Dron) o).id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}
